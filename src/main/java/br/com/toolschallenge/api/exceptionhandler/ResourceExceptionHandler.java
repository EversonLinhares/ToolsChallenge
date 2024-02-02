package br.com.toolschallenge.api.exceptionhandler;

import br.com.toolschallenge.domain.exception.DuplicatedObjectException;
import br.com.toolschallenge.domain.exception.NegocioException;
import br.com.toolschallenge.domain.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErros(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map( objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(messages);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
        String msg = e.getMessage();
        ApiErrors apiErrors = new ApiErrors(msg);
        return new ResponseEntity(apiErrors, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicatedObjectException.class)
    public ResponseEntity<String> duplicatedObjectException(DuplicatedObjectException e){
        String msg = e.getMessage();
        ApiErrors apiErrors = new ApiErrors(msg);
        return new ResponseEntity(apiErrors,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> negocioException(NegocioException e){
        String msg = e.getMessage();
        ApiErrors apiErrors = new ApiErrors(msg);
        return new ResponseEntity(apiErrors,HttpStatus.BAD_REQUEST);
    }
}
