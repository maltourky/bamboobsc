/* 
 * Copyright 2012-2013 bambooBSC of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.sys.GreenStepHessianProxyFactory;
import com.netsteadfast.greenstep.sys.GreenStepHessianUtils;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;

@Order(1)
@Aspect
@Component
public class HessianServiceProxyAspect implements IBaseAspectService {
	protected Logger logger = Logger.getLogger(HessianServiceProxyAspect.class);
	private static final String DEFAULT_HESSIAN_URL_PATTERN = "/hessian/"; // 預設的 hession 服務子位址, 必須與 web.xml 設定一致
	private static final String DEFAULT_HESSIAN_EXTENSION_NAME = ".hessian"; // 預設的 hession 服務擴展名, 必須與 Hessian-servlet.xml 設定一致
	
	@Around( AspectConstants.LOGIC_SERVICE_PACKAGE )
	@Override
	public Object logicServiceProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		if (!GreenStepHessianUtils.isEnableCallRemote()) {
			return pjp.proceed();
		}
		return this.proxyProcess(pjp);
	}

	@Around( AspectConstants.BASE_SERVICE_PACKAGE )
	@Override
	public Object baseServiceProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		if (!GreenStepHessianUtils.isEnableCallRemote()) {
			return pjp.proceed();
		}		
		return this.proxyProcess(pjp);
	}
	
	/**
	 * no for DataAccessObject
	 */
	@Override
	public Object dataAccessObjectProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		return pjp.proceed();
	}
	
	private Object proxyProcess(ProceedingJoinPoint pjp) throws AuthorityException, ServiceException, Throwable {
		MethodSignature signature = (MethodSignature)pjp.getSignature();
		Annotation[] annotations = pjp.getTarget().getClass().getAnnotations();
		String serviceId = AspectConstants.getServiceId(annotations);
		
		/**
		 * 不需要被遠端代理的 service-bean
		 */
		if (!GreenStepHessianUtils.isProxyServiceId(serviceId)) {
			logger.info( "reject proxy service: " + serviceId );
			return pjp.proceed();
		}
		String serviceInterfacesName = "";
		Class<?> serviceInterfaces[] = pjp.getTarget().getClass().getInterfaces();
		for (Class<?> clazz : serviceInterfaces) {
			if (clazz.getName().indexOf(".service.")>-1) {
				serviceInterfacesName = clazz.getName();
			}
		}
		if (StringUtils.isBlank(serviceInterfacesName)) {
			logger.error( "error no service interface: " + serviceId );
			throw new Exception( "error no service interface: " + serviceId );			
		}
		String contextPath = ApplicationSiteUtils.getContextPath( Constants.getSystem() ) + DEFAULT_HESSIAN_URL_PATTERN;
		String url = GreenStepHessianUtils.getServerUrl() + contextPath + serviceId + DEFAULT_HESSIAN_EXTENSION_NAME;
		
		String theSystemPath = ApplicationSiteUtils.getHost( Constants.getSystem() ) + "/" + ApplicationSiteUtils.getContextPath( Constants.getSystem() );
		
		/**
		 * 不要自己呼叫遠端代理的自己, 會造成 HessianServiceProxyAspect 一直重複觸發 (客戶端與server-remote同一台的情況下)
		 * 如客戶端是 http://127.0.0.1:8080/
		 * 但是客戶端有開啟 hessian.enable=Y 呼叫遠端代理, 然後遠端url設定為 hessian.serverUrl=http://127.0.0.1:8080/
		 * 
		 */
		if ( url.indexOf(theSystemPath) > -1 ) {
			logger.error("cannot open same-server. now system contextPath = " + theSystemPath + " , but proxy url = " + url);
			throw new Exception("cannot open same-server. now system contextPath = " + theSystemPath + " , but proxy url = " + url);
		}
		
		logger.info( "proxy url = " + url );
		GreenStepHessianProxyFactory factory = new GreenStepHessianProxyFactory();
		factory.addHeader(GreenStepHessianUtils.HEADER_CHECK_VALUE_PARAM_NAME, GreenStepHessianUtils.getEncAuthValue());
		Object proxyServiceObject = factory.createForHeaderMode(Class.forName(serviceInterfacesName), url);
		Method[] proxyObjectMethods = proxyServiceObject.getClass().getMethods();
		Method proxyObjectMethod = null;
		if (null == proxyObjectMethods) {
			logger.error( "error no find proxy method: " + serviceId );
			throw new Exception( "error no find proxy method: " + serviceId );
		}
		for (Method m : proxyObjectMethods) {
			if (m.getName().equals(signature.getMethod().getName())) {
				if (Arrays.equals(m.getParameterTypes(), signature.getMethod().getParameterTypes())) {
					proxyObjectMethod = m;
				}
			}
		}
		
		if (null == proxyObjectMethod) {
			logger.error( "error no execute proxy method: " + serviceId );
			throw new Exception( "error no execute proxy method: " + serviceId );
		}
		
		Object resultObj = null;
		try {
			resultObj = proxyObjectMethod.invoke(proxyServiceObject, pjp.getArgs());
		} catch (InvocationTargetException e) {
			if (e.getMessage() != null) {
				throw new ServiceException(e.getMessage().toString());
			}
			if (e.getTargetException().getMessage() != null) {
				throw new ServiceException(e.getTargetException().getMessage().toString());
			}
			throw e;
		} catch (Exception e) {
			logger.error( e.getMessage().toString() );
			throw e;
		}
		logger.info( "proxy success: " + serviceId + " method: " + proxyObjectMethod.getName() );
		return resultObj;		
	}
	
}
