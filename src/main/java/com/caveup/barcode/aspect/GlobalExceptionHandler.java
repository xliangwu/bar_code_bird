package com.caveup.barcode.aspect;


import com.caveup.barcode.result.ApiStatusCode;
import com.caveup.barcode.result.helper.ApiResultHelper;
import com.caveup.barcode.result.model.ApiResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;

/**
 * @author Vincent
 * @date 2018/11/28 16:47
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResultModel handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResultHelper.error(ApiStatusCode.UNKNOWN_ERROR.getCode(), ApiStatusCode.UNKNOWN_ERROR.getComments());
    }

    /**
     * 无效参数处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, ValidationException.class})
    @ResponseBody
    public ApiResultModel handleException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ApiResultHelper.error(ApiStatusCode.PARAM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ApiResultModel handleException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ApiResultHelper.error(ApiStatusCode.PARAM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 接口参数不符合标准
     *
     * @param e
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ApiResultModel handleException(ConstraintViolationException e) {
        log.error(e.getMessage());
        return ApiResultHelper.error(ApiStatusCode.PARAM_ERROR.getCode(), e.getMessage());
    }
}
