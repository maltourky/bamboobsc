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

var BSC_PROG005D0001A_S00_fieldsId = new Object();
BSC_PROG005D0001A_S00_fieldsId['name'] 			= 'BSC_PROG005D0001A_S00_name';
BSC_PROG005D0001A_S00_fieldsId['value'] 		= 'BSC_PROG005D0001A_S00_value';

function BSC_PROG005D0001A_S00_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG005D0001A_S00_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG005D0001A_S00_fieldsId);		
		return;
	}	
	BSC_PROG005D0001A_S00_clear();
}

function BSC_PROG005D0001A_S00_add() {
	
}

function BSC_PROG005D0001A_S00_clear() {
	setFieldsBackgroundDefault(BSC_PROG005D0001A_S00_fieldsId);		
	dijit.byId('BSC_PROG005D0001A_S00_name').set("value", "");
	dijit.byId('BSC_PROG005D0001A_S00_value').set("value", "");
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
	
	<table border="0" width="100%" height="100px" cellpadding="1" cellspacing="0" >	
		<tr>
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="BSC_PROG005D0001A_S00_name" id="BSC_PROG005D0001A_S00_name" value="" width="200" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0001A_S00_name'">
    				Input name.
				</div>       			
    		</td>  
    		<td height="50px" width="50%"  align="left">
    			<font color='RED'>*</font><b>Value</b>:
    			<br/>    			
    			<input id="BSC_PROG005D0001A_S00_value" name="BSC_PROG005D0001A_S00_value" type="text" data-dojo-type="dijit/form/NumberSpinner" 
    				value="0" data-dojo-props="smallDelta:1, constraints:{min:-999,max:999, pattern: '+000;-0' }" />
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0001A_S00_value'">
    				Input value and only number.
				</div>       			
    		</td> 		
    	</tr>  	
    	<tr>
    		<td height="50px" width="100%"  align="center" colspan="2">
    			<gs:button name="BSC_PROG005D0001A_S00_add" id="BSC_PROG005D0001A_S00_add" onClick="BSC_PROG005D0001A_S00_add();"
    				label="Add" 
    				iconClass="dijitIconSave"></gs:button>    			  		
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
    		
	