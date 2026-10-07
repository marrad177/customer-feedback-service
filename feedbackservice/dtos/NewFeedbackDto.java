package feedbackservice.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public record NewFeedbackDto(@Range(min = 1, max = 5) int rating,
                             String feedback,
                             String customer,
                             @NotBlank String product,
                             @NotBlank String vendor) {

    public NewFeedbackDto(int rating, String feedback, String customer, String product, String vendor) {
        this.rating = rating;
        this.feedback = feedback;
        this.customer = customer;
        this.product = product;
        this.vendor = vendor;
    }
}
