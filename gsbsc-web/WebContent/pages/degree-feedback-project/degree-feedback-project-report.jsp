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
	if ( _gscore_please_select_id == dijit.byId('BSC_PROG005D0004Q_owner').get('value') ) {
		BSC_PROG005D0004Q_clear();
		return;
	}	
}

function BSC_PROG005D0004Q_query() {
	xhrSendParameter(
			'${basePath}/bsc.degreeFeedbackProjectQueryScoreAction.action', 
			{ 
				'fields.projectOid'	:	'${fields.oid}',
				'fields.ownerOid'	:	dijit.byId('BSC_PROG005D0004Q_owner').get('value')
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ( 'Y' != data.success ) {
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				BSC_PROG005D0004Q_bar(data.project);
			}, 
			function(error) {
				alert(error);
			}
	);	
}
function BSC_PROG005D0004Q_bar(project) {
	if (project == null || project.items == null || project.items.length<1) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'No score data!', function(){}, 'Y');
		return;
	}
	var seriesCategories = [];
	var seriesData = [];
	var avgScores = [];
	var levelText = '';
	for (var i=0; i<project.items.length; i++) {
		var item = project.items[i];
		avgScores.push( item.avgScore );
		seriesCategories.push( item.name );
	}
	seriesData.push({
		"name"	:	'Avg Score',
		"data"	:	avgScores
	});
	for (var i=0; i<project.levels.length; i++) {
		var level = project.levels[i];
		levelText += level.value + '-' + level.name;
		if ( (i+1)<project.levels.length ) {
			levelText += ', ';
		}
	}
	
    $('#BSC_PROG005D0004Q_charts').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: project.year + ' - ' + project.name + ' / ' + project.employee.empId + ' - ' + project.employee.fullName
        },
        subtitle: {
            text: levelText
        },
        xAxis: {
            categories: seriesCategories,
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Score',
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
            valueSuffix: ' value'
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -40,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: seriesData
	});		
}

function BSC_PROG005D0004Q_clear() {
	${programId}_DlgShow('${fields.oid}');
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
							BSC_PROG005D0004Q_query();
						}
					">Query</button>
					
				<button name="BSC_PROG005D0004Q_btnClear" id="BSC_PROG005D0004Q_btnClear" data-dojo-type="dijit.form.Button"
					data-dojo-props="
						showLabel:false,
						iconClass:'dijitIconClear',
						onClick:function(){ 
							BSC_PROG005D0004Q_clear();
						}
					">Clear</button>									
												
			</td>
		</tr>
	</table>		
	
	<br/>
	
	<div id="BSC_PROG005D0004Q_charts" style="min-width: 800px; height: 100%; max-width: 1024px; margin: 0 auto"></div>
	
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
