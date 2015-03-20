<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String mainSysBasePath = ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), request);

%>
<!doctype html>
<html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <base href="<%=basePath%>">
    
    <title>bambooCORE</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="bambooCORE">
	<meta http-equiv="description" content="bambooCORE">
	
<style type="text/css">

.btnExcelIcon {
  	background-image: url(./icons/excel.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnPdfIcon {
  	background-image: url(./icons/application-pdf.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

.btnSignatureIcon {
  	background-image: url(./icons/text_signature.png);
  	background-repeat: no-repeat;
  	width: 16px;
  	height: 16px;
  	text-align: center;
}

#BSC_PROG003D0002Q_content { 
	width:100%; 
	height:100%;
	page-break-after:always 
}

</style>

<script type="text/javascript">

var BSC_PROG003D0002Q_fieldsId = new Object();
BSC_PROG003D0002Q_fieldsId['visionOid'] 	= 'BSC_PROG003D0002Q_visionOid';
BSC_PROG003D0002Q_fieldsId['frequency'] 	= 'BSC_PROG003D0002Q_frequency';
BSC_PROG003D0002Q_fieldsId['employeeOid'] 	= 'BSC_PROG003D0002Q_employeeOid';
BSC_PROG003D0002Q_fieldsId['dateType'] 		= 'BSC_PROG003D0002Q_dateType';

function BSC_PROG003D0002Q_query() {
	BSC_PROG003D0002Q_clearContent();
	setFieldsBackgroundDefault(BSC_PROG003D0002Q_fieldsId);
	xhrSendParameter(
			'${basePath}/bsc.personalReportContentQueryAction.action', 
			{ 
				'fields.visionOid' 		: 	dijit.byId("BSC_PROG003D0002Q_visionOid").get("value"),
				'fields.frequency'		:	dijit.byId("BSC_PROG003D0002Q_frequency").get("value"),
				'fields.employeeOid'	:	dijit.byId("BSC_PROG003D0002Q_employeeOid").get("value"),
				'fields.dateType'		:	dijit.byId("BSC_PROG003D0002Q_dateType").get("value"),
				'fields.year'			:	dijit.byId("BSC_PROG003D0002Q_year").get("value")
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0002Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				dojo.byId("BSC_PROG003D0002Q_content").innerHTML = data.body;
			}, 
			function(error) {
				alert(error);
			}
	);		
}

function BSC_PROG003D0002Q_generateExport(docType) {
	var url = '${basePath}/bsc.personalReportExcelQueryAction.action';
	if ( 'PDF' == docType ) {
		url = '${basePath}/bsc.personalReportPdfQueryAction.action';
	}
	setFieldsBackgroundDefault(BSC_PROG003D0002Q_fieldsId);
	xhrSendParameter(
			url, 
			{ 
				'fields.visionOid' 			: 	dijit.byId("BSC_PROG003D0002Q_visionOid").get("value"),
				'fields.frequency'			:	dijit.byId("BSC_PROG003D0002Q_frequency").get("value"),
				'fields.employeeOid'		:	dijit.byId("BSC_PROG003D0002Q_employeeOid").get("value"),
				'fields.dateType'			:	dijit.byId("BSC_PROG003D0002Q_dateType").get("value"),
				'fields.year'				:	dijit.byId("BSC_PROG003D0002Q_year").get("value"),
				'fields.uploadSignatureOid'	:	dojo.byId("BSC_PROG003D0002Q_uploadSignatureOid").value
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					setFieldsBackgroundAlert(data.fieldsId, BSC_PROG003D0002Q_fieldsId);
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				window.open(
						"<%=mainSysBasePath%>/core.commonLoadUploadFileAction.action?type=download&oid=" + data.uploadOid,
						"Personal-Report-export",
			            "resizable=yes,scrollbars=yes,status=yes,width=400,height=200");    									
			}, 
			function(error) {
				alert(error);
			}
	);
	
}

function BSC_PROG003D0002Q_setFrequencyValue() {
	var dateType = dijit.byId("BSC_PROG003D0002Q_dateType").get("value");
	dijit.byId("BSC_PROG003D0002Q_frequency").set("value", "5"); // half-year
	if ( "3" == dateType ) {
		dijit.byId("BSC_PROG003D0002Q_frequency").set("value", "6"); // year
	} 	
}

function BSC_PROG003D0002Q_clearContent() {
	dojo.byId("BSC_PROG003D0002Q_content").innerHTML = "";
}

function BSC_PROG003D0002Q_uploadSignatureSuccess() {
	
}

function BSC_PROG003D0002Q_uploadSignatureFail() {
	dojo.byId('BSC_PROG003D0002Q_uploadSignatureOid').value = '';
}

function BSC_PROG003D0002Q_uploadSignatureClear() {
	var uploadOid = dojo.byId('BSC_PROG003D0002Q_uploadSignatureOid').value;
	if (null == uploadOid || 'null' == uploadOid || '' == uploadOid) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'No before save signature!', function(){}, 'Y');	
		return;
	}	
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"Clear signature? ", 
			function(success) {
				if (!success) {
					return;
				}	
				dojo.byId('BSC_PROG003D0002Q_uploadSignatureOid').value = '';
				alertDialog(_getApplicationProgramNameById('${programId}'), 'Clear signature success!', function(){}, 'Y');				
			}, 
			(window.event ? window.event : null) 
	);
}

