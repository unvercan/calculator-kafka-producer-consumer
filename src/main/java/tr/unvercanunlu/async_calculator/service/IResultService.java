package tr.unvercanunlu.async_calculator.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.async_calculator.model.entity.Result;

import java.util.List;
import java.util.UUID;

@Validated
public interface IResultService {

    List<Result> getAll();

    Result get(@NotNull(message = "Id should not be null.") UUID id);

    void delete(@NotNull(message = "Id should not be null.") UUID id);

}
