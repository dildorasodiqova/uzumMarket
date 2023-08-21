package uz.pdp.uzummarket.Dto.errorDto;

import org.springframework.http.HttpStatus;

public record ErrorDto(String message, Integer status) {

}
