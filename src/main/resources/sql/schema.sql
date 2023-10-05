CREATE TABLE IF NOT EXISTS "operation"
(
    "code" INTEGER     NOT NULL,
    "name" VARCHAR(15) NOT NULL,
    PRIMARY KEY ("code"),
    UNIQUE ("code")
);

CREATE TABLE IF NOT EXISTS "operand"
(
    "id"     UUID    NOT NULL DEFAULT RANDOM_UUID(),
    "first"  INTEGER NOT NULL,
    "second" INTEGER NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "result"
(
    "id"    UUID  NOT NULL DEFAULT RANDOM_UUID(),
    "value" FLOAT NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "calculation"
(
    "id"             UUID    NOT NULL DEFAULT RANDOM_UUID(),
    "operand_id"     UUID             DEFAULT NULL,
    "operation_code" INTEGER          DEFAULT NULL,
    "result_id"      UUID             DEFAULT NULL,
    "completed"      BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id"),
    FOREIGN KEY ("operand_id") REFERENCES "operand",
    FOREIGN KEY ("operation_code") REFERENCES "operation",
    FOREIGN KEY ("result_id") REFERENCES "result"
);
