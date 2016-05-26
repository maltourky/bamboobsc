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
| getNowDate() | String | get now date, format: 2016/05/26 |
| getNowDate2() | String | get now date, format: 2016-05-26 |
| getUuid() | String | get a UUID value |
| defaultString(String sourceString) | String | null return blank value |
| getBasePath() | String | get full URL |
| getDojoMainTabContainer() | String | get main DOJO tabContainer Id `gscoreTabContainer` |
| checkFields(String[] fieldsName, String[] msg, Class`<IActionFieldsCheckUtils>[]` checkUtilsClass)<br/><br/>checkFields(String[] fieldsName, String[] msg, Class`<IActionFieldsCheckUtils>`[] checkUtilsClass, List`<String>` fieldsId)<br/><br/>checkFields(String[] fieldsName, String[] msg, Class`<IActionFieldsCheckUtils>`[] checkUtilsClass, String[] methodsName, List`<String>` fieldsId) | void | check input field |
| transformFields2ValueObject(T valueObj, String... fieldsName) | `<T>` BaseValue | fill page fields Map variable to value object |
| transformFields2ValueObject(T valueObj, String[] objFieldsName , String[] fieldsName) | `<T>` BaseValue | fill page fields Map variable to value object |
| transformSearchGridList2JsonDataMapList(List<T> searchList, String... fields) | List`<Map<String, String>>` | fill List value object data to List map for query page grid need |
| transformList2JsonDataMapList(List<T> searchList, String[] objFields, String[] mapKeys) | List`<Map<String, String>>` | fill List value object data to List map for query page grid need |
| providedSelectZeroDataMap(boolean pleaseSelectItem) | Map`<String, String>` | provide zero Map for `gs:select` or `s:select` tag |
| isNoSelectId(String value) | boolean | if no select options item return true |
| transformAppendIds2ListByEncode(String appendIds)<br/><br/>transformAppendIds2List(String appendIds)<br/><br/>joinAppend2String(List`<String>` datas) | String | for text work |

#IBaseAdditionalSupportAction interfaces
The IBaseAdditionalSupportAction interfaces support View Page( JSP ) to get action method program-id and program-name.
```JAVA
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
```

JSP code for IBaseAdditionalSupportAction show head label.
```JSP
<jsp:include page="../header.jsp"></jsp:include>
```

<br/>
<br/>

#BaseJsonAction
BaseJsonAction is extends BaseSupportAction, it is for Save/Update/Delete controller action use.

| Name | Return | description |
| --- | --- | --- |
| allowJob() | boolean | usually only true, because before will check by UserLoginInterceptor, ControllerAuthorityCheckInterceptor |
| getNoAllowMessage() | String | get no allow message |
| getLogin() | String | Y/N |
| getIsAuthorize() | String | Y/N |
| getMessage() | String | message |
| getSuccess() | String | Y/N |
| getFieldsId() | List`<String>` | get page fields-id, when throw ControllerException, view page will RED-color input field background style |

<br/>
<br/>
#BaseQueryGridJsonAction
BaseQueryGridJsonAction is extends BaseJsonAction, it main support Grid query.

| Name | Return | description |
| --- | --- | --- |
| getItems() | List`<Map<String, String>>` | grid results list |
| getPageOfShowRow() | String | String-number of page show row size |
| getPageOfSelect() | String | String-number of page number of select |
| getPageOfCountSize() | String | String-number of grid record size |
| getPageOfSize() | String | String-number of page total size |

<br/>
<br/>
#Controller annotation

| Name | description |
| --- | --- |
| `@ControllerAuthority` | `check=true` , ControllerAuthorityCheckInterceptor will check |
| `@ControllerMethodAuthority` | programId value set controller action program-id |

Program id naming rules example:
<br/>

| Program-id | description |
| --- | --- |
| BSC_PROG002D | `BSC` is BSC-system, `CORE` is CORE-SYSTEM, `PROG002D` the `D` mean it is a menu item directory |
| BSC_PROG002D0001Q | 0001Q is for Query page, 0001 is serial-no, `Q` is mean Query page |
| BSC_PROG002D0001A | 0001A is for Create page, 0001 is serial-no, `A` is mean Create/Add page |
| BSC_PROG002D0001E | 0001E is for Edit page, 0001 is serial-no, `E` is mean Edit page |

<br/>
<br/>

#GreenStep page Tag
The gs tag is work with Struts2 and DOJO javascript.

| Tag | description |
| --- | --- |
| gs:button | page DOJO button compoment, can easy do send data to action no need write many javascript |
| gs:grid | page query grid, show DOJO grid |
| gs:select | page DOJO select compoment |
| gs:textbox | page DOJO input compoment |
| gs:toolbar | page head DOJO label compoment |

<br/>
<br/>


#Add controller to menu item and config authority
Please use:
<br/> 
1. Registration use `02 - Program registration` to add.<br/> 
2. Config menu use `03 - Menu settings` settings.<br/>
3. Role's permitted settings -> `01 - Role` function to add permitted value<br/>

<br/>
<br/>

#reference example:
Management query action:<br>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/src/com/netsteadfast/greenstep/bsc/action/VisionManagementAction.java<br/><br>
Grid query action:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/src/com/netsteadfast/greenstep/bsc/action/VisionManagementGridQueryAction.java<br/><br>
Save data or update data action:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/src/com/netsteadfast/greenstep/bsc/action/VisionSaveOrUpdateAction.java<br/><br>
View page / JSP:<br>
Management query view page:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/WebContent/pages/vision/vision-management.jsp<br/><br/>
Create view page:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/WebContent/pages/vision/vision-create.jsp<br/><br/>
Edit view page:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/WebContent/pages/vision/vision-edit.jsp<br/><br/>
<br/><br/>
Spring config:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/resource/applicationContext/controller/applicationContext-vision-web.xml<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/resource/applicationContext/applicationContext-STANDARD-WEB.xml<br/>
<br/>
Struts config:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/resource/struts/struts-bsc-vision.xml
