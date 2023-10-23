package tr.unvercanunlu.calculator_kafka_producer_consumer.controller.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.unvercanunlu.calculator_kafka_producer_consumer.controller.ApiConfig;
import tr.unvercanunlu.calculator_kafka_producer_consumer.controller.ICalculationController;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.dto.CalculationDto;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.request.CreateCalculationRequest;
import tr.unvercanunlu.calculator_kafka_producer_consumer.service.ICalculationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.CALCULATION_API)
public class CalculationController implements ICalculationController {

    private final Logger logger = LoggerFactory.getLogger(CalculationController.class);

    private final ICalculationService calculationService;

    @Override
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateCalculationRequest request) {
        this.logger.info("Create Calculation Request is received.");

        this.logger.debug("Received Create Calculation Request: " + request);

        this.calculationService.create(request);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CalculationDto>> retrieveAll() {
        this.logger.info("Retrieve ALl Calculations Request is received.");

        List<CalculationDto> calculationDtoList = this.calculationService.retrieveAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(calculationDtoList);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<CalculationDto> retrieve(@PathVariable(name = "id") UUID calculationId) {
        this.logger.info("Retrieve Calculation with '" + calculationId + "' ID is received.");

        CalculationDto calculationDto = this.calculationService.retrieve(calculationId);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(calculationDto);
    }
}
