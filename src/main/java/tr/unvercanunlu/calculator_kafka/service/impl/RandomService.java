package tr.unvercanunlu.calculator_kafka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.unvercanunlu.calculator_kafka.service.IRandomService;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RandomService implements IRandomService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final Random random = new Random();

    @Override
    public Integer generate(Integer gapStart, Integer gapEnd) {
        Integer randomValue = gapStart + random.nextInt(gapEnd);

        this.logger.log(Level.INFO, () -> String.format("Random value between [%d, %d] gap is created. Value: %d", gapStart, gapEnd, randomValue));

        return randomValue;
    }

}
