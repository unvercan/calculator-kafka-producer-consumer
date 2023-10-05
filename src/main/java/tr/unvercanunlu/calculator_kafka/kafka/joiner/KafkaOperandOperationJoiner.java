package tr.unvercanunlu.calculator_kafka.kafka.joiner;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.kstream.ValueJoinerWithKey;
import org.springframework.stereotype.Component;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperandMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.OperationMessage;
import tr.unvercanunlu.calculator_kafka.kafka.message.ResultMessage;
import tr.unvercanunlu.calculator_kafka.model.entity.Result;
import tr.unvercanunlu.calculator_kafka.service.IResultService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaOperandOperationJoiner implements ValueJoinerWithKey<String, OperandMessage, OperationMessage, ResultMessage> {

    private final IResultService resultService;

    @Override
    public ResultMessage apply(String calculationId, OperandMessage operandMessage, OperationMessage operationMessage) {
        Result result = Result.builder()
                .value(operandMessage.getFirst().doubleValue() + operandMessage.getSecond().doubleValue())
                .build();

        System.out.println(result + " is created.");

        result = this.resultService.create(result);

        System.out.println(result + " is saved to database.");

        ResultMessage resultMessage = ResultMessage.builder()
                .calculationId(UUID.fromString(calculationId))
                .resultId(result.getId())
                .value(result.getValue())
                .build();

        System.out.println(resultMessage + " is created.");

        return resultMessage;

    }

}
