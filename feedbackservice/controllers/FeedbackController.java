package feedbackservice.controllers;

import feedbackservice.dtos.FeedbackDto;
import feedbackservice.dtos.FeedbackPageDto;
import feedbackservice.dtos.NewFeedbackDto;
import feedbackservice.services.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<FeedbackDto> getFeedback(@PathVariable String id) {
        return ResponseEntity.ok().body(feedbackService.getFeedbackById(id));
    }

    @GetMapping("/feedback")
    public ResponseEntity<FeedbackPageDto> getAllFeedback(@RequestParam(name = "page", defaultValue = "1") int page,
                                                          @RequestParam(name = "perPage", defaultValue = "10") int perPage,
                                                          @RequestParam(name = "customer", required = false) String customer,
                                                          @RequestParam(name = "rating", required = false, defaultValue = "0") int rating,
                                                          @RequestParam(name = "product", required = false) String product,
                                                          @RequestParam(name = "vendor", required = false) String vendor) {
        return ResponseEntity.ok().body(feedbackService.getAllFeedback(page,
                perPage,
                customer,
                rating,
                product,
                vendor));
    }

    @PostMapping("/feedback")
    public ResponseEntity<String> feedback(@RequestBody @Valid NewFeedbackDto newFeedbackDto) {
        String location = "/feedback/" + feedbackService.addFeedback(newFeedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", location).body(location);
    }
}
