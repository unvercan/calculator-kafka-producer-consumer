package tr.unvercanunlu.calculator_kafka_producer_consumer.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.dto.CalculationDto;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.request.CreateCalculationRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface ICalculationService {

    void create(
            @Valid
            @NotNull(message = "Create Calculation Request should not be null.")
            CreateCalculationRequest request);

    List<CalculationDto> retrieveAll();

    CalculationDto retrieve(@NotNull(message = "Calculation ID should not be null.") UUID calculationId);

    void setResult(
            @NotNull(message = "Calculation ID should not be null.") UUID calculationId,
            @NotNull(message = "Result should not be null.") Double result);

    void setCompleteness(
            @NotNull(message = "Calculation ID should not be null.") UUID calculationId,
            @NotNull(message = "Done should not be null.") Boolean done);

}
