package tr.unvercanunlu.sample.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.unvercanunlu.sample.controller.ApiConfig;
import tr.unvercanunlu.sample.controller.ISampleController;
import tr.unvercanunlu.sample.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.sample.model.entity.Sample;
import tr.unvercanunlu.sample.model.request.SampleRequest;
import tr.unvercanunlu.sample.service.ISampleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.SAMPLE_API)
public class SampleController implements ISampleController {

    private final IKafkaProducer<String, Sample> sampleKafkaProducer;

    private final ISampleService sampleService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Sample>> getAll() {
        List<Sample> sampleList = this.sampleService.getAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sampleList);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Sample> get(@PathVariable(name = "id") UUID id) {
        Sample sample = this.sampleService.get(id);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sample);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
        this.sampleService.delete(id);

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Sample> add(@RequestBody SampleRequest request) {
        Sample sample = this.sampleService.add(request);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sample);
    }

    @Override
    @RequestMapping(path = "/random", method = RequestMethod.POST)
    public ResponseEntity<List<Sample>> randomize(@RequestParam(name = "count", required = false, defaultValue = "1") String countText) {
        int count;

        try {
            count = Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }

        if (count <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }

        List<Sample> sampleList = this.sampleService.randomize(count);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sampleList);
    }

    @Override
    @RequestMapping(path = "/populate", method = RequestMethod.POST)
    public ResponseEntity<Void> populate() {
        List<Sample> sampleList = this.sampleService.getAll();

        sampleList.forEach(sample -> this.sampleKafkaProducer.send(sample.getId().toString(), sample));

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

}
