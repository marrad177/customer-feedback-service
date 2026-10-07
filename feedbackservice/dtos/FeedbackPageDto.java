package feedbackservice.dtos;

public record FeedbackPageDto(long total_documents,
                              boolean is_first_page,
                              boolean is_last_page,
                              FeedbackDto[] documents) {
}
