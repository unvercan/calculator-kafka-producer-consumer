package tr.unvercanunlu.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.sample.model.entity.Sample;

import java.util.UUID;

@Repository
public interface ISampleRepository extends JpaRepository<Sample, UUID> {

}
