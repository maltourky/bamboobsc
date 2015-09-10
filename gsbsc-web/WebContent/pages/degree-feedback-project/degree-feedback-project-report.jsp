<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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

</style>

<script type="text/javascript">

function BSC_PROG005D0004Q_ownerChange() {
	
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
		cancelJsMethod="${programId}_DlgHide();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_DlgShow('${fields.oid}');" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%">
		<tr>
			<td align="center" bgcolor="#58775d"><b><font color="#ffffff"><s:property value="project.year"/> - <s:property value="project.name"/></font></b></td>
		</tr>		
		<tr>
			<td align="left" bgcolor="#ffffff">
				<b>Owner:</b>
				&nbsp;
				<gs:select name="BSC_PROG005D0004Q_owner" dataSource="ownerMap" id="BSC_PROG005D0004Q_owner" value="fields.employeeOid" onChange="BSC_PROG005D0004Q_ownerChange();"></gs:select>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0004Q_owner'">
    				Select project's owner.
				</div> 
				
				<button name="BSC_PROG005D0004Q_btnQuery" id="BSC_PROG005D0004Q_btnQuery" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconSearch',
						onClick:function(){ 
							
						}
					">Query</button>
					
					
				<button name="BSC_PROG005D0004Q_btnExportPng" id="BSC_PROG005D0004Q_btnExportPng" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconPrint',
						onClick:function(){ 
							
						}
					">Export PNG</button>
					
				<button name="BSC_PROG005D0004Q_btnClear" id="BSC_PROG005D0004Q_btnClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							
						}
					">Clear</button>									
												
			</td>
		</tr>
	</table>		
	
	<br/>
	
	<div id="BSC_PROG005D0004Q_content"></div>
	
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
<br/>	
<br/>
<br/>
<br/>
<br/>
			
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
