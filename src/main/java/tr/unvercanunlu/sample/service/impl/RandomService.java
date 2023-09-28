package tr.unvercanunlu.sample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.unvercanunlu.sample.service.IRandomService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomService implements IRandomService {

    private final Random random = new Random();

    @Override
    public Integer generate(int startGapInclusive, int endGapInclusive) {
        return startGapInclusive + random.nextInt(endGapInclusive);
    }

}
