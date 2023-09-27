package tr.unvercanunlu.sample.service;

import tr.unvercanunlu.sample.model.entity.Sample;

import java.util.List;
import java.util.UUID;

public interface ISampleService {

    List<Sample> getAll();

    Sample get(UUID id);

    void delete(UUID id);

}
