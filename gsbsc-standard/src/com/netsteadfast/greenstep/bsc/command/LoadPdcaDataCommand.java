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
package com.netsteadfast.greenstep.bsc.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicServiceCommonSupport;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IPdcaDocService;
import com.netsteadfast.greenstep.bsc.service.IPdcaService;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbPdca;
import com.netsteadfast.greenstep.po.hbm.BbPdcaDoc;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PdcaDocVO;
import com.netsteadfast.greenstep.vo.PdcaItemVO;
import com.netsteadfast.greenstep.vo.PdcaVO;

public class LoadPdcaDataCommand extends BaseChainCommandSupport implements Command {
	private IPdcaService<PdcaVO, BbPdca, String> pdcaService;
	private IPdcaDocService<PdcaDocVO, BbPdcaDoc, String> pdcaDocService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		pdcaService = (IPdcaService<PdcaVO, BbPdca, String>) AppContext.getBean("bsc.service.PdcaService");
		pdcaDocService = (IPdcaDocService<PdcaDocVO, BbPdcaDoc, String>) AppContext.getBean("bsc.service.PdcaDocService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>) AppContext.getBean("bsc.service.EmployeeService");
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>) AppContext.getBean("bsc.service.OrganizationService");
		kpiService = (IKpiService<KpiVO, BbKpi, String>) AppContext.getBean("bsc.service.KpiService");
		String pdcaOid = (String)context.get("pdcaOid");
		PdcaVO pdca = new PdcaVO();
		pdca.setOid(pdcaOid);
		DefaultResult<PdcaVO> result = pdcaService.findObjectByOid(pdca);
		if (result.getValue() == null) {
			this.setMessage(context, result.getSystemMessage().getValue());
		} else {
			pdca = result.getValue();
			this.loadDetail(pdca);
			this.loadPdcaItems(pdca);
			this.setResult(context, pdca);
		}
		return false;
	}
	
	private void loadDetail(PdcaVO pdca) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdca.getOid());
		StringBuilder str = new StringBuilder();
		
		// 1. Project owner
		List<String> ownerNames = this.employeeService.findForAppendNames( this.employeeService.findForAppendEmployeeOidsByPdcaOwner(pdca.getOid()) );
		for (String name : ownerNames) {
			str.append(name).append(Constants.ID_DELIMITER);
		}
		pdca.setResponsibilityAppendNames( str.toString() );
		
		str.setLength(0);
		
		// 2. Confirm employee
		if (!StringUtils.isBlank(pdca.getConfirmEmpId())) {
			pdca.setConfirmEmployeeName( pdca.getConfirmEmpId() );
			try {
				EmployeeVO employee = BscBaseLogicServiceCommonSupport.findEmployeeDataByEmpId(employeeService, pdca.getConfirmEmpId());
				pdca.setConfirmEmployeeName( employee.getFullName() );
			} catch (Exception e) {
			}
		}
		
		// 3. Project organization/department
		List<String> orgaNames = this.organizationService.findForAppendNames( this.organizationService.findForAppendOrganizationOidsByPdcaOrga(pdca.getOid()) );
		for (String name : orgaNames) {
			str.append(name).append(Constants.ID_DELIMITER);
		}
		pdca.setOrganizationAppendNames(str.toString());
		
		str.setLength(0);
		
		// 4. Project KPIs
		List<String> kpiNames = this.kpiService.findForAppendNames( this.kpiService.findForAppendOidsByPdcaKpis(pdca.getOid()) );
		for (String name : kpiNames) {
			str.append(name).append(Constants.ID_DELIMITER);
		}
		pdca.setKpisAppendNames(str.toString());
		
		str.setLength(0);
		
		// 5. parent project
		if (!StringUtils.isBlank(pdca.getParentOid())) {
			DefaultResult<PdcaVO> pResult = this.pdcaService.findObjectByOid(pdca);
			if (pResult.getValue() == null) {
				throw new ServiceException( pResult.getSystemMessage().getValue() );
			}
			pdca.setParentName( pResult.getValue().getTitle() );
		}
		
		// 6. Documents file
		pdca.setDocs( this.pdcaDocService.findListVOByParams(paramMap) );
		
	}
	
	private void loadPdcaItems(PdcaVO pdca) throws ServiceException, Exception {
		pdca.setItemPlan( new ArrayList<PdcaItemVO>() );
		pdca.setItemDo( new ArrayList<PdcaItemVO>() );
		pdca.setItemCheck( new ArrayList<PdcaItemVO>() );
		pdca.setItemAction( new ArrayList<PdcaItemVO>() );
		
		
		
	}

}
