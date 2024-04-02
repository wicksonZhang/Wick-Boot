package cn.wickson.security.commons.handler;

import cn.wickson.security.commons.constant.GlobalResultCodeConstants;
import cn.wickson.security.commons.enums.ResultCode;
import cn.wickson.security.commons.exception.ParameterException;
import cn.wickson.security.commons.exception.ServiceException;
import cn.wickson.security.commons.exception.UserOperationException;
import cn.wickson.security.commons.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 全局异常统一处理类
 *
 * @author ZhangZiHeng
 * @date 2023-12-27
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 路径变量异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingPathVariableException.class)
    public ResultUtil<?> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        log.error("requestUrl：{}，请求参数异常", request.getRequestURI(), e);
        return ResultUtil.failure(GlobalResultCodeConstants.PARAM_IS_BLANK);
    }

    /**
     * 参数类型错误异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultUtil<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("requestUrl：{}，请求参数错误", request.getRequestURI(), e);
        return ResultUtil.failure(GlobalResultCodeConstants.PARAM_TYPE_BIND_ERROR);
    }

    /**
     * 自定义用户操作异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserOperationException.class)
    public ResultUtil<?> handleUserOperationException(UserOperationException e, HttpServletRequest request) {
        log.error("requestUrl：{}，用户操作异常{code={}，message={}}", request.getRequestURI(), e.getCode(),
                e.getDescription());
        return ResultUtil.failure(e.getCode(), e.getDescription());
    }

    /**
     * 自定义用户操作异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServiceException.class)
    public ResultUtil<?> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error("requestUrl：{}，用户操作异常{code={}，message={}}", request.getRequestURI(), e.getCode(),
                e.getMessage());
        return ResultUtil.failure(e.getCode(), e.getMessage());
    }

    /**
     * 自定义参数错误异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ParameterException.class)
    public ResultUtil<?> handleParamException(ParameterException e, HttpServletRequest request) {
        log.error("requestUrl：{}，用户操作异常{code={}，message={}}", request.getRequestURI(), e.getCode(),
                e.getDescription(), e);
        return ResultUtil.failure(e.getCode(), e.getDescription());
    }

    /**
     * 不支持请求方法异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultUtil<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("requestUrl：{}，用户操作异常", request.getRequestURI(), e);
        String errorMessage = String.format("请求方法无效，不支持%s请求", request.getMethod());
        return ResultUtil.failure(GlobalResultCodeConstants.USER_REQUEST_METHOD_INVALID, errorMessage);
    }

    /**
     * 请求参数格式错误异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultUtil<?> handleHttpRequestMethodNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        log.error("requestUrl：{}，用户操作异常", request.getRequestURI(), e);
        return ResultUtil.failure(GlobalResultCodeConstants.PARAM_REQUEST_DATA_FORMAT_INVALID);
    }

    /**
     * 请求参数校验异常处理方式一
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultUtil<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("requestUrl：{}，参数校验失败", request.getRequestURI(), e);
        System.out.println(e.getMessage());
        return ResultUtil.failure(GlobalResultCodeConstants.PARAM_REQUEST_DATA_FORMAT_INVALID);
    }

    /**
     * 请求参数校验异常处理方式二
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultUtil<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("requestUrl：{}，参数校验失败", request.getRequestURI(), e);
        ResultCode resultCode = GlobalResultCodeConstants.PARAM_VALIDATED_FAILURE;
        String msg = this.messageFormat(resultCode.getMsg(), e.getFieldErrors());
        return ResultUtil.failure(resultCode, msg);
    }

    /**
     * 请求参数校验异常处理方式三
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public ResultUtil<?> handleBindException(BindException e, HttpServletRequest request) {
        log.error("requestUrl：{}，参数校验失败", request.getRequestURI(), e);
        ResultCode resultCode = GlobalResultCodeConstants.PARAM_IS_INVALID;
        String msg = this.messageFormat(resultCode.getMsg(), e.getFieldErrors());
        return ResultUtil.failure(GlobalResultCodeConstants.PARAM_IS_INVALID, msg);
    }

    /**
     * 请求参数校验异常处理方式四
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultUtil<?> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.error("requestUrl：{}，参数校验失败", request.getRequestURI(), e);
        ResultCode resultCode = GlobalResultCodeConstants.PARAM_IS_BLANK;
        String msg = resultCode.getMsg() + "[" + e.getMessage() + "]";
        return ResultUtil.failure(resultCode, msg);
    }

    /**
     * 将validator数据校验异常信息格式化处理
     */
    private String messageFormat(String topic, List<FieldError> fieldErrorList) {
        StringBuilder msg = new StringBuilder();
        msg.append(topic);
        msg.append("：[");
        fieldErrorList.forEach(fieldError -> {
            msg.append(fieldError.getField());
            msg.append("=");
            msg.append(fieldError.getDefaultMessage());
            msg.append(" ");
        });
        msg.append("]");
        return msg.toString();
    }

}
