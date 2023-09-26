package tr.unvercanunlu.compare_match.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.compare_match.entity.Sample;
import tr.unvercanunlu.compare_match.request.SampleRequest;

import java.util.List;
import java.util.UUID;

@Validated
public interface ISampleController {

    ResponseEntity<List<Sample>> getAll();

    ResponseEntity<Sample> get(@NotNull UUID id);

    ResponseEntity<Void> delete(@NotNull UUID id);

    ResponseEntity<Sample> add(@Valid @NotNull SampleRequest request);

    ResponseEntity<List<Sample>> randomize(@NotNull @NotBlank String count);

}
