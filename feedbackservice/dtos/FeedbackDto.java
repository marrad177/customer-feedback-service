package feedbackservice.dtos;

import org.springframework.data.mongodb.core.mapping.Field;

public record FeedbackDto(String id,
                          int rating,
                          @Field(write = Field.Write.ALWAYS) String feedback,
                          @Field(write = Field.Write.ALWAYS) String customer,
                          String product,
                          String vendor) {
    public FeedbackDto(String id, int rating, String feedback, String customer, String product, String vendor) {
        this.id = id;
        this.rating = rating;
        this.feedback = feedback;
        this.customer = customer;
        this.product = product;
        this.vendor = vendor;
    }
}
