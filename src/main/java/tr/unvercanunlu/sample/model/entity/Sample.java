package tr.unvercanunlu.sample.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sample")
@Table(name = "sample")
public class Sample implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "first", nullable = false)
    private Integer first;

    @Column(name = "second", nullable = false)
    private Integer second;

}
