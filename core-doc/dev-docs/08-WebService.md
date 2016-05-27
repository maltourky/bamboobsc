<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/07-ExpressionSupportLogicService.md"> ⇦ Previous section 07 - Expression support Logic Service</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/09-JasperReport.md"> ⇨ Next section 09 -  JasperReport</a>


#08 - WebService
#Introduction
bambooBSC public a SOAP / REST.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Apache CXF http://cxf.apache.org/

###SOAP
Example for SOAP:

**Service interfaces**
```JAVA
package com.netsteadfast.greenstep.bsc.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
public interface KpiWebService {
	
	@WebMethod
	public String outputKpisResult(@WebParam(name="format") String format) throws Exception;

}
```

**Service bean**
```JAVA
package com.netsteadfast.greenstep.bsc.webservice.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.stereotype.Service;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.bsc.service.logic.IKpiLogicService;
import com.netsteadfast.greenstep.bsc.webservice.KpiWebService;

@Service("bsc.webservice.KpiWebService")
@WebService
@SOAPBinding
public class KpiWebServiceImpl implements KpiWebService {

	@WebMethod
	@Override
	public String outputKpisResult(@WebParam(name="format") String format) throws Exception {
		IKpiLogicService kpiLogicService = (IKpiLogicService)AppContext.getBean("bsc.service.logic.KpiLogicService");		
		return kpiLogicService.findKpis(format);
	}

}
```

**Config applicationContext-STANDARD-CXF.xml**
```XML
<bean id="bsc.webservice.KpiWebService" class="com.netsteadfast.greenstep.bsc.webservice.impl.KpiWebServiceImpl" />
```

**Public the service**

click `01 - WebService registration` to management
![Image of ws-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/08-001.jpg)
<br/>
<br/>
![Image of ws-mgr2](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/08-002.jpg)
<br/>
<br/>
***Note: need restart tomcat server to take effect***

