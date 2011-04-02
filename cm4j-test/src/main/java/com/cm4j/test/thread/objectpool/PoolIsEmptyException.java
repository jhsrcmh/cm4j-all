package com.cm4j.test.thread.objectpool;

public class PoolIsEmptyException extends Exception {

    public PoolIsEmptyException() {
        super();
    }

    public PoolIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolIsEmptyException(String message) {
        super(message);
    }

    public PoolIsEmptyException(Throwable cause) {
        super(cause);
    }

}
