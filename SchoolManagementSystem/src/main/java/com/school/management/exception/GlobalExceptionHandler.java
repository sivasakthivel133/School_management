package com.school.management.exception;

import com.school.management.dto.ResponseDTO;
import com.school.management.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestServiceAlertException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestServiceAlertException(BadRequestServiceAlertException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatusValue(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return ResponseEntity.ok().body(responseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleSecurityException(Exception exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatusValue(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }
}