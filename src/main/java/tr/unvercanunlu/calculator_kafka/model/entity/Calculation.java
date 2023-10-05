package tr.unvercanunlu.calculator_kafka.model.entity;

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
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "operand_id")
    private UUID operandId;

    @Column(name = "operation_code")
    private Integer operationCode;

    @Column(name = "result_id")
    private UUID resultId;

    @Column(name = "completed", nullable = false)
    private Boolean completed;

}
