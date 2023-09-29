package tr.unvercanunlu.calculator_kafka.model.entity;

import tr.unvercanunlu.calculator_kafka.model.constant.Operation;

import java.util.UUID;

public class Calculation {

    private UUID id;

    private Integer first;

    private Integer second;

    private Operation operation;

    private Double result;

}
