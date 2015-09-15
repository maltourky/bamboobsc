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
	
	<table border="0" width="100%" bgcolor="#d8d8d8">
		<tr>
			<td align="left" width="15%" bgcolor="#f5f5f5"><b>Id</b></td>
			<td align="left" width="15%" bgcolor="#f5f5f5"><b>Name</b></td>
			<td align="left" width="15%" bgcolor="#f5f5f5"><b>Assignee</b></td>
			<td align="left" width="30%" bgcolor="#f5f5f5"><b>Reason</b></td>
			<td align="center" width="25%" bgcolor="#f5f5f5"><b>#</b></td>
		</tr>
		<s:if test="null != tasks">
		<s:iterator value="tasks" status="st">
		
		<tr>
			<td align="left" width="15%" bgcolor="#ffffff"><s:property value="id"/></td>
			<td align="left" width="15%" bgcolor="#ffffff"><s:property value="name"/></td>
			<td align="left" width="15%" bgcolor="#ffffff"><s:property value="assignee"/></td>
			<td align="left" width="30%" bgcolor="#ffffff"><s:property value="taskReason[#st.index]"/></td>
			<td align="center" width="25%" bgcolor="#ffffff">
				
				<button name="BSC_PROG005D0001A_S02_confirmDlgBtn_${fields.oid}_${id}" id="BSC_PROG005D0001A_S02_confirmDlgBtn_${fields.oid}_${id}" data-dojo-type="dijit.form.Button"
					
					<s:if test=" \"Y\" != allowAssignee[#st.index] "> disabled="disabled" </s:if>
					
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSave',
						onClick:function(){ 
							BSC_PROG005D0001A_S03_DlgShow('${fields.oid};${id}');
						}
					">Confirm</button>
					
			</td>
		</tr>			
		</s:iterator>
		</s:if>
		
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
