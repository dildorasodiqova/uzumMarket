package uz.pdp.uzummarket;

import org.springframework.http.HttpStatus;

public record ErrorDto(String message, Integer status) {
}
