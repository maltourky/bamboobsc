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
package com.netsteadfast.greenstep.base.service.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.po.hbm.TbUserRole;
import com.netsteadfast.greenstep.util.BusinessProcessManagementUtils;
import com.netsteadfast.greenstep.vo.SysBpmnResourceVO;

public abstract class BusinessProcessManagementBaseLogicService extends CoreBaseLogicService implements IBusinessProcessManagementResourceProvide<SysBpmnResourceVO, Task> {
	
	public BusinessProcessManagementBaseLogicService() {
		super();
	}

	public abstract String getBusinessProcessManagementResourceId();
	
	public SysBpmnResourceVO getBusinessProcessManagementResourceObject() throws ServiceException, Exception {
		return BusinessProcessManagementUtils.loadResource( this.getBusinessProcessManagementResourceId() );
	}
	
	public String startProcess(Map<String, Object> paramMap) throws Exception {
		return BusinessProcessManagementUtils.startProcess(this.getBusinessProcessManagementResourceId(), paramMap);
	}
	
	public void completeTask(String taskId, Map<String, Object> paramMap) throws Exception {
		BusinessProcessManagementUtils.completeTask(taskId, paramMap);
	}
	
	public List<Task> queryTask() throws Exception {
		return BusinessProcessManagementUtils.queryTask( this.getBusinessProcessManagementResourceId() );
	}		
	
	public boolean isAllowTaskAssignee(String taskAssignee) throws ServiceException, Exception {
		return this.isAllowTaskAssignee(super.getAccountId(), taskAssignee);
	}
	
	public boolean isAllowTaskAssignee(String accountId, String taskAssignee) throws ServiceException, Exception {
		if (super.isBlank(accountId) || super.isBlank(taskAssignee)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		List<TbUserRole> roles = this.findUserRoles(accountId);
		if (roles==null || roles.size()<1) {
			return false;
		}
		boolean allow = false;
		for (int i=0; i<roles.size() && !allow; i++) {
			if ( BusinessProcessManagementUtils
					.isRoleAssignee(this.getBusinessProcessManagementResourceId(), roles.get(i).getRole(), taskAssignee) ) {
				allow = true;
				i = roles.size();
			}
		}		
		return allow;
	}
	
	public List<Task> queryTaskByVariable(String variableKeyName, String variableKeyValue) throws ServiceException, Exception {
		if (super.isBlank(variableKeyName) || super.isBlank(variableKeyValue)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		List<Task> tasks = new ArrayList<Task>();
		List<Task> queryTasks = this.queryTask();
		if (null == queryTasks || queryTasks.size()<1) {
			return tasks;
		}
		for (Task task : queryTasks) {
			Map<String, Object> variables = BusinessProcessManagementUtils.getTaskVariables(task);
			if (variables==null || super.isBlank( (String)variables.get(variableKeyName) ) ) {
				continue;
			}
			if (variableKeyValue.equals(variables.get(variableKeyName))) {
				tasks.add( task );
			}
		}
		return tasks;
	}	
	
}
