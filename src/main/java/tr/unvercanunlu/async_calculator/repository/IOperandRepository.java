package tr.unvercanunlu.async_calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.async_calculator.model.entity.Operand;

import java.util.UUID;

@Repository
public interface IOperandRepository extends JpaRepository<Operand, UUID> {

}
