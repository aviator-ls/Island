package com.aviator.island.exception;

import com.aviator.island.constants.ResponseCode;
import com.aviator.island.entity.ResponseContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

/**
 * Created by aviator_ls on 2018/8/8.
 */
@ControllerAdvice(basePackages = {"com.aviator.island.controller"})
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    protected ResponseContent assemblyResponseBody(ResponseCode responseCode) {
        ResponseContent responseContent = new ResponseContent();
        responseContent.setResponseCode(responseCode.getCode());
        responseContent.setResponseMsg(responseCode.getMsg());
        return responseContent;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseContent handleInvokeException(Exception e) {
        if (e instanceof HttpMessageNotReadableException) {
            logger.error("bad request!", e);
            return this.assemblyResponseBody(ResponseCode.BAD_REQUEST);
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            logger.error("not supported media type!", e);
            return this.assemblyResponseBody(ResponseCode.NOT_SUPPORT_MEDIA_TYPE);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            logger.error("request method mot match!", e);
            return this.assemblyResponseBody(ResponseCode.METHOD_NOT_SUPPORTED);
        } else if (e instanceof IllegalArgumentException) {
            logger.error("illegal request param!", e);
            return this.assemblyResponseBody(ResponseCode.ILLEGAL_PARAM);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            logger.error("method argument type mismatch!", e);
            return this.assemblyResponseBody(ResponseCode.METHOD_ARGUMENT_TYPE_MISMATCH);
        } else if (e instanceof ParamsErrorException) {
            logger.error(e.getMessage(), e);
            return this.assemblyResponseBody(ResponseCode.PARAMS_ERROR);
        } else if (e instanceof ConstraintViolationException) {
            logger.error("requestParam or pathVariable error!", e);
            return this.assemblyResponseBody(ResponseCode.PARAMS_ERROR);
        } else {
            logger.error("merge into sys error!", e);
            return this.assemblyResponseBody(ResponseCode.SYS_ERROR);
        }
    }

}
