package tr.unvercanunlu.calculator_kafka.model.dto;

import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationDto {

    private UUID id;

    private Integer first;

    private Integer second;

    private String operation;

    private Double result;

}
