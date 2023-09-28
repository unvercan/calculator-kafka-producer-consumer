package tr.unvercanunlu.sample.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.unvercanunlu.sample.controller.ApiConfig;
import tr.unvercanunlu.sample.controller.ISumController;
import tr.unvercanunlu.sample.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.sample.model.entity.Sum;
import tr.unvercanunlu.sample.service.ISumService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.SUM_API)
public class SumController implements ISumController {

    private final ISumService sumService;

    private final IKafkaProducer<String, Sum> sumKafkaProducer;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Sum>> getAll() {
        List<Sum> sumList = this.sumService.getAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sumList);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Sum> get(@PathVariable(name = "id") UUID id) {
        Sum sum = this.sumService.get(id);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sum);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
        this.sumService.delete(id);

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @Override
    @RequestMapping(path = "/populate", method = RequestMethod.POST)
    public ResponseEntity<Void> populate() {
        List<Sum> sumList = this.sumService.getAll();

        sumList.forEach(sum -> this.sumKafkaProducer.send(sum.getId().toString(), sum));

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

}
