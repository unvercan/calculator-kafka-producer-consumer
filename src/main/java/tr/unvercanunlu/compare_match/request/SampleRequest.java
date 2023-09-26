package tr.unvercanunlu.compare_match.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SampleRequest {

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer first;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer second;

}
