package tr.unvercanunlu.calculator_kafka.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka.model.entity.Operand;

import java.util.List;
import java.util.UUID;

@Validated
public interface IOperandController {

    ResponseEntity<List<Operand>> getAll();

    ResponseEntity<Operand> get(@NotNull(message = "Id should not be null.") UUID id);

    ResponseEntity<Void> populate();

}
