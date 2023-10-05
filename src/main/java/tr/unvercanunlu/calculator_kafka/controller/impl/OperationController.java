package tr.unvercanunlu.calculator_kafka.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.unvercanunlu.calculator_kafka.controller.ApiConfig;
import tr.unvercanunlu.calculator_kafka.controller.IOperationController;
import tr.unvercanunlu.calculator_kafka.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka.service.IOperationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.OPERATION_API)
public class OperationController implements IOperationController {

    private final IOperationService operationService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Operation>> getAll() {
        List<Operation> operations = this.operationService.getAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operations);
    }

    @Override
    @RequestMapping(path = "/{code}", method = RequestMethod.GET)
    public ResponseEntity<Operation> get(@PathVariable(name = "code") Integer code) {
        Operation operation = this.operationService.get(code);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operation);
    }

}
