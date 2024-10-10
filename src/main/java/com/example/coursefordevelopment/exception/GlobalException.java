package com.example.coursefordevelopment.exception;

import com.example.coursefordevelopment.dto.Request.ApiRespone;
import com.example.coursefordevelopment.dto.Request.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    //bắt lỗi chưa được setup
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiRespone> handLingRunTimeException(RuntimeException exception)
    {
        ApiRespone apiRespone = new ApiRespone();

        apiRespone.setCode(9999);
        apiRespone.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }

    //lỗi được setup sẽ trả về
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespone> handLingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiRespone apiRespone= new ApiRespone();

        apiRespone.setCode(errorCode.getCode());
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRespone> handLingValidation(MethodArgumentNotValidException exception) //trả lại validation
    {
        String enumkey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumkey);
        }catch (IllegalArgumentException e)
        {

        }
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setCode(errorCode.getCode());
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }
}
