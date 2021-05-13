package com.xiborra.meep_test.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MyRestTemplateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus statusCode;

	public MyRestTemplateException(HttpStatus httpStatus, String error) {
		super(error);
		this.statusCode = httpStatus;
	}

}
