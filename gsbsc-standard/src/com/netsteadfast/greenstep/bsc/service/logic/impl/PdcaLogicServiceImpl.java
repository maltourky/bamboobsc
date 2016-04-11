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
package com.netsteadfast.greenstep.bsc.service.logic.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IPdcaLogicService;
import com.netsteadfast.greenstep.util.BusinessProcessManagementUtils;
import com.netsteadfast.greenstep.vo.PdcaVO;
import com.netsteadfast.greenstep.vo.SysBpmnResourceVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.PdcaLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PdcaLogicServiceImpl extends BaseLogicService implements IPdcaLogicService {
	protected Logger logger=Logger.getLogger(PdcaLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_LENGTH = 500;
	
	public PdcaLogicServiceImpl() {
		super();
	}
	
	@Override
	public String getBusinessProcessManagementResourceId() {
		return "PDCAProjectProcess";
	}

	@Override
	public SysBpmnResourceVO getBusinessProcessManagementResourceObject(String resourceId) throws ServiceException, Exception {
		return BusinessProcessManagementUtils.loadResource(getBusinessProcessManagementResourceId());
	}

	@Override
	public String startProcess(Map<String, Object> paramMap) throws Exception {
		return BusinessProcessManagementUtils.startProcess(this.getBusinessProcessManagementResourceId(), paramMap);
	}

	@Override
	public void completeTask(String taskId, Map<String, Object> paramMap) throws Exception {
		BusinessProcessManagementUtils.completeTask(taskId, paramMap);
	}

	@Override
	public List<Task> queryTask() throws Exception {
		return BusinessProcessManagementUtils.queryTask( this.getBusinessProcessManagementResourceId() );
	}	
	
	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<PdcaVO> create(PdcaVO pdca, List<String> organizationOids, List<String> employeeOids,
			List<String> attachment, Map<String, Object> itemJsonData) throws ServiceException, Exception {
		
		// TODO Auto-generated method stub
		return null;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<PdcaVO> update(PdcaVO pdca, List<String> organizationOids, List<String> employeeOids,
			List<String> attachment, Map<String, Object> itemJsonData) throws ServiceException, Exception {
		
		// TODO Auto-generated method stub
		return null;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.DELETE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )				
	@Override
	public DefaultResult<Boolean> delete(PdcaVO pdca) throws ServiceException, Exception {
		
		// TODO Auto-generated method stub
		return null;
	}

}
