/**
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.geekidea.springbootplus.common.exception;

import com.alibaba.fastjson.JSON;
import io.geekidea.springbootplus.common.api.ApiCode;
import io.geekidea.springbootplus.common.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author geekidea
 * @date 2018-11-08
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 非法参数验证异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResult handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> list = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            list.add(fieldError.getDefaultMessage());
        }
        Collections.sort(list);
        log.error("fieldErrors"+ JSON.toJSONString(list));
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION,list);
    }

    /**
     * 系统登录异常处理
     * @param exception
     * @return
     */
//    @ExceptionHandler(value = SysLoginException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResult sysLoginExceptionHandler(SysLoginException exception) {
//        log.warn("系统登录异常:" + exception.getMessage());
//        return new ApiResult(500,exception.getMessage());
//    }


    /**
     * 默认的异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exceptionHandler(Exception exception) {
        log.error("exception:",exception);
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, ApiCode.SYSTEM_EXCEPTION);
    }

    /**
     * HTTP解析请求参数异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("httpMessageNotReadableException:",exception);
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, ApiCode.PARAMETER_PARSE_EXCEPTION);
    }

    /**
     * TODO
     * HTTP
     * @param exception
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult httpMediaTypeException(HttpMediaTypeException exception) {
        log.error("httpMediaTypeException:",exception);
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, ApiCode.HTTP_MEDIA_TYPE_EXCEPTION);
    }
}
