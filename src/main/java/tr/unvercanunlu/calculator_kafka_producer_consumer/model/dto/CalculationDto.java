package tr.unvercanunlu.calculator_kafka_producer_consumer.model.dto;

import lombok.*;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity.Operation;

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

    private Boolean done;

    private Operation operation;

    private Integer first;

    private Integer second;

    private Double result;

}
