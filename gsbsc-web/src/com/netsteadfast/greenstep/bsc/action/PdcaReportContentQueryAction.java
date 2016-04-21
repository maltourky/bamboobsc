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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
import com.netsteadfast.greenstep.bsc.service.IPdcaKpisService;
import com.netsteadfast.greenstep.bsc.service.IPdcaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbPdcaKpis;
import com.netsteadfast.greenstep.po.hbm.BbPdcaMeasureFreq;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.FSUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.PdcaKpisVO;
import com.netsteadfast.greenstep.vo.PdcaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PdcaReportContentQueryAction")
@Scope
public class PdcaReportContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -3513975229265102278L;
	protected Logger logger=Logger.getLogger(PdcaReportContentQueryAction.class);
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService;
	private IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> pdcaKpisService;
	private String message = "";
	private String success = IS_NO;
	private String body = "";
	
	public PdcaReportContentQueryAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Resource(name="bsc.service.VisionService")
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}	
	
	@JSON(serialize=false)
	public IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> getPdcaMeasureFreqService() {
		return pdcaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaMeasureFreqService")
	public void setPdcaMeasureFreqService(
			IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService) {
		this.pdcaMeasureFreqService = pdcaMeasureFreqService;
	}	
	
	@JSON(serialize=false)
	public IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> getPdcaKpisService() {
		return pdcaKpisService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaKpisService")
	public void setPdcaKpisService(IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> pdcaKpisService) {
		this.pdcaKpisService = pdcaKpisService;
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
		
		// for TEST!!!
		test.setValue( FSUtils.readStr("/home/git/bamboobsc/core-doc/PDCA-REPORT-SAMPLE.htm") );
		test.setMessage("test!");
		
		return test;
	}
	
	private List<ChainResultObj> getBscReportContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		List<ChainResultObj> results = new ArrayList<ChainResultObj>();
		
		String pdcaOid = this.getFields().get("pdcaOid");
		
		// 先找出 bb_pdca_kpis 的最上層 vision
		List<String> visionOids = this.visionService.findForOidByPdcaOid( pdcaOid );
		
		for (String visionOid : visionOids) {
			PdcaMeasureFreqVO measureFreq = new PdcaMeasureFreqVO();
			measureFreq.setPdcaOid(pdcaOid);
			DefaultResult<PdcaMeasureFreqVO> mfResult = this.pdcaMeasureFreqService.findByUK(measureFreq);
			if (mfResult.getValue() == null) {
				throw new ServiceException( mfResult.getSystemMessage().getValue() );
			}
			measureFreq = mfResult.getValue();
			Context context = this.getBscReportChainContext(pdcaOid, visionOid, measureFreq);
			SimpleChain chain = new SimpleChain();
			ChainResultObj resultObj = chain.getResultFromResource("kpiReportHtmlContentChain", context);
			results.add(resultObj);
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
	private Context getBscReportChainContext(String pdcaOid, String visionOid, PdcaMeasureFreqVO measureFreq) throws Exception {
		Context context = new ContextBase();
		context.put("visionOid", visionOid);
		context.put("startDate", SimpleUtils.getStrYMD(measureFreq.getStartDate(), "/"));
		context.put("endDate", SimpleUtils.getStrYMD(measureFreq.getEndDate(), "/"));		
		context.put("startYearDate", SimpleUtils.getStrYMD(measureFreq.getStartDate(), "/"));
		context.put("endYearDate", SimpleUtils.getStrYMD(measureFreq.getEndDate(), "/"));		
		context.put("frequency", measureFreq.getFreq());
		context.put("dataFor", measureFreq.getDataType());
		context.put("orgId", measureFreq.getOrgId());
		context.put("empId", measureFreq.getEmpId());
		context.put("account", "");
		context.put("ngVer", YesNo.NO);
		
		List<String> kpiIds = new ArrayList<String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdcaOid);
		List<BbPdcaKpis> pdcaKpisList = this.pdcaKpisService.findListByParams(paramMap);
		for (BbPdcaKpis pdcaKpi : pdcaKpisList) {
			kpiIds.add( pdcaKpi.getKpiId() );
		}
		
		context.put("kpiIds", kpiIds); // 只顯示,要顯示的KPI
		
		return context;
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
