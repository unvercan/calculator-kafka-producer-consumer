package tr.unvercanunlu.calculator_kafka_prod_cons.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.calculator_kafka_prod_cons.controller.ApiConfig;
import tr.unvercanunlu.calculator_kafka_prod_cons.controller.IOperationController;
import tr.unvercanunlu.calculator_kafka_prod_cons.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka_prod_cons.repository.IOperationRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.OPERATION_API)
public class OperationController implements IOperationController {

    private final IOperationRepository operationRepository;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Operation>> getAll() {
        List<Operation> operations = this.operationRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operations);
    }

    @Override
    @RequestMapping(path = "/{code}", method = RequestMethod.GET)
    public ResponseEntity<Operation> get(@PathVariable(name = "code") Integer code) {
        Operation operation = this.operationRepository.findById(code)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operation);
    }
}
