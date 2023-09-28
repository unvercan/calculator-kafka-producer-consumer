package tr.unvercanunlu.sample.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.sample.model.entity.Sample;
import tr.unvercanunlu.sample.model.request.SampleRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface ISampleService {

    List<Sample> getAll();

    Sample get(@NotNull(message = "Id should not be null.") UUID id);

    void delete(@NotNull(message = "Id should not be null.") UUID id);

    Sample add(@NotNull(message = "Sample should not be null.") @Valid SampleRequest request);

    List<Sample> randomize(
            @Positive(message = "Count should be positive integer.")
            @NotNull(message = "Count should not be null.")
            @Min(value = 1, message = "Count should be at least one.")
            @Max(value = 1000, message = "Count should be at most one thousand.")
            @Digits(integer = 4, fraction = 0, message = "Count should be integer and at most one thousand.")
            Integer count);

}
