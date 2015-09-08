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

function BSC_PROG005D0001A_S01_add() {
	var size = BSC_PROG005D0001A_itemData.length;	
	if ( size >= ${maxItemSize} ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'Cannot over items : ' + '${maxItemSize}', function(){}, 'Y');
		return;
	}
	var nameStr = dijit.byId('BSC_PROG005D0001A_S01_name').get('value');
	if ( viewPage.isBlank(nameStr) || viewPage.isEmpty(nameStr) ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'Please input name!', function(){}, 'N');
		return;
	}	
	nameStr = viewPage.escape1(nameStr);
	for (var n=0; n<size; n++) {
		if ( BSC_PROG005D0001A_itemData[n].name == nameStr ) {
			n = size;
			alertDialog(_getApplicationProgramNameById('${programId}'), 'Name is found!', function(){}, 'N');
			return;
		}
	}		
	BSC_PROG005D0001A_itemData.push( {name: nameStr, description: ' '} ); // 暫時不使用description
	BSC_PROG005D0001A_S01_showDataTable();
}

function BSC_PROG005D0001A_S01_removeField( name ) {
	var size = BSC_PROG005D0001A_itemData.length;
	for (var n=0; n<size; n++) {
		var dataItem = BSC_PROG005D0001A_itemData[n];
		if ( dataItem.name == name ) {
			BSC_PROG005D0001A_itemData.splice( n , 1 );
			n = size;
		}
	}
	BSC_PROG005D0001A_S01_showDataTable();
}

function BSC_PROG005D0001A_S01_clear() {
	setFieldsBackgroundDefault(BSC_PROG005D0001A_S01_fieldsId);		
	dijit.byId('BSC_PROG005D0001A_S01_name').set("value", "");
}

function BSC_PROG005D0001A_S01_showDataTable() {
	var size = BSC_PROG005D0001A_itemData.length;
	var txtContent = '';
	txtContent += '<table border="0" width="100%" bgcolor="#d8d8d8">';
	txtContent += '<tr>';
	txtContent += '<td width="20%" align="center" bgcolor="#f5f5f5">*</td>';
	txtContent += '<td width="80%" align="left" bgcolor="#f5f5f5"><b>Name</b></td>';
	txtContent += '</tr>';
	for (var n=0; n<size; n++) {
		var dataItem = BSC_PROG005D0001A_itemData[n];
		txtContent += '<tr>';
		var img = '<img src="' + _getSystemIconUrl('REMOVE') + '" border="0" onClick="BSC_PROG005D0001A_S01_removeField(\'' + dataItem.name + '\');" /> ';
		txtContent += '<td width="20%" align="center" bgcolor="#ffffff">' + img + '</td>';
		txtContent += '<td width="80%" align="left" bgcolor="#ffffff">' + dataItem.name + '</td>';
		txtContent += '</tr>';		
	}
	txtContent += '</table>';
	dojo.byId( 'BSC_PROG005D0001A_S01_dataTable' ).innerHTML = txtContent;
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
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Name</b>:
    			<br/>
    			<gs:textBox name="BSC_PROG005D0001A_S01_name" id="BSC_PROG005D0001A_S01_name" value="" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG005D0001A_S01_name'">
    				Input name.
				</div>       			
    		</td>  	
    	</tr>  	
    	<tr>
    		<td height="50px" width="100%"  align="center" >
    			<gs:button name="BSC_PROG005D0001A_S01_add" id="BSC_PROG005D0001A_S01_add" onClick="BSC_PROG005D0001A_S01_add();"
    				label="Add" 
    				iconClass="dijitIconSave"></gs:button>   
				<button name="BSC_PROG005D0001A_S01_clear" id="BSC_PROG005D0001A_S01_clear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:true,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG005D0001A_itemData = [];
							BSC_PROG005D0001A_S01_showDataTable();
						}
					">Clear</button>						  		
    		</td>
    	</tr> 
    	    	
    </table>
    
    <div id="BSC_PROG005D0001A_S01_dataTable"></div>
    
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

<script type="text/javascript">BSC_PROG005D0001A_S01_showDataTable();</script>   
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
    		
	