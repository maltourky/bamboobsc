#Introduction
BaseDAO for data basic operation.<br>
BaseService for for data basic operation package call DAO.<br>

***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Hibernate http://hibernate.org/<br/>
3. Dozer https://github.com/DozerMapper/dozer<br/>
4. MyBatis https://github.com/mybatis/mybatis-3<br/>

#<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/01-Entity.md">Previous section 01-Entity</a>

###DAO interfaces example
```JAVA
package com.netsteadfast.greenstep.bsc.dao;

import java.util.List;

import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.po.hbm.BbTest;
import com.netsteadfast.greenstep.vo.TestVO;

public interface ITestDAO<T extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseDAO<BbTest, String> {

}
```

###DAO implements example
```JAVA
package com.netsteadfast.greenstep.bsc.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.bsc.dao.ITestDAO;
import com.netsteadfast.greenstep.po.hbm.BbTest;
import com.netsteadfast.greenstep.vo.TestVO;

@Repository("bsc.dao.TestDAO")
@Scope("prototype")
public class TestDAOImpl extends BaseDAO<BbTest, String> implements ITestDAO<BbTest, String> {
	
	public TestDAOImpl() {
		super();
	}
	
}
```

###BaseDAO method description.

| Name | Return | description |
| --- | --- | --- |
| getSessionFactory() | SessionFactory | get hibernate session factory |
| getCurrentSession() | Session | get hibernate current session |
| getJdbcTemplate() | JdbcTemplate | get Spring JDBC template |
| getNamedParameterJdbcTemplate() | NamedParameterJdbcTemplate | get Spring JDBC template |
| getConnection() | Connection | get Spring JDBC connection |
| getSqlSession() | SqlSession | get MyBatis-3 session |
| updateByNativeSQL(String sql) | void | jdbc update |
| executeByNativeSQL(String sql) | void | jdbc execute |
| queryByNativeSQL(String sql, T rowMapper, Object... args) | Object | jdbc queryForObject |
| queryByNativeSQL(String sql) | int | jdbc query for count mode |
| queryForListByNativeSQL(String sql)<br/><br/>queryForListByNativeSQL(String sql, RowMapper rowMapper)<br/><br/>queryForListByNativeSQL(String sql, Object[] args)<br/><br/>queryForListByNativeSQL(String sql, Object[] args, RowMapper rowMapper) | List | jdbc query for List |
| getPersisentName() | String | hibernate get persisent name |
| count(String hql)<br/><br/>count(String hql, Object... args)<br/><br/>countByParams(Map`<String, Object>` params, Map`<String, String>` likeParams) | int | hibernate query for count mode |
| findList(final String hql, final int offset, final int length) | List | hibernate query for page grid |
| findPageQueryResult(String findHQL, String countHQL, Map`<String, Object>` params, int offset, int limit) | QueryResult<> | hibernate query for page grid |
| findPageQueryResultByQueryName(String pageQueryName, Map`<String, Object>` params, int offset, int limit) | QueryResult<> | hibernate query for page grid config from /resource/dynamichql/*-dynamic-hql.xml |
| save(T entityObject) | T `<BaseEntity>` | hibernate save |
| persist(T entityObject) | T `<BaseEntity>` | hibernate persist |
| update(T entityObject) | T `<BaseEntity>` | hibernate update |
| merge(T entityObject) | T `<BaseEntity>` | hibernate merge |
| delete(T entityObject) | T `<BaseEntity>` | hibernate delete |
| clear() | void | hibernate current session clear |
| findByPK(PK pk)<br/><br/>findByOid(T entityObj)<br/><br/>findByPK(Map`<String, Object>` pkMap) | T | hibernate find by OID/PK |
| countByPK(PK pk)<br/><br/>countByOid(T entityObj)<br/><br/>countByPK(Map`<String, Object>` pkMap) | int | hibernate count by OID/PK |
| deleteByPK(PK pk)<br/><br/>deleteByPK(Map`<String, Object>` pkMap) | boolean | hibernate delete by OID/PK |
| findListByParams(Map`<String, Object>` params, Map`<String, String>` likeParams, Map`<String, String>` orderParams)<br/><br/>findListByParams2(Map`<String, CustomeOperational>` customOperParams)<br/><br/>findListByParams2(Map`<String, Object>` params, Map`<String, CustomeOperational>` customOperParams)<br/><br/>findListByParams2(Map`<String, Object>` params, Map`<String, String>` likeParams, Map`<String, CustomeOperational>` customOperParams)<br/><br/>findListByParams2(Map`<String, Object>` params, Map`<String, String>` likeParams, Map`<String, CustomeOperational>` customOperParams, Map`<String, String>` orderParams)  | List | hibernate query list |
| findByUK(T entityObject)<br/><br/>findByEntityUK(Map`<String, Object>` ukMap) | T | hibernate find by UK |
| countByUK(T entityObject)<br/><br/>countByEntityUK(Map`<String, Object>` ukMap) | int | hibernate count by UK |
| getIbatisMapperNameSpace() | String | get MyBatis config namespace name |
| ibatisSelectListByParams(Map`<String, Object>` params) | List | MyBatis query list |
| ibatisSelectOneByValue(T valueObj) | T BaseValue | MyBatis query object |
| getDynamicHqlResource(String resource) | DynamicHql | get /resource/dynamichql/*-dynamic-hql.xml config |
| getDynamicHql(String queryName, Map`<String, Object>` paramMap)<br/><br/>getDynamicHql(String resource, String queryName, Map<String, Object> paramMap) | String | get dynamic hql from /resource/dynamichql/*-dynamic-hql.xml config |


<br/>
<br/>

###Service interfaces example
*** The service interfaces must define two static variable, `MAPPER_ID_PO2VO` with `MAPPER_ID_VO2PO`  ***
```JAVA
package com.netsteadfast.greenstep.bsc.service;

import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.service.IBaseService;

public interface ITestService<T extends java.io.Serializable, E extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseService<T, E, PK> {
	
	public static String MAPPER_ID_PO2VO="test.po2vo"; // Dozer mapper config PO to VO id
	public static String MAPPER_ID_VO2PO="test.vo2po"; // Dozer mapper config VO to PO id
	
}
```

| Name | description |
| --- | --- |
| MAPPER_ID_PO2VO | value set Dozer config PO2VO id |
| MAPPER_ID_VO2PO | value set Dozer config VO2PO id |

<br/>
<br/>

###Service implements example

*** Template T, E, PK ***

| Name | description |
| --- | --- |
| T | BaseValue object |
| E | BaseEntity object |
| PK | PK key type default is String |

```JAVA
package com.netsteadfast.greenstep.bsc.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.bsc.dao.ITestDAO;
import com.netsteadfast.greenstep.po.hbm.BbTest;
import com.netsteadfast.greenstep.bsc.service.ITestService;
import com.netsteadfast.greenstep.vo.TestVO;

@Service("bsc.service.TestService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class TestServiceImpl extends BaseService<TestVO, BbTest, String> implements ITestService<TestVO, BbTest, String> {
	protected Logger logger=Logger.getLogger(TestServiceImpl.class);
	private ITestDAO<BbTest, String> testDAO;
	
	public VisionServiceImpl() {
		super();
	}

	public ITestDAO<BbTest, String> getTestDAO() {
		return testDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.TestDAO")
	@Required		
	public void setTestDAO(
			ITestDAO<BbTest, String> testDAO) {
		this.testDAO = testDAO;
	}

	@Override
	protected IBaseDAO<BbTest, String> getBaseDataAccessObject() {
		return testDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}
	
}
```
