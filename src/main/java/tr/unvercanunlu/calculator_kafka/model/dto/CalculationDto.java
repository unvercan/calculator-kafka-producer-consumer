package tr.unvercanunlu.calculator_kafka.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationDto implements Serializable {

    private UUID id;

    private Integer first;

    private Integer second;

    private String operation;

    private Double result;

}
