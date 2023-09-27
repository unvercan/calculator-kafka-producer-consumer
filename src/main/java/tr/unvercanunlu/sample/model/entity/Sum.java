package tr.unvercanunlu.sample.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sum")
public class Sum implements Serializable {

    @Id
    private UUID id;

    private Integer third;

}
