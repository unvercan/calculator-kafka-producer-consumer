package tr.unvercanunlu.calculator_kafka.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.unvercanunlu.calculator_kafka.controller.ApiConfig;
import tr.unvercanunlu.calculator_kafka.controller.IOperandController;
import tr.unvercanunlu.calculator_kafka.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka.model.entity.Operand;
import tr.unvercanunlu.calculator_kafka.model.request.OperandRequest;
import tr.unvercanunlu.calculator_kafka.service.IOperandService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.OPERAND_API)
public class OperandController implements IOperandController {

    private final IKafkaProducer<String, Operand> operandKafkaProducer;

    private final IOperandService operandService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Operand>> getAll() {
        List<Operand> operandList = this.operandService.getAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operandList);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Operand> get(@PathVariable(name = "id") UUID id) {
        Operand operand = this.operandService.get(id);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operand);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
        this.operandService.delete(id);

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Operand> add(@RequestBody OperandRequest request) {
        Operand operand = this.operandService.add(request);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operand);
    }

    @Override
    @RequestMapping(path = "/random", method = RequestMethod.POST)
    public ResponseEntity<List<Operand>> randomize(@RequestParam(name = "count", required = false, defaultValue = "1") String countText) {
        int count;

        try {
            count = Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }

        if (count <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }

        List<Operand> operandList = this.operandService.randomize(count);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operandList);
    }

    @Override
    @RequestMapping(path = "/populate", method = RequestMethod.POST)
    public ResponseEntity<Void> populate() {
        List<Operand> operandList = this.operandService.getAll();

        operandList.forEach(operand -> this.operandKafkaProducer.send(operand.getId().toString(), operand));

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

}
