package tr.unvercanunlu.sample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.unvercanunlu.sample.service.IRandomService;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RandomService implements IRandomService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final Random random = new Random();

    @Override
    public Integer generate(Integer startGap, Integer endGap) {
        Integer randomValue = startGap + random.nextInt(endGap);

        this.logger.log(Level.INFO, () -> String.format("Random value between [%d, %d] gap is created. Value: %d", startGap, endGap, randomValue));

        return randomValue;
    }

}
