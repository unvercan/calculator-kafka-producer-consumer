package tr.unvercanunlu.calculator_kafka.service.impl;

import org.springframework.stereotype.Service;
import tr.unvercanunlu.calculator_kafka.service.IRandomService;

import java.util.Random;

@Service
public class RandomService implements IRandomService {

    private final Random random = new Random();

    @Override
    public Integer generate(Integer gapStart, Integer gapEnd) {
        return gapStart + random.nextInt(gapEnd);

    }

}
