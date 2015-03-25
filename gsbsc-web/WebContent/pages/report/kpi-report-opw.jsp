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
	
	<script src="<%=mainSysBasePath%>/jsPlumb/external/jquery-1.9.0-min.js"></script>
	
<style type="text/css">

</style>

<script type="text/javascript">

function BSC_PROG003D0001Q_getOpenWindowView() {
    $.ajax({
    	type	: "POST",
    	dataType: 'json',
    	async	: false,
    	timeout	: 24000,
    	cache	: false,
    	url		: '<%=basePath%>/bsc.kpiReportContentQueryAction.action',
    	data	: { 
			'fields.visionOid' 					: 	'${fields.visionOid}',
			'fields.startYearDate'				:	'${fields.startYearDate}',
			'fields.endYearDate'				:	'${fields.endYearDate}',
			'fields.startDate'					:	'${fields.startDate}',
			'fields.endDate'					:	'${fields.endDate}',
			'fields.dataFor'					:	'${fields.dataFor}',
			'fields.measureDataOrganizationOid'	:	'${fields.measureDataOrganizationOid}',
			'fields.measureDataEmployeeOid'		:	'${fields.measureDataEmployeeOid}',
			'fields.frequency'					:	'${fields.frequency}',
			'fields.nextType'					:	'${fields.nextType}',
			'fields.nextId'						:	'${fields.nextId}'
    	},
    	success	: function(data) {
    		if ('Y' != data.success) {
    			$("#BSC_PROG003D0001Q_content").html("<h1><font color='RED'>" + data.message + "</font></h1>");
    			return;
    		}
    		$("#BSC_PROG003D0001Q_content").html( data.body );
    	},
     	error: function(data) {
     		alert(data);
     		$("#BSC_PROG003D0001Q_content").html( "<h1><font color='RED'>error!</font></h1>" );
     	}        	
	});    	
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
		
	<div id="BSC_PROG003D0001Q_content"><h1>Please wait!</h1></div>
	
<script type="text/javascript">
${programId}_page_message();
BSC_PROG003D0001Q_getOpenWindowView();
</script>
</body>
</html>
