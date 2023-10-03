package tr.unvercanunlu.calculator_kafka.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka.model.entity.Calculation;

import java.util.List;
import java.util.UUID;

@Validated
public interface ICalculationService {

    List<Calculation> getAll();

    Calculation get(@NotNull(message = "Id should not be null.") UUID id);

    Calculation create(
            @NotNull(message = "Operand request should not be null.")
            @Valid
            Calculation calculation);

    List<Calculation> randomize(
            @Positive(message = "Count should be positive integer.")
            @NotNull(message = "Count should not be null.")
            @Min(value = 1, message = "Count should be at least one.")
            @Max(value = 1000, message = "Count should be at most one thousand.")
            @Digits(integer = 4, fraction = 0, message = "Count should be integer and at most one thousand.")
            Integer count);

}
