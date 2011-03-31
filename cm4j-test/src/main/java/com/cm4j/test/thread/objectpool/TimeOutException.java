package com.cm4j.test.thread.objectpool;

public class TimeOutException extends Exception{

    private static final long serialVersionUID = 1L;

    public TimeOutException() {
        super();
    }

    public TimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeOutException(String message) {
        super(message);
    }

    public TimeOutException(Throwable cause) {
        super(cause);
    }
}
