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
import tr.unvercanunlu.calculator_kafka.controller.IResultController;
import tr.unvercanunlu.calculator_kafka.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka.model.entity.Result;
import tr.unvercanunlu.calculator_kafka.service.IResultService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.RESULT_API)
public class ResultController implements IResultController {

    private final IResultService resultService;

    private final IKafkaProducer<String, Result> resultKafkaProducer;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Result>> getAll() {
        List<Result> resultList = this.resultService.getAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultList);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Result> get(@PathVariable(name = "id") UUID id) {
        Result result = this.resultService.get(id);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
        this.resultService.delete(id);

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @Override
    @RequestMapping(path = "/populate", method = RequestMethod.POST)
    public ResponseEntity<Void> populate() {
        List<Result> resultList = this.resultService.getAll();

        resultList.forEach(result -> this.resultKafkaProducer.send(result.getId().toString(), result));

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

}
