/*
 * Copyright (C) 2015 Information Management Services, Inc.
 */
package com.imsweb.geocoder.exception;

public class NotAuthorizedException extends ApiException {

    public NotAuthorizedException() {
        super();
    }

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }

}
