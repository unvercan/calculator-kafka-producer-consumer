package tr.unvercanunlu.compare_match.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sample {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "first", nullable = false)
    private Integer first;

    @Column(name = "second", nullable = false)
    private Integer second;

}
