package tr.unvercanunlu.compare_match.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.unvercanunlu.compare_match.controller.ISampleController;
import tr.unvercanunlu.compare_match.dao.ISampleRepository;
import tr.unvercanunlu.compare_match.entity.Sample;
import tr.unvercanunlu.compare_match.request.SampleRequest;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/" + "api" + "/" + "v1" + "/" + "sample")
public class SampleController implements ISampleController {

    private static final Random random = new Random();

    private static final Supplier<Integer> randomGenerator = () -> 1 + random.nextInt(10);

    private final ISampleRepository sampleRepository;

    @Override
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Sample>> getAll() {
        List<Sample> sampleList = this.sampleRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sampleList);
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Sample> get(@PathVariable(name = "id") UUID id) {
        Optional<Sample> optionalSample = this.sampleRepository.findById(id);

        ResponseEntity<Sample> response;

        if (optionalSample.isPresent()) {
            Sample sample = optionalSample.get();

            response = ResponseEntity.status(HttpStatus.OK.value())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(sample);
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }

        return response;
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
        Optional<Sample> optionalSample = this.sampleRepository.findById(id);

        ResponseEntity<Void> response;

        if (optionalSample.isPresent()) {
            this.sampleRepository.deleteById(id);

            response = ResponseEntity.status(HttpStatus.OK.value()).build();
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }

        return response;
    }

    @Override
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Sample> add(@RequestBody SampleRequest request) {
        Sample sample = Sample.builder()
                .first(request.getFirst())
                .second(request.getSecond())
                .build();

        sample = this.sampleRepository.save(sample);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sample);
    }

    @Override
    @RequestMapping(path = "/random", method = RequestMethod.POST)
    public ResponseEntity<List<Sample>> randomize(
            @RequestParam(name = "count", required = false, defaultValue = "1") String countText
    ) {
        Integer count;

        try {
            count = Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }

        if (count <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).build();
        }

        List<Sample> sampleList = IntStream.range(0, count)
                .mapToObj(i -> Sample.builder()
                        .first(randomGenerator.get())
                        .second(randomGenerator.get())
                        .build()
                ).toList();

        sampleList = sampleRepository.saveAll(sampleList);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sampleList);
    }

}
