/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.vo;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class DegreeFacebackItemVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -1648878998875116799L;
	private String oid;
	private String projectOid;
	private String name;
	private String description;
	
	public DegreeFacebackItemVO() {
		
	}
	
	public DegreeFacebackItemVO(String oid, String projectOid, String name,
			String description) {
		super();
		this.oid = oid;
		this.projectOid = projectOid;
		this.name = name;
		this.description = description;
	}

	public DegreeFacebackItemVO(String oid, String projectOid, String name) {
		super();
		this.oid = oid;
		this.projectOid = projectOid;
		this.name = name;
	}

	@Override
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getProjectOid() {
		return projectOid;
	}

	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
