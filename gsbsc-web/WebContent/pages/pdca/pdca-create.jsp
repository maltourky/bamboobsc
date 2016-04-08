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

var BSC_PROG006D0001A_fieldsId = new Object();

function BSC_PROG006D0001A_saveSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG006D0001A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG006D0001A_fieldsId);		
		return;
	}	
	BSC_PROG006D0001A_clear();
}

function BSC_PROG006D0001A_clear() {
	setFieldsBackgroundDefault(BSC_PROG006D0001A_fieldsId);	
	
}


//------------------------------------------------------------------------------
// Content-Tab
//------------------------------------------------------------------------------
function BSC_PROG006D0001A_contentTab_reloadEmployeeAppendName() {
	var appendOid = dojo.byId('BSC_PROG006D0001A_contentTab_appendEmployeeOid').value;
	if (''==appendOid || null==appendOid ) {
		dojo.byId('BSC_PROG006D0001A_contentTab_appendEmployeeOid').value = '';
		dojo.byId('BSC_PROG006D0001A_contentTab_employeeAppendName').innerHTML = '';		
		return;
	}
	xhrSendParameter(
			'${basePath}/bsc.commonGetEmployeeNamesAction.action', 
			{ 'fields.appendId' : dojo.byId('BSC_PROG006D0001A_contentTab_appendEmployeeOid').value }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if (data!=null && data.appendName!=null) {
					dojo.byId('BSC_PROG006D0001A_contentTab_employeeAppendName').innerHTML = data.appendName;
				}								
			}, 
			function(error) {
				alert(error);
			}
	);	
}
function BSC_PROG006D0001A_contentTab_clearEmplAppendId() {
	dojo.byId('BSC_PROG006D0001A_contentTab_appendEmployeeOid').value = '';
	dojo.byId('BSC_PROG006D0001A_contentTab_employeeAppendName').innerHTML = '';
}

var BSC_PROG006D0001A_contentTab_maxUpload = 5;
var BSC_PROG006D0001A_contentTab_uploads = [];

function BSC_PROG006D0001A_contentTab_uploadDocument() {
	if (BSC_PROG006D0001A_contentTab_uploads.length >= BSC_PROG006D0001A_contentTab_maxUpload) {
		alertDialog(_getApplicationProgramNameById('${programId}'), "Can only upload " + BSC_PROG006D0001A_contentTab_maxUpload + " files!", function(){}, "Y");
		return;
	}
	openCommonUploadDialog('BSC', 'tmp', 'Y', 'BSC_PROG006D0001A_contentTab_uploadDocumentOid', 'BSC_PROG006D0001A_contentTab_uploadSuccess', 'BSC_PROG006D0001A_contentTab_uploadFail');
}
function BSC_PROG006D0001A_contentTab_uploadSuccess() {
	var docOid = dojo.byId("BSC_PROG006D0001A_contentTab_uploadDocumentOid").value;	
	if (docOid!='') {
		BSC_PROG006D0001A_contentTab_uploads.push({
			'oid' 	: docOid,
			'name' 	: BSC_PROG006D0001A_contentTab_getUploadShowName(docOid)
		});
	}
	dojo.byId("BSC_PROG006D0001A_contentTab_uploadDocumentOid").value = "";
	hideCommonUploadDialog();
	BSC_PROG006D0001A_contentTab_showUploadDataTable();
}
function BSC_PROG006D0001A_contentTab_uploadFail() {
	dojo.byId("BSC_PROG006D0001A_contentTab_uploadDocumentOid").value = "";
	hideCommonUploadDialog();
}
function BSC_PROG006D0001A_contentTab_delUpload(oid) {
	var size = BSC_PROG006D0001A_contentTab_uploads.length;
	for (var n=0; n<size; n++) {
		if ( BSC_PROG006D0001A_contentTab_uploads[n].oid == oid ) {
			BSC_PROG006D0001A_contentTab_uploads.splice( n , 1 );
			n = size;
		}
	}	
	BSC_PROG006D0001A_contentTab_showUploadDataTable();
}
function BSC_PROG006D0001A_contentTab_clearUploadDataTable() {
	BSC_PROG006D0001A_contentTab_uploads = [];
	dojo.byId("BSC_PROG006D0001A_contentTab_uploadDocumentOid").value = "";
	BSC_PROG006D0001A_contentTab_showUploadDataTable();
}
function BSC_PROG006D0001A_contentTab_showUploadDataTable() {
	var size = BSC_PROG006D0001A_contentTab_uploads.length;	
	var txtContent = '';
	if (BSC_PROG006D0001A_contentTab_uploads.length > 0) {
		txtContent += '<table border="0" width="100%" bgcolor="#d8d8d8">';
		txtContent += '<tr>';
		txtContent += '<td width="20%" align="center" bgcolor="#f5f5f5">*</td>';
		txtContent += '<td width="80%" align="left" bgcolor="#f5f5f5"><b>Documents (file name)</b></td>';
		txtContent += '</tr>';
		for (var n=0; n<size; n++) {
			var dataItem = BSC_PROG006D0001A_contentTab_uploads[n];
			txtContent += '<tr>';
			var img = '<img src="' + _getSystemIconUrl('REMOVE') + '" border="0" onClick="BSC_PROG006D0001A_contentTab_delUpload(\'' + dataItem.oid + '\');" /> ';
			txtContent += '<td width="20%" align="center" bgcolor="#ffffff">' + img + '</td>';
			txtContent += '<td width="80%" align="left" bgcolor="#ffffff"><a href="#" onclick="openCommonLoadUpload( \'download\', \'' + dataItem.oid + '\', {}); return false;" style="color:#424242">' + dataItem.name + '</a></td>';
			txtContent += '</tr>';				
		}
		txtContent += '</table>';		
	}
	dojo.byId( 'BSC_PROG006D0001A_contentTab_uploadDocumentTable' ).innerHTML = txtContent;	
}
function BSC_PROG006D0001A_contentTab_getUploadShowName(oid) {
	var names = getUploadFileNames(oid);	
	if (names == null || names.length!=1 ) {
		return "unknown";
	}
	return names[0].showName;
}

