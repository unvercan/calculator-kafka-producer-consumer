package tr.unvercanunlu.compare_match.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleRequest {

    @Positive(message = "First value should be positive integer.")
    @Digits(integer = 2, fraction = 0, message = "First value should be integer.")
    @NotNull(message = "First value cannot be null.")
    @Min(value = 1, message = "First value should be at least one.")
    @Max(value = 10, message = "First value should be at most ten.")
    private Integer first;

    @Positive(message = "Second value should be positive integer.")
    @Digits(integer = 2, fraction = 0, message = "Second value should be integer.")
    @NotNull(message = "Second value cannot be null.")
    @Min(value = 1, message = "Second value should be at least one.")
    @Max(value = 10, message = "Second value should be at most ten.")
    private Integer second;

}
