package tr.unvercanunlu.sample.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.util.List;
import java.util.UUID;

@Validated
public interface ISumService {

    List<Sum> getAll();

    Sum get(@NotNull(message = "Id should not be null.") UUID id);

    void delete(@NotNull(message = "Id should not be null.") UUID id);

}
