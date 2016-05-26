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






