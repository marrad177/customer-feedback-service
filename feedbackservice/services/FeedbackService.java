package feedbackservice.services;

import feedbackservice.dtos.FeedbackDto;
import feedbackservice.dtos.FeedbackPageDto;
import feedbackservice.dtos.NewFeedbackDto;
import feedbackservice.entitites.Feedback;
import feedbackservice.exceptions.FeedbackNotFoundException;
import feedbackservice.repositories.FeedbackRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private final int defaultPage = 1;
    private final int defaultPerPage = 10;
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public FeedbackDto getFeedbackById(String id) {
        if (feedbackRepository.findById(id).isEmpty()) {
            throw new FeedbackNotFoundException("Feedback with id " + id + " not found");
        }
        Feedback feedback = feedbackRepository.findById(id).get();
        return new FeedbackDto(feedback.getId(),
                feedback.getRating(),
                feedback.getFeedback(),
                feedback.getCustomer(),
                feedback.getProduct(),
                feedback.getVendor());
    }

    public FeedbackPageDto getAllFeedback(int page, int perPage,
                                          String customer, int rating, String product, String vendor) {
        int requestedPage = page < 0 ? defaultPage - 1 : page - 1;
        int requestedPerPage = perPage < 5 || perPage > 20 ? defaultPerPage : perPage;

        Feedback feedbackExample = new Feedback();
        feedbackExample.setCustomer(customer);
        feedbackExample.setRating(rating);
        feedbackExample.setProduct(product);
        feedbackExample.setVendor(vendor);

        ExampleMatcher matcher;
        if (rating == 0) {
            matcher = ExampleMatcher
                    .matching()
                    .withIgnoreNullValues()
                    .withIgnoreCase(true)
                    .withIgnorePaths("rating");
        } else {
            matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase(true);
        }
        Example<Feedback> example = Example.of(feedbackExample, matcher);

        Pageable pageable = PageRequest.of(requestedPage, requestedPerPage, Sort.by("id").descending());
        Page<Feedback> feedbacks = feedbackRepository.findAll(example, pageable);
        FeedbackDto[] feedbacksArray = feedbacks
                .stream()
                .map(feedback -> new FeedbackDto(
                        feedback.getId(),
                        feedback.getRating(),
                        feedback.getFeedback(),
                        feedback.getCustomer(),
                        feedback.getProduct(),
                        feedback.getVendor()))
                .toArray(FeedbackDto[]::new);
        return new FeedbackPageDto(feedbacks.getTotalElements(), feedbacks.isFirst(), feedbacks.isLast(), feedbacksArray);
    }

    public String addFeedback(NewFeedbackDto newFeedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setRating(newFeedbackDto.rating());
        feedback.setFeedback(newFeedbackDto.feedback());
        feedback.setCustomer(newFeedbackDto.customer());
        feedback.setProduct(newFeedbackDto.product());
        feedback.setVendor(newFeedbackDto.vendor());
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return savedFeedback.getId();
    }
}
