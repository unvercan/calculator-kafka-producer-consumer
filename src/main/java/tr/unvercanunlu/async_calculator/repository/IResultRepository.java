package tr.unvercanunlu.async_calculator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.async_calculator.model.entity.Result;

import java.util.UUID;

@Repository
public interface IResultRepository extends MongoRepository<Result, UUID> {

}
