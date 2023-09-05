package uz.pdp.uzummarket.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.exception.DataAlreadyExistsException;
import uz.pdp.uzummarket.exception.DataNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<BaseResponse<DataNotFoundException>> dataNotfound(DataNotFoundException e){
//        ErrorDto errorDto = new ErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(404).body(
                BaseResponse.<DataNotFoundException>builder()
                        .message("not found")
                        .success(false)
                        .code(404)
                        .data(e)
                        .build()
        );
    }
    @ExceptionHandler(value = DataAlreadyExistsException.class)
    public ResponseEntity<DataAlreadyExistsException> dataAlreadyException(DataAlreadyExistsException e){
//        ErrorDto errorDto = new ErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(500).body(e);
    }
}
