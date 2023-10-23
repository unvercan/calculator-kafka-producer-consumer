package tr.unvercanunlu.calculator_kafka_producer_consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity.Calculation;

import java.util.UUID;

@Repository
public interface ICalculationRepository extends JpaRepository<Calculation, UUID> {

    @Modifying
    @Query(value = "UPDATE \"calculation\" " +
            "SET \"calculation\".\"result\" = :result " +
            "WHERE \"calculation\".\"id\" = :calculationId", nativeQuery = true)
    void setResult(
            @Param(value = "calculationId") UUID calculationId,
            @Param(value = "result") Double result);

    @Modifying
    @Query(value = "UPDATE \"calculation\" " +
            "SET \"calculation\".\"done\" = :done " +
            "WHERE \"calculation\".\"id\" = :calculationId", nativeQuery = true)
    void setCompleteness(
            @Param(value = "calculationId") UUID calculationId,
            @Param(value = "done") Boolean done);

}
