package tr.unvercanunlu.calculator_kafka.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.unvercanunlu.calculator_kafka.controller.ApiConfig;
import tr.unvercanunlu.calculator_kafka.controller.ICalculationController;
import tr.unvercanunlu.calculator_kafka.model.entity.Calculation;
import tr.unvercanunlu.calculator_kafka.model.request.CalculationRequest;
import tr.unvercanunlu.calculator_kafka.service.ICalculationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.CALCULATION_API)
public class CalculationController implements ICalculationController {

    private final ICalculationService calculationService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Calculation>> getAll() {
        List<Calculation> calculations = this.calculationService.getAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(calculations);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Calculation> get(@PathVariable(name = "id") UUID id) {
        Calculation calculation = this.calculationService.get(id);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(calculation);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody CalculationRequest request) {
        System.out.println(request + " is received as request.");

        this.calculationService.create(request);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /*
    @Override
    public ResponseEntity<Void> randomize(String count) {
        this.calculationService.randomize(Integer.parseInt(count));

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
    */

}
