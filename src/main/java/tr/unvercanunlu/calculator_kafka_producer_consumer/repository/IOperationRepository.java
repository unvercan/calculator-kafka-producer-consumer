package tr.unvercanunlu.calculator_kafka_producer_consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity.Operation;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, Integer> {
}
