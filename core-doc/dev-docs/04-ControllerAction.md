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



