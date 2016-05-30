<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/10-BPMN.md">⇦ Previous section 02-DAO and Service</a>



#11 - Mail
#Introduction
Send email.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>

###Use CORE-system job to send mail
the method will send job, and pre job max 50s mail to send, a job wait 2 min.<br/>
Example:
```JAVA
TemplateResultObj result = TemplateUtils.getResult(TemplateCode.TPLMSG0001, note);
String content = result.getContent();
if (StringUtils.isBlank(content)) {
  content = note.getNote();
}
SysMailHelperVO mailHelper = new SysMailHelperVO();
mailHelper.setMailId( this.sysMailHelperService.findForMaxMailIdComplete(mailId) );
mailHelper.setMailFrom( MailClientUtils.getDefaultFrom() );
mailHelper.setMailTo(toMail);
mailHelper.setSubject( note.getTitle() );
mailHelper.setText( content.getBytes("utf8") );
mailHelper.setRetainFlag(YesNo.NO);
mailHelper.setSuccessFlag(YesNo.NO);
this.sysMailHelperService.saveObject(mailHelper);
```

