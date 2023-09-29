package tr.unvercanunlu.async_calculator.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.async_calculator.model.entity.Operand;
import tr.unvercanunlu.async_calculator.model.request.OperandRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface IOperandController {

    ResponseEntity<List<Operand>> getAll();

    ResponseEntity<Operand> get(@NotNull(message = "Id should not be null.") UUID id);

    ResponseEntity<Void> delete(@NotNull(message = "Id should not be null.") UUID id);

    ResponseEntity<Operand> add(
            @Valid
            @NotNull(message = "Request should have request body.")
            OperandRequest request);

    ResponseEntity<List<Operand>> randomize(
            @Positive(message = "Count should be positive integer.")
            @NotNull(message = "Count should not be null.")
            @NotBlank(message = "Count should not be empty.")
            @Min(value = 1, message = "Count should be at least one.")
            @Max(value = 1000, message = "Count should be at most one thousand.")
            @Digits(integer = 4, fraction = 0, message = "Count should be integer and at most one thousand.")
            String count);

    ResponseEntity<Void> populate();

}
