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

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.bsc.model.PeriodTrendsData;
import com.netsteadfast.greenstep.bsc.util.PeriodTrendsCalUtils;
import com.netsteadfast.greenstep.vo.KpiVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.KpiPeriodTrendsQueryAction")
@Scope
public class KpiPeriodTrendsQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -9053352575613414666L;
	protected Logger logger=Logger.getLogger(KpiPeriodTrendsQueryAction.class);
	private String message = "";
	private String success = IS_NO;
	private List<PeriodTrendsData<KpiVO>> periodDatas = new ArrayList<PeriodTrendsData<KpiVO>>();
	
	public KpiPeriodTrendsQueryAction() {
		super();
	}
	
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		
		/*
		periodDatas = PeriodTrendsCalUtils.getKpiScoreChange(
				visionOid1, startDate1, endDate1, startYearDate1, endYearDate1, frequency1, dataFor1, orgId1, empId1, 
				measureDataOrganizationOid1, measureDataEmployeeOid1, 
				visionOid2, startDate2, endDate2, startYearDate2, endYearDate2, frequency2, dataFor2, orgId2, empId2, 
				measureDataOrganizationOid2, measureDataEmployeeOid2);		
		*/
	}
	
	/**
	 * bsc.kpiPeriodTrendsQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0007Q")
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
	public List<PeriodTrendsData<KpiVO>> getPeriodDatas() {
		return periodDatas;
	}	

}
