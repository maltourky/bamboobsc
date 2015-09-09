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
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="N" 
		saveJsMethod=""
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>

	<table border="0" width="100%" bgcolor="#d8d8d8">
		<tr>
			<td align="center" width="10%" bgcolor="#f5f5f5"><b>Year</b></td>
			<td align="left" width="40%" bgcolor="#f5f5f5"><b>Name</b></td>
			<td align="left" width="40%" bgcolor="#f5f5f5"><b>Description</b></td>
			<td align="center" width="10%" bgcolor="#f5f5f5">#</td>
		</tr>
		<s:if test=" null != projects && projects.size != 0 ">
		<s:iterator value="projects" var="st">
		
		<tr>
			<td align="center" width="10%" bgcolor="#ffffff"><s:property value="year"/></td>
			<td align="left" width="40%" bgcolor="#ffffff"><s:property value="name"/></td>
			<td align="left" width="40%" bgcolor="#ffffff"><s:property value="description"/></td>
			<td align="center" width="10%" bgcolor="#ffffff">
				<button name="BSC_PROG005D0002Q_score_${oid}" id="BSC_PROG005D0002Q_score_${oid}" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconSave',
						onClick:function(){ 
							alert('${oid}');
						}
					">Score</button>					
			</td>
		</tr>		
		
		</s:iterator>
		</s:if>
	</table>
	
<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
	