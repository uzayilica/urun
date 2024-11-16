package com.uzay.urun.exception;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {

 @ExceptionHandler(UrunNotFoundException.class)
 public ResponseEntity<Object> handleUrunNotFoundException(UrunNotFoundException exception) {
     ErrorResponse errorResponse = ErrorResponse
             .builder()
             .message(exception.getMessage())
             .errorStatusCode("404 not found")
             .timestamp(LocalDateTime.now())
             .build();

     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
 }


 @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
     ArrayList<Map> hatalistesi = new ArrayList<>();
     HashMap<String, Object> map = new HashMap<>();
     //! Hata 1'den çok olabilir
     List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
  for (FieldError field : fieldErrors) {
          map.put("hatalı alan",field.getField());
          map.put("mesaj", field.getDefaultMessage());
          map.put("statusCode", field.getCode());
          map.put("rejectedValue", field.getRejectedValue());
          hatalistesi.add(map);
  }
  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hatalistesi);
 }


 @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException exception  ) {
     return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
 }





}
