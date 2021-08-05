package br.com.caelum.carangobom.controllers.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.caelum.carangobom.data.DTO.FieldErrorDTO;
import br.com.caelum.carangobom.data.DTO.JsonErrorDTO;

public interface IFormValidation {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public default List<FieldErrorDTO> validate(MethodArgumentNotValidException exception) {
        return exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ex -> new FieldErrorDTO(ex.getField(), ex.getDefaultMessage()))
                .collect(Collectors.toList());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public default JsonErrorDTO validate(HttpMessageNotReadableException exception) {
        return new JsonErrorDTO(exception.getCause().getLocalizedMessage());
    }
}
