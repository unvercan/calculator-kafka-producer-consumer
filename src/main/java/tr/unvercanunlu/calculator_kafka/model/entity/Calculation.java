package tr.unvercanunlu.calculator_kafka.model.entity;

import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calculation {

    private UUID id;

    private UUID operandId;

    private Integer operationCode;

    private UUID resultId;

    private Boolean completed;

}
