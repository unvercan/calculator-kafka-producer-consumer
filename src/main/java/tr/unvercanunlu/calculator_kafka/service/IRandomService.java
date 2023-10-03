package tr.unvercanunlu.calculator_kafka.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IRandomService {

    Integer generate(
            @Positive(message = "Start of gap should be positive integer.")
            @NotNull(message = "Start of gap should not be null.")
            @Min(value = 0, message = "Start of gap should be at least zero.")
            Integer gapStart,
            @Positive(message = "End of gap should be positive integer.")
            @NotNull(message = "End of gap should not be null.")
            @Min(value = 1, message = "End of gap should be at least one.")
            Integer gapEnd);

}
