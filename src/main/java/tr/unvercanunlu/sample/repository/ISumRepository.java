package tr.unvercanunlu.sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.sample.model.entity.Sum;

import java.util.UUID;

@Repository
public interface ISumRepository extends MongoRepository<Sum, UUID> {

}
