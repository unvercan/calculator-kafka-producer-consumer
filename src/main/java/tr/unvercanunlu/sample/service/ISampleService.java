package tr.unvercanunlu.sample.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.sample.model.entity.Sample;

import java.util.List;
import java.util.UUID;

@Validated
public interface ISampleService {

    List<Sample> getAll();

    Sample get(@NotNull(message = "Id should not be null.") UUID id);

    void delete(@NotNull(message = "Id should not be null.") UUID id);

    Sample add(@NotNull(message = "Sample should not be null.") Sample sample);

}