function BSC_PROG006D0001A_contentTab_reloadKPIsAppendName() {
	alert('GET-OIDs='+dojo.byId('BSC_PROG006D0001A_contentTab_appendKPIsOid').value);
}
function BSC_PROG006D0001A_contentTab_clearKPIsAppendId() {
	dojo.byId('BSC_PROG006D0001A_contentTab_appendKPIsOid').value = '';
	dojo.byId('BSC_PROG006D0001A_contentTab_kpisAppendName').innerHTML = '';	
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

<body class="flat" bgcolor="#EEEEEE" >

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="BSC_PROG006D0001A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<!-- ##################################################################### -->
	<!-- ContentTab hidden field -->
	<input type="hidden" name="BSC_PROG006D0001A_contentTab_appendEmployeeOid" id="BSC_PROG006D0001A_contentTab_appendEmployeeOid" value="" />
	<input type="hidden" name="BSC_PROG006D0001A_contentTab_uploadDocumentOid" id="BSC_PROG006D0001A_contentTab_uploadDocumentOid" value="" /><!-- 這個upload放oid的欄位只是當temp用 -->
	<input type="hidden" name="BSC_PROG006D0001A_contentTab_appendKPIsOid" id="BSC_PROG006D0001A_contentTab_appendKPIsOid" value="" />	
	<!-- ##################################################################### -->
	
	
	<table border="0" width="100%" height="100px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Title</b>:
    			<br/>
    			<gs:textBox name="BSC_PROG006D0001A_title" id="BSC_PROG006D0001A_title" value="" width="400" maxlength="100"></gs:textBox>
				<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_title'">
    				Input title.
				</div>
    		</td>
    	</tr>
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="BSC_PROG006D0001A_save" id="BSC_PROG006D0001A_save" onClick="BSC_PROG006D0001A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="${basePath}/bsc.pdcaSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						
    					} 
    				"
    				errorFn=""
    				loadFn="BSC_PROG006D0001A_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button>    			
    			<gs:button name="BSC_PROG006D0001A_clear" id="BSC_PROG006D0001A_clear" onClick="BSC_PROG006D0001A_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>       		
    		</td>
    	</tr>
	</table>
	
    <div data-dojo-type="dijit/layout/TabContainer" style="width: 100%; height: 100%;" data-dojo-props="region:'center', tabStrip:true" id="BSC_PROG006D0001A_TabContainer">
        
        <!-- ##################################################################### -->
        <!-- ContentTab -->
        <div data-dojo-type="dijit/layout/ContentPane" title="Content" data-dojo-props="selected:true">
			<table border="0" width="100%" height="450px" cellpadding="1" cellspacing="0" >
				<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<font color='RED'>*</font><b>Date range</b>:
		    			<br/>
		    			Start
		    			<input id="BSC_PROG006D0001A_contentTab_startDate" type="text" name="BSC_PROG006D0001A_contentTab_startDate" data-dojo-type="dijit.form.DateTextBox"
		    				maxlength="10" 
		    				constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" />
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_startDate'">
							Select start date.
						</div>
						&nbsp;						
						End
						<input id="BSC_PROG006D0001A_contentTab_endDate" type="text" name="BSC_PROG006D0001A_contentTab_endDate" data-dojo-type="dijit.form.DateTextBox"
							maxlength="10" 
							constraints="{datePattern:'yyyy/MM/dd', selector:'date' }" style="width:120px;" />																	    									    	
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_endDate'">
							Select end date.
						</div>  			
		    		</td>
		    	</tr>
				<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<font color='RED'>*</font><b>Responsibility&nbsp;(Employee)</b>:
		    			&nbsp;&nbsp;
						<button name="BSC_PROG006D0001A_contentTab_emplSelect" id="BSC_PROG006D0001A_contentTab_emplSelect" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconFolderOpen',
								onClick:function(){ 
									BSC_PROG001D0001Q_S00_DlgShow('BSC_PROG006D0001A_contentTab_appendEmployeeOid;BSC_PROG006D0001A_contentTab_reloadEmployeeAppendName');
								}
							"></button>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_emplSelect'">
		    				Select responsibility&nbsp;(Employee). 
						</div>						
						<button name="BSC_PROG006D0001A_contentTab_emplClear" id="BSC_PROG006D0001A_contentTab_emplClear" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconClear',
								onClick:function(){ 
									BSC_PROG006D0001A_contentTab_clearEmplAppendId();
								}
							"></button>		
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_emplClear'">
		    				Clear responsibility (Employee). 
						</div>											
						<br/>
						<span id="BSC_PROG006D0001A_contentTab_employeeAppendName"></span>	 		    			
		    		</td>
		    	</tr>
				<tr>
		    		<td height="50px" width="100%"  align="left">
		    			<font color='RED'>*</font><b>KPIs</b>:
		    			&nbsp;&nbsp;
						<button name="BSC_PROG006D0001A_contentTab_kpiSelect" id="BSC_PROG006D0001A_contentTab_kpiSelect" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconFolderOpen',
								onClick:function(){ 
									BSC_PROG002D0004Q_S00_DlgShow('BSC_PROG006D0001A_contentTab_appendKPIsOid;BSC_PROG006D0001A_contentTab_reloadKPIsAppendName');
								}
							"></button>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_kpiSelect'">
		    				Select KPIs. 
						</div>						
						<button name="BSC_PROG006D0001A_contentTab_kpiClear" id="BSC_PROG006D0001A_contentTab_kpiClear" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconClear',
								onClick:function(){ 
									BSC_PROG006D0001A_contentTab_clearKPIsAppendId();
								}
							"></button>		
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_kpiClear'">
		    				Clear KPIs. 
						</div>											
						<br/>
						<span id="BSC_PROG006D0001A_contentTab_kpisAppendName"></span>	 		    			
		    		</td>
		    	</tr>		    			    	
				<tr>
		    		<td height="50px" width="100%"  align="left">
				    	<b>Description</b>:
				    	<br/>
				    	<textarea id="BSC_PROG006D0001A_contentTab_description" name="BSC_PROG006D0001A_contentTab_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_description'">
		    				Input description, the maximum allowed 500 characters.
						</div>		    			
		    		</td>
		    	</tr>		    	
				<tr>
				    <td height="150px" width="100%" align="left" colspan="2">
				    	<b>Document / attachment</b>:
				    	<br/>
						<button name="BSC_PROG006D0001A_contentTab_uploadDocumentBtn" id="BSC_PROG006D0001A_contentTab_uploadDocumentBtn" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconFolderOpen',
								onClick:function(){ 
									BSC_PROG006D0001A_contentTab_uploadDocument();
								}
							"></button>		
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_uploadDocumentBtn'">
		    				Upload project document/attachment. 
						</div>  
						<button name="BSC_PROG006D0001A_contentTab_uploadDocumentClearBtn" id="BSC_PROG006D0001A_contentTab_uploadDocumentClearBtn" data-dojo-type="dijit.form.Button"
							data-dojo-props="
								showLabel:false,
								iconClass:'dijitIconClear',
								onClick:function(){ 
									BSC_PROG006D0001A_contentTab_clearUploadDataTable();
								}
							"></button>		
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG006D0001A_contentTab_uploadDocumentClearBtn'">
		    				Clear project upload document/attachment. 
						</div>												    	
				    	<br/>
				    	<div id="BSC_PROG006D0001A_contentTab_uploadDocumentTable"></div>
				    </td>
				</tr> 		    	
		    </table>
        </div>
        <!-- ##################################################################### -->
        
        <div data-dojo-type="dijit/layout/ContentPane" title="Plan" data-dojo-props="selected:false">
            222
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="Do" data-dojo-props="selected:false">
            333
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="Check" data-dojo-props="selected:false">
            444
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="Action" data-dojo-props="selected:false">
            555
        </div>
    </div>
	
<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
