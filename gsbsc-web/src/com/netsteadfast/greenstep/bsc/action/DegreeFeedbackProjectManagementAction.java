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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackAssignService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackItemService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackLevelService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackProjectService;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackAssign;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackItem;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackProject;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.DegreeFeedbackAssignVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackItemVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.DegreeFeedbackProjectManagementAction")
@Scope
public class DegreeFeedbackProjectManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -4337083382053186479L;
	private IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService;
	private IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService;
	private IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService;
	private IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private DegreeFeedbackProjectVO degreeFeedbackProject = new DegreeFeedbackProjectVO();
	private List<BbDegreeFeedbackItem> degreeFeedbackItems = new ArrayList<BbDegreeFeedbackItem>();
	private List<BbDegreeFeedbackLevel> degreeFeedbackLevels = new ArrayList<BbDegreeFeedbackLevel>();
	
	public DegreeFeedbackProjectManagementAction() {
		super();
	}
	
	public IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> getDegreeFeedbackProjectService() {
		return degreeFeedbackProjectService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.DegreeFeedbackProjectService")	
	public void setDegreeFeedbackProjectService(
			IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService) {
		this.degreeFeedbackProjectService = degreeFeedbackProjectService;
	}

	public IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> getDegreeFeedbackItemService() {
		return degreeFeedbackItemService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.DegreeFeedbackItemService")		
	public void setDegreeFeedbackItemService(
			IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService) {
		this.degreeFeedbackItemService = degreeFeedbackItemService;
	}

	public IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> getDegreeFeedbackLevelService() {
		return degreeFeedbackLevelService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.DegreeFeedbackLevelService")			
	public void setDegreeFeedbackLevelService(
			IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService) {
		this.degreeFeedbackLevelService = degreeFeedbackLevelService;
	}

	public IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> getDegreeFeedbackAssignService() {
		return degreeFeedbackAssignService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.DegreeFeedbackAssignService")		
	public void setDegreeFeedbackAssignService(
			IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService) {
		this.degreeFeedbackAssignService = degreeFeedbackAssignService;
	}

	public IEmployeeService<EmployeeVO, BbEmployee, String> getEmployeeService() {
		return employeeService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.EmployeeService")		
	public void setEmployeeService(
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService) {
		this.employeeService = employeeService;
	}

	private void initData() throws ServiceException, Exception {
		
	}
	
	private void loadProjectData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.degreeFeedbackProject, new String[]{"oid"});
		DefaultResult<DegreeFeedbackProjectVO> result = this.degreeFeedbackProjectService
				.findObjectByOid(degreeFeedbackProject);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.degreeFeedbackProject = result.getValue();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectOid", this.degreeFeedbackProject.getOid());
		this.degreeFeedbackItems = this.degreeFeedbackItemService.findListByParams(paramMap);
		this.degreeFeedbackLevels = this.degreeFeedbackLevelService.findListByParams(paramMap);		
		List<String> appendOwnerEmplOids = this.employeeService
				.findForAppendEmployeeOidsByDegreeFeedbackProjectOwner(this.degreeFeedbackProject.getOid());
		List<String> appendRaterEmplOids = this.employeeService
				.findForAppendEmployeeOidsByDegreeFeedbackProjectRater(this.degreeFeedbackProject.getOid());
		List<String> appendOwnerNames = this.employeeService.findForAppendNames(appendOwnerEmplOids);
		List<String> appendRaterNames = this.employeeService.findForAppendNames(appendRaterEmplOids);
		this.getFields().put("ownerOids", this.fillAppendContent(appendOwnerEmplOids));
		this.getFields().put("raterOids", this.fillAppendContent(appendRaterEmplOids));
		this.getFields().put("ownerNames", this.fillAppendContent(appendOwnerNames));
		this.getFields().put("raterNames", this.fillAppendContent(appendRaterNames));
	}	
	
	private String fillAppendContent(List<String> datas) {
		StringBuilder sb = new StringBuilder();
		for (String str : datas) {
			sb.append(str).append(Constants.ID_DELIMITER);
		}
		return sb.toString();
	}
	
	/**
	 * bsc.degreeFeedbackProjectManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0001Q")
	public String execute() throws Exception {
		try {
			this.initData();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.degreeFeedbackProjectCreateAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0001A")
	public String create() throws Exception {
		try {
			this.initData();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;				
	}		
	
	/**
	 * bsc.degreeFeedbackProjectEditAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG005D0001E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadProjectData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;			
	}
	
	/**
	 * bsc.degreeFeedbackLevelCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0001A_S00")
	public String createLevel() throws Exception {
		try {
			this.initData();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;				
	}
	
	/**
	 * bsc.degreeFeedbackItemCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG005D0001A_S01")
	public String createItem() throws Exception {
		try {
			this.initData();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;				
	}	

	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}
	
	public String getMaxLevelSize() {
		return String.valueOf(BscConstants.MAX_DEGREE_FEEDBACK_LEVEL_SIZE);
	}
	
	public String getMaxItemSize() {
		return String.valueOf(BscConstants.MAX_DEGREE_FEEDBACK_ITEM_SIZE);
	}

	public DegreeFeedbackProjectVO getDegreeFeedbackProject() {
		return degreeFeedbackProject;
	}

	public void setDegreeFeedbackProject(DegreeFeedbackProjectVO degreeFeedbackProject) {
		this.degreeFeedbackProject = degreeFeedbackProject;
	}

	public List<BbDegreeFeedbackItem> getDegreeFeedbackItems() {
		return degreeFeedbackItems;
	}

	public void setDegreeFeedbackItems(List<BbDegreeFeedbackItem> degreeFeedbackItems) {
		this.degreeFeedbackItems = degreeFeedbackItems;
	}

	public List<BbDegreeFeedbackLevel> getDegreeFeedbackLevels() {
		return degreeFeedbackLevels;
	}

	public void setDegreeFeedbackLevels(List<BbDegreeFeedbackLevel> degreeFeedbackLevels) {
		this.degreeFeedbackLevels = degreeFeedbackLevels;
	}

}
