/*
 * Copyright (C) 2015 Information Management Services, Inc.
 */
package com.imsweb.geocoder.exception;

public class NotFoundException extends ApiException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
