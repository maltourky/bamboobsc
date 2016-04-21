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

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IPdcaService;
import com.netsteadfast.greenstep.po.hbm.BbPdca;
import com.netsteadfast.greenstep.vo.PdcaVO;

public class LoadPdcaDataCommand extends BaseChainCommandSupport implements Command {
	private IPdcaService<PdcaVO, BbPdca, String> pdcaService;

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		pdcaService = (IPdcaService<PdcaVO, BbPdca, String>) AppContext.getBean("bsc.service.PdcaService");
		String pdcaOid = (String)context.get("pdcaOid");
		PdcaVO pdca = new PdcaVO();
		pdca.setOid(pdcaOid);
		DefaultResult<PdcaVO> result = pdcaService.findObjectByOid(pdca);
		if (result.getValue() == null) {
			this.setMessage(context, result.getSystemMessage().getValue());
		} else {
			pdca = result.getValue();
			this.loadDetail(pdca);
			this.setResult(context, pdca);
		}
		return false;
	}
	
	private void loadDetail(PdcaVO pdca) throws ServiceException, Exception {
		
	}

}
