/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
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
package com.netsteadfast.greenstep.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;

public class WsServiceUtils {
	
	public static Object getService(String wsClientBeanId, String wsdlAddress) throws Exception {
		if (StringUtils.isBlank(wsClientBeanId)) {
			throw new IllegalArgumentException("error, bean-Id is required!");
		}
		Object serviceObj = AppContext.getBean(wsClientBeanId);
		if (!StringUtils.isBlank(wsdlAddress)) { // 更改連線wsdl位置, 不使用原本xml設定檔中設定的連線wsdl位置 
			BindingProvider bindingProvider = (BindingProvider) serviceObj;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlAddress);			
		}
		return serviceObj;
	}
	
	public static Object getService(String wsClientBeanId) throws Exception {
		return getService(wsClientBeanId, null);
	}
	
	public static boolean testConnection(String wsdlAddress) throws Exception {
		if (StringUtils.isBlank(wsdlAddress)) {
			return true;
		}
		boolean status = false;
		try {
			URL url = new URL(wsdlAddress);
			URLConnection connection = url.openConnection();
			if (connection.getContent()!=null) {
				status = true;
			}			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}
	
}
