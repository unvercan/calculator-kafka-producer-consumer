package tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "calculation")
@Table(name = "calculation")
public class Calculation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "operation_code")
    private Integer operationCode;

    @Column(name = "first")
    private Integer first;

    @Column(name = "second")
    private Integer second;

    @Column(name = "result")
    private Double result;

}
