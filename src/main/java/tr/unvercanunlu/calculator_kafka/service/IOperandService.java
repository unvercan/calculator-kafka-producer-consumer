package tr.unvercanunlu.calculator_kafka.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka.model.entity.Operand;

import java.util.List;
import java.util.UUID;

@Validated
public interface IOperandService {

    List<Operand> getAll();

    Operand get(@NotNull(message = "Id should not be null.") UUID id);

    Operand create(
            @NotNull(message = "Operand request should not be null.")
            @Valid
            Operand operand);

}
