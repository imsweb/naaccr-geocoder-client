/*
 * Copyright (C) 2015 Information Management Services, Inc.
 */
package com.imsweb.geocoder.exception;

public class BadRequestException extends ApiException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

}
