<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/08-WebService.md"> ⇦ Previous section 08 - WebService</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/10-BPMN.md"> ⇨ Next section 10 - BPMN</a>


#09 - JasperReport
#Introduction
bambooBSC public a JasperReport.<br>


***You must first understand the following framework***<br/>
1. JasperReport / iReport http://community.jaspersoft.com/<br/>


###Upload and deploy the jasperreport
Example is bambooBSC SWOT PDF report.

BSC_RPT002<BR/>
 |<BR/>
 |--BSC_RPT002.jrxml<BR/>
 |--BSC_RPT002_1.jrxml<BR/>
 
zip compression directory folder `BSC_RPT002` to `BSC_RPT002.zip`

#Management and upload
click `08 - Report management` to management
![Image of RPT-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/09-001.jpg)
<br/>
<br/>
![Image of RPT-mgr2](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/09-002.jpg)
<br/>
<br/>
***Settings report parameter***
![Image of RPT-mgr3](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/09-003.jpg)
<br/>
<br/>
#JSP call show report example 
use common javascript function to open.
```
openCommonJasperReportLoadWindow( 
 title name, 
 report id, 
 type now olny PDF, 
 json parameter );
```

```javascript
openCommonJasperReportLoadWindow( "SWOT-Report", "BSC_RPT002", "PDF", { 'reportId' : data.reportId } );
```

#reference example:
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/WebContent/pages/vision/vision-management.jsp

