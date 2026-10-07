package feedbackservice.advices;

import feedbackservice.dtos.FeedbackDto;
import feedbackservice.exceptions.FeedbackNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FeedbackControllerAdvice {
    @ExceptionHandler(FeedbackNotFoundException.class)
    public ResponseEntity<FeedbackDto> handleException(Exception ex){
        return ResponseEntity.notFound().build();
    }
}
