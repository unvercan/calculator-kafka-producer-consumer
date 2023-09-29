package tr.unvercanunlu.calculator_kafka.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka.model.entity.Operand;
import tr.unvercanunlu.calculator_kafka.model.request.OperandRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface IOperandService {

    List<Operand> getAll();

    Operand get(@NotNull(message = "Id should not be null.") UUID id);

    void delete(@NotNull(message = "Id should not be null.") UUID id);

    Operand add(@NotNull(message = "Operand request should not be null.") @Valid OperandRequest request);

    List<Operand> randomize(
            @Positive(message = "Count should be positive integer.")
            @NotNull(message = "Count should not be null.")
            @Min(value = 1, message = "Count should be at least one.")
            @Max(value = 1000, message = "Count should be at most one thousand.")
            @Digits(integer = 4, fraction = 0, message = "Count should be integer and at most one thousand.")
            Integer count);

}
