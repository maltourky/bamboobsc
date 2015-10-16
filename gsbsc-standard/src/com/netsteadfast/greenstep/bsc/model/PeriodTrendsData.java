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
package com.netsteadfast.greenstep.bsc.model;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class PeriodTrendsData<T extends BaseValueObj> implements java.io.Serializable {
	private static final long serialVersionUID = 342511764380400693L;
	private T current;
	private T previous;
	private float change = 0.0f;
	
	public PeriodTrendsData() {
		
	}
	
	public PeriodTrendsData(T current, T previous) {
		super();
		this.current = current;
		this.previous = previous;
	}
	
	public T getCurrent() {
		return current;
	}
	public void setCurrent(T current) {
		this.current = current;
	}
	public T getPrevious() {
		return previous;
	}
	public void setPrevious(T previous) {
		this.previous = previous;
	}
	public float getChange() {
		return change;
	}
	public void setChange(float change) {
		this.change = change;
	}
	
}
