import java.io.*;
import java.lang.*;
import java.util.*;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netsteadfast.greenstep.base.model.*;
import com.netsteadfast.greenstep.util.*;

// ===========================================================================================

NamedParameterJdbcTemplate jdbcTemplate = DataUtils.getJdbcTemplate();
Map<String, Object> accParamMap = new HashMap<String, Object>();
accParamMap.put("oid", SimpleUtils.getUUIDStr());
accParamMap.put("account", account);
accParamMap.put("password", transPassword);
accParamMap.put("onJob", YesNo.YES);
accParamMap.put("cuserid", "admin");
accParamMap.put("cdate", new Date());
jdbcTemplate.update("insert into tb_account(OID, ACCOUNT, PASSWORD, ON_JOB, CUSERID, CDATE) values(:oid, :account, :password, :onJob, :cuserid, :cdate)", accParamMap);

Map<String, Object> uroleParamMap = new HashMap<String, Object>();
uroleParamMap.put("oid", SimpleUtils.getUUIDStr());
uroleParamMap.put("role", "BSC_STANDARD");
uroleParamMap.put("account", account);
uroleParamMap.put("description", account + " 's role, create by LDAP login!");
uroleParamMap.put("cuserid", "admin");
uroleParamMap.put("cdate", new Date());
jdbcTemplate.update("insert into tb_user_role(OID, ROLE, ACCOUNT, DESCRIPTION, CUSERID, CDATE) values(:oid, :role, :account, :description, :cuserid, :cdate)", uroleParamMap);

Map<String, Object> empParamMap = new HashMap<String, Object>();
empParamMap.put("oid", SimpleUtils.getUUIDStr());
empParamMap.put("account", account);
empParamMap.put("empId", SimpleUtils.createRandomString(10));
empParamMap.put("fullName", "NAME - create by LDAP login!");
empParamMap.put("jobTitle", "TITLE - create by LDAP login!");
empParamMap.put("cuserid", "admin");
empParamMap.put("cdate", new Date());
jdbcTemplate.update("insert into bb_employee(OID, ACCOUNT, EMP_ID, FULL_NAME, JOB_TITLE, CUSERID, CDATE) values(:oid, :account, :empId, :fullName, :jobTitle, :cuserid, :cdate)", empParamMap);

// ===========================================================================================
