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
		name="bb_degree_faceback_item", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"PROJECT_OID", "NAME"} ) 
		} 
)
public class BbDegreeFacebackItem extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 3348968812764872195L;
	private String oid;
	private String projectOid;
	private String name;
	private String description;
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

	@EntityUK(name="projectOid")
	@Column(name="PROJECT_OID")
	public String getProjectOid() {
		return projectOid;
	}
	
	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid;
	}
	
	@EntityUK(name="name")
	@Column(name="NAME")	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
