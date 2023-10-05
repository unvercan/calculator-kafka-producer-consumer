package tr.unvercanunlu.calculator_kafka.kafka.message;

import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperandMessage implements Message {

    private UUID operandId;

    private UUID calculationId;

    private Integer first;

    private Integer second;

}
