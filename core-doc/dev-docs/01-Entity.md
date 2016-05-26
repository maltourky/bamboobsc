#Entity

a persistence object extends BaseEntity
```java
package com.netsteadfast.greenstep.po.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.EntityPK;
import com.netsteadfast.greenstep.base.model.EntityUK;

@Entity
@Table(
		name="bb_test", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ID"} ) 
		} 
)
public class BbTest extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 1015124515729620253L;
	private String oid;	
	private String id;
	private String title;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;	  
	
	@Override
	@Id
	@EntityPK(name="oid")
	@Column(name="OID")
	public String getOid() {
		return oid;
	}
	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}		

	@EntityUK(name="id")
	@Column(name="ID")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	@Column(name="CUSERID")
	public String getCuserid() {
		return this.cuserid;
	}
	@Override
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	@Override
	@Column(name="CDATE")
	public Date getCdate() {
		return this.cdate;
	}
	@Override
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	@Override
	@Column(name="UUSERID")
	public String getUuserid() {
		return this.uuserid;
	}
	@Override
	public void setUuserid(String uuserid) {
		this.uuserid = uuserid;
	}
	@Override
	@Column(name="UDATE")
	public Date getUdate() {
		return this.udate;
	}
	@Override
	public void setUdate(Date udate) {
		this.udate = udate;
	}
	
}
```

<br/>
<br/>

###Persistence object (BaseEntity) must found variable field

| Name | Type |
| --- | --- |
| oid | This is PK, String |
| cuserid | create user id |
| cdate | create date-time |
| uuserid | update user id |
| udate | update date-time |

<br/>
<br/>

###Persistence object (BaseEntity) annotation

| Name | description | example |
| --- | --- | --- |
| @EntityPK | for PK(primary key) variable | @EntityPK(name="oid") |
| @EntityUK | for UK(unique key) variable | @EntityUK(name="id") |

the annotation name="" value is object variable name, not table field name.<br/>
if PO add the @EntityPK annotation, BaseService will check PK(primary key) when call saveObject method.<br/>
if PO add the @EntityUK annotation, BaseService will check UK(unique key) when call saveObject method.<br/>






