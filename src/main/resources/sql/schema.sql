CREATE TABLE IF NOT EXISTS "operation"
(
    "code" INTEGER,
    "name" VARCHAR(15),
    CONSTRAINT "operation_PK" PRIMARY KEY ("code"),
    CONSTRAINT "operation_UQ" UNIQUE ("code")
);

CREATE TABLE IF NOT EXISTS "calculation"
(
    "id"             UUID       DEFAULT RANDOM_UUID(),
    "done"           BOOLEAN    DEFAULT FALSE,
    "result"         FLOAT      DEFAULT NULL,
    "first"          INTEGER,
    "second"         INTEGER,
    "operation_code" INTEGER,
    CONSTRAINT "calculation_PK"           PRIMARY KEY ("id"),
    CONSTRAINT "calculation_operation_FK" FOREIGN KEY ("operation_code") REFERENCES "operation"
);
