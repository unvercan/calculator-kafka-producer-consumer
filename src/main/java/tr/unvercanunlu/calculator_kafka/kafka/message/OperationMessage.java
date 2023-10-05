package tr.unvercanunlu.calculator_kafka.kafka.message;

import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationMessage implements Message {

    private Integer code;

    private UUID calculationId;

}
