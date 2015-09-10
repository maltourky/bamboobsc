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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.logic.IDegreeFeedbackLogicService;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackScoreVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.DegreeFeedbackProjectScoreSaveOrUpdateAction")
@Scope
public class DegreeFeedbackProjectScoreSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -1991878548958363799L;
	protected Logger logger=Logger.getLogger(DegreeFeedbackProjectScoreSaveOrUpdateAction.class);
	private IDegreeFeedbackLogicService degreeFeedbackLogicService;
	private String message = "";
	private String success = IS_NO;	
	
	public DegreeFeedbackProjectScoreSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IDegreeFeedbackLogicService getDegreeFeedbackLogicService() {
		return degreeFeedbackLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.DegreeFeedbackLogicService")		
	public void setDegreeFeedbackLogicService(IDegreeFeedbackLogicService degreeFeedbackLogicService) {
		this.degreeFeedbackLogicService = degreeFeedbackLogicService;
	}	
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"projectOid",
							"owner"
					}, 
					new String[]{
							"Data error no project, please close the page!<BR/>",
							"Please select owner!<BR/>"
					}, 
					new Class[]{
							NotBlankFieldCheckUtils.class,
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
		Map<String, List<Map<String, Object>>> scores = null;
		try {
			scores = this.fillJsonData( this.getFields().get("scoreData") );
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (scores == null || scores.get("data") == null || scores.get("data").size() < 1 ) {
			throw new ControllerException( "Please choice item's score!<BR/>" );
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, List<Map<String, Object>>> fillJsonData(String dataStr) throws Exception {		
		return (Map<String, List<Map<String, Object>>>)new ObjectMapper().readValue( dataStr, LinkedHashMap.class );
	}	
	
	private List<DegreeFeedbackScoreVO> fillScores() throws Exception {
		List<DegreeFeedbackScoreVO> scores = new ArrayList<DegreeFeedbackScoreVO>();
		Map<String, List<Map<String, Object>>> dataMap = this.fillJsonData( this.getFields().get("scoreData") );
		List<Map<String, Object>> nodes = dataMap.get( "data" );
		for (Map<String, Object> node : nodes) {
			String name = String.valueOf(node.get("name"));
			if (StringUtils.isBlank(name)) {
				logger.warn( "BSC_PROG005D0003Q_RADIO name is blank." );				
				continue;
			}			
			// BSC_PROG005D0003Q_RADIO:<s:property value="project.oid"/>:<s:property value="items[#st1.index].oid"/>
			String tmp[] = name.split(":");
			if (tmp.length!=3) {
				logger.warn( "BSC_PROG005D0003Q_RADIO name is error: " + name );				
				continue;				
			}			
			DegreeFeedbackScoreVO scoreObj = new DegreeFeedbackScoreVO();
			scoreObj.setProjectOid( this.getFields().get("projectOid") );
			scoreObj.setItemOid( tmp[2] );
			scoreObj.setScore( NumberUtils.toInt(String.valueOf(node.get("value")), 0) );
			scoreObj.setMemo( String.valueOf(node.get("memo")) );
			scores.add( scoreObj );
		}
		return scores;
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackLogicService.updateScore(
				this.getFields().get("projectOid"), 
				this.getFields().get("owner"),
				this.getAccountOid(),
				this.fillScores());
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * bsc.degreeFeedbackProjectScoreUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0003Q")
	public String doUpdate() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.update();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) {
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
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

}
