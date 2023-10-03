package tr.unvercanunlu.calculator_kafka.repository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka.model.entity.Calculation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
public interface ICalculationRepository {

    UUID create(
            @NotNull(message = "Calculation should not be null.")
            @Valid
            Calculation calculation);

    Calculation update(
            @NotNull(message = "Id should not be null.")
            UUID id,
            @NotNull(message = "Calculation should not be null.")
            @Valid
            Calculation calculation);

    Optional<Calculation> find(@NotNull(message = "Id should not be null.") UUID id);

    List<Calculation> findAll();

    void delete(@NotNull(message = "Id should not be null.") UUID id);

}
