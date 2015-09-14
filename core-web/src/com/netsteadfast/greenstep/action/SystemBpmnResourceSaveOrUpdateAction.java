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
package com.netsteadfast.greenstep.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResource;
import com.netsteadfast.greenstep.service.ISysBpmnResourceService;
import com.netsteadfast.greenstep.util.BusinessProcessManagementUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysBpmnResourceVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemBpmnResourceSaveOrUpdateAction")
@Scope
public class SystemBpmnResourceSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 3200563358806590053L;
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	protected Logger logger=Logger.getLogger(SystemBpmnResourceSaveOrUpdateAction.class);
	private ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemBpmnResourceSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> getSysBpmnResourceService() {
		return sysBpmnResourceService;
	}

	@Autowired
	@Resource(name="core.service.SysBpmnResourceService")		
	public void setSysBpmnResourceService(
			ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService) {
		this.sysBpmnResourceService = sysBpmnResourceService;
	}

	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"id",
							"name"
					}, 
					new String[]{
							"Id is required!<BR/>",
							"Name is required!<BR/>"
					}, 
					new Class[]{
							NotBlankFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class
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
	
	private void selfTestUploadResourceData() throws ControllerException, AuthorityException, ServiceException, Exception {
		String id = (String)this.getFields().get("id");		
		String resourceFileProcessId = BusinessProcessManagementUtils.getResourceProcessId4Upload(
				(String)this.getFields().get("uploadOid"));
		if (!id.equals(resourceFileProcessId)) {
			this.fieldsId.add( "id" );
			throw new ControllerException("Resource file process-Id not equals Id field!<BR/>");
		}
	}
	
	private void fillContent(SysBpmnResourceVO resource) throws ServiceException, Exception {
		byte[] content = UploadSupportUtils.getDataBytes((String)this.getFields().get("uploadOid"));
		resource.setContent(content);		
	}
	
	private void fillResource(SysBpmnResourceVO resource) throws Exception {
		this.transformFields2ValueObject(resource, new String[]{"id", "name", "description"});
		if (StringUtils.defaultString(resource.getDescription()).length()>MAX_DESCRIPTION_LENGTH) {
			resource.setDescription( resource.getDescription().substring(0, MAX_DESCRIPTION_LENGTH) );			
		}		
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		if ( StringUtils.isBlank(this.getFields().get("uploadOid")) ) {
			throw new ControllerException("Please upload BPMN(zip) file!");
		}
		this.selfTestUploadResourceData();
		SysBpmnResourceVO resource = new SysBpmnResourceVO();
		this.fillResource(resource);		
		this.fillContent(resource);
		DefaultResult<SysBpmnResourceVO> result = this.sysBpmnResourceService.saveObject(resource);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * core.systemBpmnResourceSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0004A")
	public String doSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.save();
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
