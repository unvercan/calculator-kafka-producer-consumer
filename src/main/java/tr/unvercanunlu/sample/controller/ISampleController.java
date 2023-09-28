package tr.unvercanunlu.sample.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.sample.model.entity.Sample;
import tr.unvercanunlu.sample.model.request.SampleRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface ISampleController {

    ResponseEntity<List<Sample>> getAll();

    ResponseEntity<Sample> get(@NotNull(message = "Id should not be null.") UUID id);

    ResponseEntity<Void> delete(@NotNull(message = "Id should not be null.") UUID id);

    ResponseEntity<Sample> add(
            @Valid
            @NotNull(message = "Request should have request body.")
            SampleRequest request
    );

    ResponseEntity<List<Sample>> randomize(
            @Positive(message = "Count should be positive integer.")
            @NotNull(message = "Count should not be null.")
            @NotBlank(message = "Count should not be empty.")
            @Min(value = 1, message = "Count should be at least one.")
            @Max(value = 1000, message = "Count should be at max one thousand.")
            @Digits(integer = 4, fraction = 0, message = "Count should be integer and at max one thousand.")
            String count
    );

    ResponseEntity<Void> populate();

}
