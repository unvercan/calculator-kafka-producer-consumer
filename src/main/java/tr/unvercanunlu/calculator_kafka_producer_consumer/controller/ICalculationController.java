package tr.unvercanunlu.calculator_kafka_producer_consumer.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.dto.CalculationDto;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.request.CreateCalculationRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface ICalculationController {

    ResponseEntity<Void> create(
            @Valid
            @NotNull(message = "Create Calculation Request should not be null.")
            CreateCalculationRequest request);

    ResponseEntity<List<CalculationDto>> retrieveAll();

    ResponseEntity<CalculationDto> retrieve(@NotNull(message = "Calculation ID should not be null.") UUID calculationId);

}
