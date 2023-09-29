package tr.unvercanunlu.calculator_kafka.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "result")
public class Result implements Serializable {

    @Id
    private UUID id;

    private Double value;

}
