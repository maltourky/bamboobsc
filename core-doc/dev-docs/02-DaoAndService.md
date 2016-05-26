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
| queryForListByNativeSQL(String sql)<br/>queryForListByNativeSQL(String sql, RowMapper rowMapper)<br/>queryForListByNativeSQL(String sql, Object[] args)<br/>queryForListByNativeSQL(String sql, Object[] args, RowMapper rowMapper) | List | jdbc query for List |
| getPersisentName() | String | hibernate get persisent name |
| count(String hql)<br/>count(String hql, Object... args)<br/>countByParams(Map`<String, Object>` params, Map`<String, String>` likeParams) | int | hibernate query for count mode |
| findList(final String hql, final int offset, final int length) | List | hibernate query for page grid |
| findPageQueryResult(String findHQL, String countHQL, Map`<String, Object>` params, int offset, int limit) | QueryResult<> | hibernate query for page grid |
| findPageQueryResultByQueryName(String pageQueryName, Map`<String, Object>` params, int offset, int limit) | QueryResult<> | hibernate query for page grid config from /resource/dynamichql/*-dynamic-hql.xml |
| save(T entityObject) | T `<BaseEntity>` | hibernate save |
| persist(T entityObject) | T `<BaseEntity>` | hibernate persist |
| update(T entityObject) | T `<BaseEntity>` | hibernate update |
| merge(T entityObject) | T `<BaseEntity>` | hibernate merge |
| delete(T entityObject) | T `<BaseEntity>` | hibernate delete |
| clear() | void | hibernate current session clear |
| findByPK(PK pk)<br/>findByOid(T entityObj)<br/>findByPK(Map`<String, Object>` pkMap) | T | hibernate find by OID/PK |
| countByPK(PK pk)<br/>countByOid(T entityObj)<br/>countByPK(Map`<String, Object>` pkMap) | int | hibernate count by OID/PK |
| deleteByPK(PK pk)<br/>deleteByPK(Map`<String, Object>` pkMap) | boolean | hibernate delete by OID/PK |
| findListByParams(Map`<String, Object>` params, Map`<String, String>` likeParams, Map`<String, String>` orderParams)  | List | hibernate query list |
| findByUK(T entityObject)<br/>findByEntityUK(Map`<String, Object>` ukMap) | T | hibernate find by UK |
| countByUK(T entityObject)<br/>countByEntityUK(Map`<String, Object>` ukMap) | int | hibernate count by UK |
| getIbatisMapperNameSpace() | String | get MyBatis config namespace name |
| ibatisSelectListByParams(Map`<String, Object>` params) | List | MyBatis query list |
| ibatisSelectOneByValue(T valueObj) | T BaseValue | MyBatis query object |
| getDynamicHqlResource(String resource) | DynamicHql | get /resource/dynamichql/*-dynamic-hql.xml config |
| getDynamicHql(String queryName, Map`<String, Object>` paramMap)<br/>getDynamicHql(String resource, String queryName, Map<String, Object> paramMap) | String | get dynamic hql from /resource/dynamichql/*-dynamic-hql.xml config |





