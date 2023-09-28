package tr.unvercanunlu.sample.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.util.List;
import java.util.UUID;

@Validated
public interface ISumController {

    ResponseEntity<List<Sum>> getAll();

    ResponseEntity<Sum> get(@NotNull(message = "Id should not be null.") UUID id);

    ResponseEntity<Void> delete(@NotNull(message = "Id should not be null.") UUID id);

    ResponseEntity<Void> populate();

}
