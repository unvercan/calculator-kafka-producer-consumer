package tr.unvercanunlu.sample.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.unvercanunlu.sample.config.ApiConfig;
import tr.unvercanunlu.sample.controller.ISumController;
import tr.unvercanunlu.sample.model.entity.Sum;
import tr.unvercanunlu.sample.repository.ISumRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.SUM_API)
public class SumController implements ISumController {

    private final ISumRepository sumRepository;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Sum>> getAll() {
        List<Sum> sumList = this.sumRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(sumList);
    }

}
