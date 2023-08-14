package uz.pdp.uzummarket.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.uzummarket.ErrorDto;
import uz.pdp.uzummarket.exception.DataNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ErrorDto> datanotfound(DataNotFoundException e){
        ErrorDto errorDto = new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(errorDto.status()).body(errorDto);
    }
}
