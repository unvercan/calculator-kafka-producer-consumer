package tr.unvercanunlu.calculator_kafka_prod_cons.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.calculator_kafka_prod_cons.controller.ApiConfig;
import tr.unvercanunlu.calculator_kafka_prod_cons.controller.ICalculationController;
import tr.unvercanunlu.calculator_kafka_prod_cons.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka_prod_cons.model.entity.Calculation;
import tr.unvercanunlu.calculator_kafka_prod_cons.model.request.CalculationRequest;
import tr.unvercanunlu.calculator_kafka_prod_cons.repository.ICalculationRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.CALCULATION_API)
public class CalculationController implements ICalculationController {

    private final ICalculationRepository calculationRepository;

    private final IKafkaProducer<UUID, Calculation> calculationKafkaProducer;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Calculation>> getAll() {
        List<Calculation> calculations = this.calculationRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(calculations);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Calculation> get(@PathVariable(name = "id") UUID id) {
        Calculation calculation = this.calculationRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(calculation);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody CalculationRequest request) {
        System.out.println(request + " is received as request.");

        Calculation calculation = Calculation.builder()
                .first(request.getFirst())
                .second(request.getSecond())
                .operationCode(request.getOperationCode())
                .build();

        System.out.println(calculation + " is created.");

        calculation = this.calculationRepository.save(calculation);

        System.out.println(calculation + " is saved to database.");

        System.out.println(calculation + " is sending to Kafka.");

        this.calculationKafkaProducer.send(calculation.getId(), calculation);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
