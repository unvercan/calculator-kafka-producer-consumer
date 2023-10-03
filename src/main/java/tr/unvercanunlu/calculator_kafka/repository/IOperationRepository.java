package tr.unvercanunlu.calculator_kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.calculator_kafka.model.entity.Operation;

import java.util.UUID;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, UUID> {

}
