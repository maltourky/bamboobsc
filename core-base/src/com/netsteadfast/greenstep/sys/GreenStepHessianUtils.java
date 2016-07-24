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
package com.netsteadfast.greenstep.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.util.EncryptorUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;

@SuppressWarnings("unchecked")
public class GreenStepHessianUtils {
	
	public static final String HEADER_CHECK_VALUE_PARAM_NAME = "greenstep_hessian_auth_check_value";
	private static Map<String, Object> configMap = null;
	private static String checkValue = "W$oTj09!Rq@N30l$";
	private static List<String> proxyServiceId = new ArrayList<String>();
	
	static {
		configMap = (Map<String, Object>)AppContext.getBean("hessian.config");
		checkValue = SimpleUtils.getStr(String.valueOf(configMap.get("checkValue")), checkValue);
		if (checkValue.trim().length()<1) {
			checkValue = "W$oTj09!Rq@N30l$";
		}
		String proxyServiceIdStr = StringUtils.defaultString( String.valueOf( configMap.get("proxyServiceId") ) ).replaceAll(" ", "").trim();
		String serviceIdTmp[] = proxyServiceIdStr.split(",");
		for (int i=0; serviceIdTmp!=null && i<serviceIdTmp.length; i++) {
			String serviceId = serviceIdTmp[i].replaceAll(" ", "").trim();
			if (StringUtils.isBlank(serviceId)) {
				continue;
			}
			proxyServiceId.add(serviceId);
		}
	}
	
	public static boolean isEnableCallRemote() {
		if (YesNo.YES.equals(configMap.get("enable"))) {
			return true;
		}
		return false;
	}
	
	public static String getServerUrl() {
		return String.valueOf( configMap.get("serverUrl") );
	}
	
	public static boolean isCheckValue(String encValue) throws Exception {
		if (!checkValue.equals( getDecAuthValue(encValue) )) {
			return false;
		}
		return true;
	}
	
	public static boolean isProxyServiceId(String serviceId) {
		return proxyServiceId.contains(serviceId);
	}
	
	public static String getEncAuthValue() throws Exception {
		String value = checkValue + Constants.ID_DELIMITER + SimpleUtils.createRandomString(8) + Constants.ID_DELIMITER + System.currentTimeMillis();
		return EncryptorUtils.encrypt(Constants.getEncryptorKey1(), Constants.getEncryptorKey2(), value);
	}
	
	public static String getDecAuthValue(String encValue) throws Exception {
		String value = EncryptorUtils.decrypt(Constants.getEncryptorKey1(), Constants.getEncryptorKey2(), encValue);
		String val[] = StringUtils.defaultString(value).trim().split(Constants.ID_DELIMITER);
		if (val.length!=3) {
			return null;
		}
		if (val[0].trim().length()<1 || val[1].trim().length()!=8 || !NumberUtils.isNumber(val[2])) {
			return null;
		}
		return val[0].trim();
	}
	
}
