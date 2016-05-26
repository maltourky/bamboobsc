#Logic service
#Introduction
Login service is do many base-service with transaction script bean service.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Hibernate http://hibernate.org/<br/>
3. Dozer https://github.com/DozerMapper/dozer<br/>
4. MyBatis https://github.com/mybatis/mybatis-3<br/>
5. Activiti BPMN http://activiti.org/<br/>

#<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/02-DaoAndService.md">Previous section 02-DAO and Service</a>

###BaseLogicService
BaseLogicService is minimum unit Login Service, it main support CORE-SYSTEM used.


| Name | Return | description |
| --- | --- | --- |
| getAccountId() | String | get user account id |
| defaultString(String source) | String | null will return blank |
| isBlank(String source) | boolean | null/blank true, found value false |
| isNoSelectId(String value) | boolean | value not a select option item or `Please select` return true |
| setStringValueMaxLength(T obj, String fieldName, int maxLength) | `<T>` BaseValue | set value object variable field max string size, main used of `description` field |
| replaceSplit2Blank(T obj, String fieldName, String split) | `<T>` BaseValue | set value object variable, replace split value to blank |

reference example:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/core-base/src/com/netsteadfast/greenstep/service/logic/IApplicationSystemLogicService.java<br/>
https://github.com/billchen198318/bamboobsc/blob/master/core-base/src/com/netsteadfast/greenstep/service/logic/impl/ApplicationSystemLogicServiceImpl.java<br/>


