package tr.unvercanunlu.async_calculator.model.entity;

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
@Entity(name = "operand")
@Table(name = "operand")
public class Operand implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "first", nullable = false)
    private Integer first;

    @Column(name = "second", nullable = false)
    private Integer second;

}
