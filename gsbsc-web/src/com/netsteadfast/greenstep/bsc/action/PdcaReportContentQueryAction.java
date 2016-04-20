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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PdcaReportContentQueryAction")
@Scope
public class PdcaReportContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -3513975229265102278L;
	protected Logger logger=Logger.getLogger(PdcaReportContentQueryAction.class);
	private String message = "";
	private String success = IS_NO;
	private String body = "";
	
	public PdcaReportContentQueryAction() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException, Exception {
		try {
			super.checkFields(
					new String[]{
							"pdcaOid"
					}, 
					new String[]{
							"Please select PDCA project!<BR/>"
					}, 
					new Class[]{
							SelectItemFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}
	}
	
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		ChainResultObj pdcaReportObj = this.getPdcaReportContent();
		List<ChainResultObj> bscReportResults = this.getBscReportContent();
		if ( pdcaReportObj.getValue() instanceof String ) {
			this.body = String.valueOf(pdcaReportObj.getValue());
		}
		this.message = super.defaultString(pdcaReportObj.getMessage()).trim();
		for (ChainResultObj bscReportObj : bscReportResults) {
			if ( bscReportObj.getValue() instanceof String ) {
				this.body += String.valueOf(bscReportObj.getValue());
			}
			if (!this.message.equals(bscReportObj.getMessage())) {
				if (!"".equals(this.message)) {
					this.message += "<BR/>";
				}
				this.message += bscReportObj.getMessage();
			}			
		}
		if (!StringUtils.isBlank(this.body)) {
			this.success = IS_YES;
		}
	}
	
	private ChainResultObj getPdcaReportContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		ChainResultObj test = new ChainResultObj();
		test.setValue("test!");
		test.setMessage("test!");
		return test;
	}
	
	@SuppressWarnings("unchecked")
	private List<ChainResultObj> getBscReportContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		List<ChainResultObj> results = new ArrayList<ChainResultObj>();
		// 先找出 bb_pdca_kpis 的最上層 vision
		
		return results;
	}
	
	/**
	 * bsc.pdcaReportContentQuery.action
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG006D0002Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContent();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;
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
	
	@JSON
	public String getBody() {
		return body;
	}	

}
