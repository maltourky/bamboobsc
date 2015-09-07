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
package com.netsteadfast.greenstep.bsc.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.bsc.service.logic.IDegreeFeedbackLogicService;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.DegreeFeedbackProjectSaveOrUpdateAction")
@Scope
public class DegreeFeedbackProjectSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -6776416857096672930L;
	private IDegreeFeedbackLogicService degreeFeedbackLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public DegreeFeedbackProjectSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public IDegreeFeedbackLogicService getDegreeFeedbackLogicService() {
		return degreeFeedbackLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.DegreeFeedbackLogicService")		
	public void setDegreeFeedbackLogicService(
			IDegreeFeedbackLogicService degreeFeedbackLogicService) {
		this.degreeFeedbackLogicService = degreeFeedbackLogicService;
	}

	@JSON
	@Override
	public String getLogin() {
		return super.isAccountLogin();
	}
	
	@JSON
	@Override
	public String getIsAuthorize() {
		return super.isActionAuthorize();
	}	

	@JSON
	@Override
	public String getMessage() {
		return this.message;
	}

	@JSON
	@Override
	public String getSuccess() {
		return this.success;
	}

	@JSON
	@Override
	public List<String> getFieldsId() {
		return this.fieldsId;
	}

}
