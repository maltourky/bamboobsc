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
package com.netsteadfast.greenstep.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.lingala.zip4j.core.ZipFile;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResource;
import com.netsteadfast.greenstep.service.ISysBpmnResourceService;
import com.netsteadfast.greenstep.vo.SysBpmnResourceVO;

@SuppressWarnings("unchecked")
public class BusinessProcessManagementUtils {
	protected static Logger logger=Logger.getLogger(BusinessProcessManagementUtils.class); 
	private static final String _SUB_NAME = ".bpmn";
	private static ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService;
	private static RepositoryService repositoryService;
	
	static {
		sysBpmnResourceService = (ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String>)
				AppContext.getBean("core.service.SysBpmnResourceService");
		repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
	}
	
	public static String deployment(String resourceId) throws ServiceException, Exception {
		if (StringUtils.isBlank(resourceId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysBpmnResourceVO sysBpmnResource = new SysBpmnResourceVO();
		sysBpmnResource.setId(resourceId);
		return deployment(sysBpmnResource);
	}
	
	public static String deployment(SysBpmnResourceVO sysBpmnResource) throws ServiceException, Exception {
		DefaultResult<SysBpmnResourceVO> result = sysBpmnResourceService.findByUK(sysBpmnResource);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		ZipInputStream zip = new ZipInputStream( new ByteArrayInputStream(result.getValue().getContent()) );
		Deployment deployment = null;
		try {
			deployment = repositoryService
					.createDeployment()
					.name( result.getValue().getName() )
					.addZipInputStream( zip )
					.deploy();	
			result.getValue().setDeploymentId(deployment.getId());
			sysBpmnResourceService.updateObject(result.getValue());		
			logger.info("deployment Id: " + deployment.getId() + " , name: " + deployment.getName());			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( e.getMessage().toString() );
			throw e;
		} finally {
			zip.close();
			zip = null;				
		}		
		return deployment.getId();
	}
	
	public static void deleteDeployment(String resourceId, boolean force) throws ServiceException, Exception {
		if (StringUtils.isBlank(resourceId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysBpmnResourceVO sysBpmnResource = new SysBpmnResourceVO();
		sysBpmnResource.setId(resourceId);
		deleteDeployment(sysBpmnResource, force);
	}
	
	public static void deleteDeployment(SysBpmnResourceVO sysBpmnResource, boolean force) throws ServiceException, Exception {
		DefaultResult<SysBpmnResourceVO> result = sysBpmnResourceService.findByUK(sysBpmnResource);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		if (StringUtils.isBlank(result.getValue().getDeploymentId())) {
			throw new Exception( "No deployment!" );
		}
		logger.warn( "delete deployment Id:" + result.getValue().getDeploymentId() + " , force: " + force );
		repositoryService.deleteDeployment(result.getValue().getDeploymentId(), force);
		List<String> names = repositoryService.getDeploymentResourceNames( result.getValue().getDeploymentId() );
		if (names==null || names.size()<1) {			
			result.getValue().setDeploymentId(null);
			sysBpmnResourceService.updateObject(result.getValue());
		}
	}
	
	public static String getResourceProcessId(File activitiBpmnFile) throws Exception {
		if (null == activitiBpmnFile || !activitiBpmnFile.exists()) {
			throw new Exception("file no exists!");
		}
		if (!activitiBpmnFile.getName().endsWith(_SUB_NAME)) {
			throw new Exception("not resource file.");
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(activitiBpmnFile);
		Element element = doc.getDocumentElement();
		if (!"definitions".equals(element.getNodeName())) {
			throw new Exception("not resource file.");
		}
		String processId = activitiBpmnFile.getName();
		if (processId.endsWith(_SUB_NAME)) {
			processId = processId.substring(0, processId.length()-_SUB_NAME.length());
		}
		NodeList nodes = element.getChildNodes();
		for (int i=0; i<nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if ("process".equals(node.getNodeName())) {
				NamedNodeMap nodeMap = node.getAttributes();
				for (int attr=0; attr<nodeMap.getLength(); attr++) {
					Node processAttr = nodeMap.item(attr);
					if ("id".equals(processAttr.getNodeName())) {
						processId = processAttr.getNodeValue();
					}
				}
			}
		}
		return processId;
	}
	
	public static String getResourceProcessId4Upload(String uploadOid) throws Exception {
		File files[] = selfTestDecompress4Upload(uploadOid);
		if (files==null) {
			throw new Exception("file no exists!");
		}
		File activitiBpmnFile = null;
		for (File file : files) {
			if (file.getName().endsWith(_SUB_NAME)) {
				activitiBpmnFile = file;
			}
		}
		return getResourceProcessId(activitiBpmnFile);
	}
	
	public static File[] decompressResource(File resourceZipFile) throws Exception {
		String extractDir = Constants.getWorkTmpDir() + "/" + BusinessProcessManagementUtils.class.getSimpleName() + "/" + SimpleUtils.getUUIDStr() + "/";
		ZipFile zipFile = new ZipFile(resourceZipFile);
		zipFile.extractAll( extractDir );
		File dir = new File(extractDir);
		return dir.listFiles();
	}
	
	public static File[] selfTestDecompress4Upload(String uploadOid) throws Exception {
		File realFile = UploadSupportUtils.getRealFile(uploadOid);
		return decompressResource(realFile);
	}

}