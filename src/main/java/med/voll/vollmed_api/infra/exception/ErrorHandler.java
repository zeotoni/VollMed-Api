package med.voll.vollmed_api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DataErrorValidation>> handleError400(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataErrorValidation::new).toList());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<List<DataErrorValidation>> handleDuplicateKeyError(DataIntegrityViolationException ex) {

        String field = "Unknown field";
        Throwable rootCause = ex.getRootCause();

        if (rootCause != null) {
            field = extractFieldFromErrorMessage(rootCause.getMessage());
        }

        String errorMessage = "Duplicate entry on field " + field;
        List<DataErrorValidation> errors = List.of(new DataErrorValidation(field, errorMessage));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    private String extractFieldFromErrorMessage(String message) {
        Pattern pattern = Pattern.compile("Duplicate entry '.*?' for key '.*?\\.(.*?)'");
        Matcher matcher = pattern.matcher(message);

        return matcher.find() ? matcher.group(1) : "unknown";
    }

    public record DataErrorValidation(String field, String message) {
        public DataErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
