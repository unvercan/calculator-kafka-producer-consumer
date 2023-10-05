package tr.unvercanunlu.calculator_kafka.kafka.message;

import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage implements Message {

    private UUID resultId;

    private UUID calculationId;

    private Double value;

}