//------------------------------------------------------------------------------
function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}
//------------------------------------------------------------------------------

</script>

</head>

<body class="claro" bgcolor="#EEEEEE" >

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>		
	
	<input type="hidden" name="BSC_PROG003D0002Q_uploadSignatureOid" id="BSC_PROG003D0002Q_uploadSignatureOid" value="" />
	
	<table border="0" width="100%" >
		<tr valign="top">
			<td width="100%" align="center" height="25%">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: 'Options' " >						
					<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:100%;height:90px">
					
						<table border="0" width="100%" >
							<tr valign="top">
								<td width="100%" align="left" height="25px" bgcolor="#d7e3ed">	
								
									<button id="BSC_PROG003D0002Q_btnQuery" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconSearch',
											showLabel:false,
											onClick:function(){  
												BSC_PROG003D0002Q_query();
											}">Query</button>		
																
									<button id="BSC_PROG003D0002Q_btnExportPng" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconPrint',
											showLabel:false,
											onClick:function(){
											
												html2canvas( $('#BSC_PROG003D0002Q_content'), {
													onrendered: function(canvas) {
												        var a = document.createElement('a');
												        a.download = 'personal-report.png';
												        a.href = canvas.toDataURL('image/png');
												        document.body.appendChild(a);
												        a.click();							
													},
													allowTaint 	: true,
													useCORS		: true,
													taintTest	: true
												});
																							  
											}">export as PNG</button>	
					
									<button id="BSC_PROG003D0002Q_btnPdf" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnPdfIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0002Q_generateExport('PDF');	
											}">PDF</button>	
									            
									<button id="BSC_PROG003D0002Q_btnXls" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnExcelIcon',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0002Q_generateExport('EXCEL');																			  
											}">Excel</button>	
											
											
									<button id="BSC_PROG003D0002Q_btnSignature" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'btnSignatureIcon',
											showLabel:false,
											onClick:function(){
												openCommonSignatureDialog('BSC', 'BSC_PROG003D0002Q_uploadSignatureOid', 'BSC_PROG003D0002Q_uploadSignatureSuccess', 'BSC_PROG003D0002Q_uploadSignatureFail');													  
											}">Signature</button>												
										
									<button id="BSC_PROG003D0002Q_btnSignatureClear" data-dojo-type="dijit.form.Button"
										data-dojo-props="
											iconClass:'dijitIconClear',
											showLabel:false,
											onClick:function(){
												BSC_PROG003D0002Q_uploadSignatureClear();
											}">Clear signature</button>														
											
								</td>											
							</tr>							
							<tr valign="top">
								<td width="100%" align="left" height="25px">	
								
									Vision: 
									<gs:select name="BSC_PROG003D0002Q_visionOid" dataSource="visionMap" id="BSC_PROG003D0002Q_visionOid"></gs:select>
						    		&nbsp;		    			
					    																	
									Measure data frequency:
									<gs:select name="BSC_PROG003D0002Q_frequency" dataSource="frequencyMap" id="BSC_PROG003D0002Q_frequency" value="5" width="140" readonly="Y"></gs:select>
									
									&nbsp;
									employee:
									<gs:select name="BSC_PROG003D0002Q_employeeOid" dataSource="employeeMap" id="BSC_PROG003D0002Q_employeeOid" ></gs:select>									
																		
								</td>	
							</tr>
							<tr valign="top">
								<td width="100%" align="left" height="30px">	
									
									<table border="0" width="100%" >
										<tr valign="top">
											<td width="250px" align="left" height="30px" >	
											
												<div id="BSC_PROG003D0002Q_year"
												    style="width:200px;"
												    name="horizontalSlider"
												    data-dojo-type="dijit/form/HorizontalSlider"
												    data-dojo-props="value:${maximum},
													    minimum:${minimum},
													    maximum:${maximum},
													    discreteValues:${discreteValues},
													    intermediateChanges:false,
													    showButtons:false,
													    onChange:function(value) {
													    
													    }
												    ">
												    <ol data-dojo-type="dijit/form/HorizontalRuleLabels" container="topDecoration"
												        style="height:1.5em;font-size:75%;color:gray;">
												        <s:if test="null != yearRangeList">
												        <s:iterator value="yearRangeList" status="st">
												        <li><s:property value="yearRangeList.get(#st.index)"/></li>
												        </s:iterator>
												        </s:if>
												    </ol>
												</div> 	
																						
											</td>
											<td width="300px" align="left" height="30px" >	
												<gs:select name="BSC_PROG003D0002Q_dateType" 
													dataSource="{ \"1\":\"In the first half\", \"2\":\"In the second half\", \"3\":\"Year\" }" 
													id="BSC_PROG003D0002Q_dateType"
													onChange="BSC_PROG003D0002Q_setFrequencyValue();"></gs:select>											
											</td>
											<td align="left" height="30px" >
												&nbsp;
											</td>											
										</tr>
									</table>											
										
								</td>	
							</tr>
														
						</table>		
													
					
					</div>
				</div>
			</td>
		</tr>
	</table>					
						
	
	<div id="BSC_PROG003D0002Q_content"></div>
	
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	