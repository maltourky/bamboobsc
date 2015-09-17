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

function BSC_PROG005D0001A_S03_saveSuccess(data) {	
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ( 'Y' == data.success ) {
		${programId}_DlgHide(); // 關掉自己的 Dlg
		BSC_PROG005D0001A_S02_DlgShow('${fields.projectOid}'); // 刷新上一層tak清單Dlg
	}
}

function BSC_PROG005D0001A_S03_clear() {
	dijit.byId('BSC_PROG005D0001A_S03_reason').set('value', '');
	dijit.byId('BSC_PROG005D0001A_S03_confirm').set('value', 'Y');
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
	
	<table border="0" width="100%" bgcolor="#ffffff">
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b><s:property value="getText('BSC_PROG005D0001A_S03_confirm')"/></b>:
    			<br/>
    			<gs:select name="BSC_PROG005D0001A_S03_confirm" dataSource="{\"Y\" : \"Yes\", \"N\" : \"Reject\"}" id="BSC_PROG005D0001A_S03_confirm" value="Y"></gs:select>  			
    		</td>   
		</tr>
		<tr>
		    <td height="100px" width="100%" align="left">
		    	<b>Reason</b>:
		    	<br/>
		    	<textarea id="BSC_PROG005D0001A_S03_reason" name="BSC_PROG005D0001A_S03_reason" data-dojo-type="dijit/form/Textarea" rows="4" cols="20" style="width:300px;height:50px;max-height:50px">Please allow Id: ${fields.taskId}</textarea>	    	
		    </td>
		</tr>
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG005D0001A_S03_save" id="BSC_PROG005D0001A_S03_save" onClick="BSC_PROG005D0001A_S03_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.degreeFeedbackProjectConfirmProcessFlowSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{     						
    						'fields.projectOid'		: '${fields.projectOid}',
    						'fields.taskId'			: '${fields.taskId}',
    						'fields.reason'			: dijit.byId('BSC_PROG005D0001A_S03_reason').get('value'),
    						'fields.confirm'		: dijit.byId('BSC_PROG005D0001A_S03_confirm').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG005D0001A_S03_saveSuccess(data);" 
    				programId="${programId}"
    				label="${action.getText('BSC_PROG005D0001A_S03_save')}" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG005D0001A_S03_clear" id="BSC_PROG005D0001A_S03_clear" onClick="BSC_PROG005D0001A_S03_clear();" 
    				label="${action.getText('BSC_PROG005D0001A_S03_clear')}" 
    				iconClass="dijitIconClear"></gs:button>       		
    		</td>
    	</tr> 		  		
	</table>
	
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
