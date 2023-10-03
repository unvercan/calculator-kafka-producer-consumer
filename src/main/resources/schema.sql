DROP TABLE IF EXISTS operation;
DROP TABLE IF EXISTS operand;
DROP TABLE IF EXISTS result;
DROP TABLE IF EXISTS calculation;

CREATE TABLE operation
(
    code INT         NOT NULL UNIQUE,
    name VARCHAR(15) NOT NULL,
    PRIMARY KEY (code)
);

CREATE TABLE operand
(
    id     UUID NOT NULL DEFAULT RANDOM_UUID(),
    first  INT  NOT NULL,
    second INT  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE result
(
    id    UUID  NOT NULL DEFAULT RANDOM_UUID(),
    value FLOAT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE calculation
(
    id             UUID    NOT NULL DEFAULT RANDOM_UUID(),
    operand_id     UUID    NOT NULL,
    operation_code INT     NOT NULL,
    result_id      FLOAT   NOT NULL,
    completed      BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (operand_id) REFERENCES operand,
    FOREIGN KEY (operation_code) REFERENCES operation,
    FOREIGN KEY (result_id) REFERENCES result
);
