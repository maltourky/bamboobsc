<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/03-LogicService.md">⇦ Previous section 03-Logic service</a>

#04 - Controller
#Introduction
bambooBSC page view / controller is use JSP / Struts2 and DOJO.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Struts2 https://struts.apache.org/docs/home.html<br/>
3. DOJO javascript framework https://dojotoolkit.org/<br/>

#BaseSupportAction
BaseSupportAction is for default page action, usually use of Management page, Edit page, Create page.<br/>
Example:
```JAVA
package com.netsteadfast.greenstep.bsc.action;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.VisionManagementAction")
@Scope
public class VisionManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -8778388464510867702L;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private VisionVO vision = new VisionVO(); // 編輯edit 要用
	private String visionContent = ""; // 編輯edit 要用
	
	public VisionManagementAction() {
		super();
	}

	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}
	
	@Autowired
	@Resource(name="bsc.service.VisionService")
	@Required	
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}
	
	private void initData() throws ServiceException, Exception {
		
	}	
	
	private void loadVisionData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.vision, new String[]{"oid"});
		DefaultResult<VisionVO> result = this.visionService.findObjectByOid(this.vision);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.vision = result.getValue();
		if (this.vision.getContent() != null) {
			this.visionContent = new String(this.vision.getContent(), Constants.BASE_ENCODING);
		}
	}	
	
	/**
	 *  bsc.visionManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0001Q")
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
	 *  bsc.visionCreateAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0001A")
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
	 *  bsc.visionEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0001E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadVisionData();
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

	public VisionVO getVision() {
		return vision;
	}

	public String getVisionContent() {
		return visionContent;
	}

}
```

| Name | Return | description |
| --- | --- | --- |
| getAccountObj() | AccountObj | get user account object |
| getAccountId() | String | get user account-id |
| getAccountOid() | String | get user account OID |
| getSysCurrentId() | String | get current-id, CORE-WEB, GSBSC-WEB, QCHARTS-WEB current-id is same value, it save in Cookie encryption with AES-128 |
| getIsSuperRole() | boolean | Administrator(admin, * role) true, other role false |
| getActionMethodProgramId() | String | get `@ControllerMethodAuthority` programId value |
| getServletContext() | ServletContext | get ServletContext |
| getHttpServletResponse() | HttpServletResponse | get HttpServletResponse |
| getHttpServletRequest() | HttpServletRequest | get HttpServletRequest |
| getPageOf() | PageOf | PageOf is for Page query grid use |
| getSearchValue() | SearchValue | SearchValue is for Page query grid use |
| getErrorMessage() | String | error msg |
| getErrorContent() | String | config of base.page.errorContent |
| getInterceptorActionInfoProvide() | ActionInfoProvide | action info |
| getDojoAjaxTimeout() | int | get config of base.page.dojoAjaxTimeout |
| getDojoAjaxSync() | String | get config of base.page.dojoAjaxSync |
| getDojoLocal() | String | base.page.dojoLocal |
| getVerMsg() | String | base.page.verMsg |
| getJsVerBuild() | String | base.page.jsVerBuild |
| getGoogleMapEnable() | String | googleMap.enable |
| getGoogleMapKey() | String | googleMap.key |
| getGoogleMapDefaultLat() | String | googleMap.defaultLat |
| getGoogleMapDefaultLng() | String | googleMap.defaultLng |
| getGoogleMapLanguage() | String | googleMap.language |
| getGoogleMapClientLocationEnable() | String | googleMap.clientLocationEnable |
| getTwitterEnable() | String | twitter.enable |
| getPageMessage() | String | page message |






