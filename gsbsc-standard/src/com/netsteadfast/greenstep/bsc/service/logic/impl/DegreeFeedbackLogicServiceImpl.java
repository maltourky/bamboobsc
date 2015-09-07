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

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackAssignService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackItemService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackLevelService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackProjectService;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackScoreService;
import com.netsteadfast.greenstep.bsc.service.logic.IDegreeFeedbackLogicService;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackAssign;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackItem;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackProject;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackScore;
import com.netsteadfast.greenstep.vo.DegreeFeedbackAssignVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackItemVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackProjectVO;
import com.netsteadfast.greenstep.vo.DegreeFeedbackScoreVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.DegreeFeedbackLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DegreeFeedbackLogicServiceImpl extends BaseLogicService implements IDegreeFeedbackLogicService {
	protected Logger logger=Logger.getLogger(DegreeFeedbackLogicServiceImpl.class);
	private static final int MAX_DESCRIPTION_OR_MEMO_LENGTH = 500;
	private IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService;
	private IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService;
	private IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService;
	private IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService;
	private IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService;
	
	public DegreeFeedbackLogicServiceImpl() {
		super();
	}

	public IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> getDegreeFeedbackProjectService() {
		return degreeFeedbackProjectService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackProjectService")
	@Required			
	public void setDegreeFeedbackProjectService(
			IDegreeFeedbackProjectService<DegreeFeedbackProjectVO, BbDegreeFeedbackProject, String> degreeFeedbackProjectService) {
		this.degreeFeedbackProjectService = degreeFeedbackProjectService;
	}

	public IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> getDegreeFeedbackItemService() {
		return degreeFeedbackItemService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackItemService")
	@Required				
	public void setDegreeFeedbackItemService(
			IDegreeFeedbackItemService<DegreeFeedbackItemVO, BbDegreeFeedbackItem, String> degreeFeedbackItemService) {
		this.degreeFeedbackItemService = degreeFeedbackItemService;
	}

	public IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> getDegreeFeedbackLevelService() {
		return degreeFeedbackLevelService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackLevelService")
	@Required		
	public void setDegreeFeedbackLevelService(
			IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> degreeFeedbackLevelService) {
		this.degreeFeedbackLevelService = degreeFeedbackLevelService;
	}
		
	public IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> getDegreeFeedbackAssignService() {
		return degreeFeedbackAssignService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackAssignService")
	@Required		
	public void setDegreeFeedbackAssignService(
			IDegreeFeedbackAssignService<DegreeFeedbackAssignVO, BbDegreeFeedbackAssign, String> degreeFeedbackAssignService) {
		this.degreeFeedbackAssignService = degreeFeedbackAssignService;
	}
	
	public IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> getDegreeFeedbackScoreService() {
		return degreeFeedbackScoreService;
	}

	@Autowired
	@Resource(name="bsc.service.DegreeFeedbackScoreService")
	@Required			
	public void setDegreeFeedbackScoreService(
			IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> degreeFeedbackScoreService) {
		this.degreeFeedbackScoreService = degreeFeedbackScoreService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<DegreeFeedbackProjectVO> createProject(
			DegreeFeedbackProjectVO project, List<DegreeFeedbackItemVO> items,
			List<DegreeFeedbackLevelVO> level, List<DegreeFeedbackAssignVO> assign) throws ServiceException, Exception {
		
		
		return null;
	}

}
