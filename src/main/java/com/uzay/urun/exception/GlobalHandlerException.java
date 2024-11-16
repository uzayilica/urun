package com.uzay.urun.exception;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

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
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(DataIntegrityViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        Set<String> errors = new HashSet<>();

        String errorMessage = ex.getRootCause().getMessage().toLowerCase();
        System.out.println("Debug - Gelen hata mesajı: " + errorMessage);

        // Hata mesajlarını daha ayrıntılı kontrol edelim
        if (errorMessage.contains("unique constraint") && errorMessage.contains("ad")) {
            errors.add("Bu ürün adı zaten kullanımda");
        }

        if (errorMessage.contains("unique constraint") && errorMessage.contains("aciklama")) {
            errors.add("Bu açıklama zaten kullanımda");
        }

        // Hiçbir spesifik hata bulunamadıysa
        if (errors.isEmpty()) {
            errors.add("Veritabanı kısıtlama hatası oluştu");
            System.out.println("Yakalanmayan hata mesajı: " + errorMessage);
        }

        response.put("timestamp", new Date());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", new ArrayList<>(errors));
        response.put("message", String.join(", ", errors));

        // Debug - oluşturulan hataları kontrol edelim
        System.out.println("Oluşturulan hatalar: " + errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}



