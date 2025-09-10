CREATE TABLE IF NOT EXISTS filter.filter
(
    id         bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name       varchar NOT NULL,
    filter     jsonb  NOT NULL
);