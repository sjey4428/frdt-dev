package com.sysco.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * A simple POJO that maps to the JSON structure of a generic response.
 *
 * <p>
 * Copyright (C) 2018 Sysco Corporation. All rights reserved.
 * <p>
 * 
 */
@SuppressWarnings("rawtypes")
@Getter
@Setter
public  class GenericResponse extends ResourceSupport implements Serializable {
	
	
	/**
	 * Serial version UID for the class to uniquely identify the object during serialization process
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * An array that contains the actual objects
	 */
	private Collection rows;
	
	/**
	 * An Map that contains the actual objects
	 */
	private Map mapData;
	
	/**
	 * A String containing error code.
	 */
	private String errorCode;
	
	/**
	 * A String containing error message.
	 */
	private String errorMessage;
	
	/**
	 *
	 */
	private boolean result = false;


}
