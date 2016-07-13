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
package com.netsteadfast.greenstep.base.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.exception.ControllerException;

public class CheckFieldHandler {
	private Map<String, String> actionFields = null;
	private List<String> actionFieldsId = null;
	private List<String> fieldsNames = new LinkedList<String>();
	private List<String> fieldsMessages = new LinkedList<String>();
	private List<Class<IActionFieldsCheckUtils>> checkUtilsClazzs = new LinkedList<Class<IActionFieldsCheckUtils>>();
	private StringBuilder msg = new StringBuilder();
	
	public CheckFieldHandler() {
		
	}
	
	public CheckFieldHandler(Map<String, String> actionFields, List<String> actionFieldsId) {
		this.actionFields = actionFields;
		this.actionFieldsId = actionFieldsId;
	}
	
	@SuppressWarnings("unchecked")
	public CheckFieldHandler add(String fieldsName, Class<?> checkUtilsClass, String message) {
		this.fieldsNames.add(fieldsName);
		this.fieldsMessages.add(StringUtils.defaultString(message));
		this.checkUtilsClazzs.add( (Class<IActionFieldsCheckUtils>) checkUtilsClass );
		return this;
	}
	
	public Map<String, String> getActionFields() {
		return actionFields;
	}

	public void setActionFields(Map<String, String> actionFields) {
		this.actionFields = actionFields;
	}

	public List<String> getActionFieldsId() {
		return actionFieldsId;
	}

	public void setActionFieldsId(List<String> actionFieldsId) {
		this.actionFieldsId = actionFieldsId;
	}

	public CheckFieldHandler process() {
		if (this.actionFieldsId == null) {
			this.actionFieldsId = new LinkedList<String>();
		}
		if (fieldsNames==null || fieldsMessages==null || checkUtilsClazzs==null 
				|| (fieldsNames.size()!=fieldsMessages.size()) 
				|| (fieldsNames.size()!=checkUtilsClazzs.size())) {
			throw new java.lang.IllegalArgumentException("check filed args error!");
		}
		for (int i=0; i<fieldsNames.size(); i++) {
			String value = this.getActionFields().get(fieldsNames.get(i));
			try {
				IActionFieldsCheckUtils checkUtils = checkUtilsClazzs.get(i).newInstance();
				if (!checkUtils.check(value)) {
					actionFieldsId.add( fieldsNames.get(i) );
					msg.append( fieldsMessages.get(i) );
				}				
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	public CheckFieldHandler single(String fieldsName, boolean checkResult, String message) {
		if (this.actionFieldsId == null) {
			this.actionFieldsId = new LinkedList<String>();
		}
		if (!checkResult) {
			return this;
		}
		String name[] = fieldsName.replaceAll(" ", "").split("[|]");
		for (int i=0; i<name.length; i++) {
			if (StringUtils.isBlank(name[i])) {
				continue;
			}
			actionFieldsId.add( name[i].trim() );
		}
		msg.append( StringUtils.defaultString(message) );
		return this;
	}
	
	public String getMessage() {
		return this.msg.toString();
	}
	
	/**
	 * only for extends BaseJsonAction action object
	 * 
	 * @throws ControllerException
	 */
	public void throwMessage() throws ControllerException {
		if (this.msg == null || this.msg.length() < 1) {
			return;
		}
		throw new ControllerException( this.msg.toString() );
	}
	
}
