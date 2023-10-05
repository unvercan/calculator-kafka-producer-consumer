package tr.unvercanunlu.calculator_kafka.controller;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka.model.entity.Operation;

import java.util.List;

@Validated
public interface IOperationController {

    ResponseEntity<List<Operation>> getAll();

    ResponseEntity<Operation> get(
            @NotNull(message = "Code should not be null.")
            @Min(value = 0, message = "Code should be at least zero.")
            @Max(value = 7, message = "Code should be at most seven.")
            @Digits(integer = 1, fraction = 0, message = "Code should be integer and has at most one digit.")
            Integer code);

}
