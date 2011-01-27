/*
 * $Id: Parameter.java,v 1.1 2007/05/22 14:40:10 dorelv Exp $
 *
 * This unpublished source code contains trade secrets and copyrighted
 * materials that are the proprietary property of iseemedia, Inc.
 * Unauthorized use, copying or distribution of this source code or the
 * ideas contained herein is a violation of U.S. and international laws
 * and is strictly prohibited.
 *
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 *
 */

package cg.util;

import java.io.Serializable;

/**
 * 
 * @author Dorel Vleju
 * Simple Transport Object
 *
 */
public class Parameter implements Serializable{
	  
	protected static final long serialVersionUID = -9887354321L;
	
	public static final int TYPE_OBJECT = 0;

	public static final int TYPE_STRING = 1;

	public static final int TYPE_INTEGER = 2;

	public static final int TYPE_DIMENSION = 3;

	public static final int TYPE_FLOAT = 4;

	public static final int TYPE_DOUBLE = 5;

	private String _name;

	private int _type;
	
	private Class _valueClass;

	private Object _value;
	
	  
	public Parameter(String name, int type, Object value) {
		_name = name;
		_type = type;
		_value = value;
		_valueClass = value.getClass();
	}
	
	public Parameter(String name, Object value) {
		_name = name;
		_type = TYPE_OBJECT;
		_value = value;
		_valueClass = value.getClass();
	}
	
	public boolean isNull(){
		return _value == null;
	}
	
	public boolean hasType(){
		return (_type != TYPE_OBJECT);
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public Object getValue() {
		return _value;
	}

	public void setValue(Object value) {
		_value = value;
	}
	
	public void setValue(Object value, Class clazz) {
		_value = value;
		_valueClass = clazz;
		_type = TYPE_OBJECT;
	}

	public Class getValueClass() {
		return _valueClass;
	}

	public void setValueClass(Class valueClass) {
		_valueClass = valueClass;
	}
	

}
