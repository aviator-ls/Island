package com.aviator.island.exception;

/**
 * Created by aviator_ls on 2018/8/28.
 */
public class UploadStaticException extends HelloException {

    public UploadStaticException() {
    }

    public UploadStaticException(Throwable cause) {
        super(cause);
    }

    public UploadStaticException(String message) {
        super(message);
    }

    public UploadStaticException(String message, Throwable cause) {
        super(message, cause);
    }
}
