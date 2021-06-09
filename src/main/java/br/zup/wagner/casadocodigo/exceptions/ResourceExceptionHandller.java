package br.zup.wagner.casadocodigo.exceptions;

// classe responsavel por intercepptar exceçoes, tratar e devolver respostas amigaveis ao cliente

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandller {

    // capturando exceptions de validação

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validatiom(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Validation exception!",
                e.getMessage(), request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            error.addErro(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }

    // capturar exception de validação do email unico

    @ExceptionHandler(ExceptionGenericValidation.class)
    public ResponseEntity<ValidationError> entityNotFound(ExceptionGenericValidation e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Campo unico",
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    // metodo para tratamento de erro recurso não encontrado / resposta 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ValidationError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "recurso não encontrado",
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
