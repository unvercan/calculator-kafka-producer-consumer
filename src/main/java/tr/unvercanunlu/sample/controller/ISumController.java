package tr.unvercanunlu.sample.controller;

import org.springframework.http.ResponseEntity;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.util.List;

public interface ISumController {

    ResponseEntity<List<Sum>> getAll();

}
