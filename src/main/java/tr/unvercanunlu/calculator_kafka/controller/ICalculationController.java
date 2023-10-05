package tr.unvercanunlu.calculator_kafka.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka.model.entity.Calculation;
import tr.unvercanunlu.calculator_kafka.model.request.CalculationRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface ICalculationController {

    ResponseEntity<List<Calculation>> getAll();

    ResponseEntity<Calculation> get(@NotNull(message = "Id should not be null.") UUID id);

    ResponseEntity<Void> create(
            @NotNull(message = "Calculation request should not be null.")
            @Valid
            CalculationRequest request);

    /*
    ResponseEntity<Void> randomize(
            @Positive(message = "Count should be positive integer.")
            @NotNull(message = "Count should not be null.")
            @NotBlank(message = "Count should not be empty.")
            @Min(value = 1, message = "Count should be at least one.")
            @Max(value = 1000, message = "Count should be at most one thousand.")
            @Digits(integer = 4, fraction = 0, message = "Count should be integer and at most one thousand.")
            String count);
     */

}
