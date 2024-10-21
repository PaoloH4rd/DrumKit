//package org.paolo.drumkit_.exception;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.sql.SQLIntegrityConstraintViolationException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class GestoreException {
//
//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    public ResponseEntity<String> sqlIntegrityConstraintViolationExceptionHandler
//            (SQLIntegrityConstraintViolationException ex){
//        String message=ex.getMessage();
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>>
//    methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
//        Map<String, String> miaMappa=new HashMap<>();
//        for(FieldError f:ex.getFieldErrors()) {
//            String nomeCampo=f.getField();
//            String errore=f.getDefaultMessage();
//            miaMappa.put(nomeCampo, errore);
//        }
//        return ResponseEntity.badRequest().body(miaMappa);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Map<String, String>> constraintViolationExceptionHandler(ConstraintViolationException ex){
//        Map<String, String> miaMappa=new HashMap<>();
//        for(ConstraintViolation<?> cv:ex.getConstraintViolations()) {
//            String nomeCampo=cv.getPropertyPath().toString();
//            String errore=cv.getMessage();
//            miaMappa.put(nomeCampo, errore);
//        }
//        return ResponseEntity.badRequest().body(miaMappa);
//    }
//
//
//}
