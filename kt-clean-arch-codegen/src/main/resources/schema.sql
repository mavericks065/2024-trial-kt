CREATE TABLE stocks (
                        id UUID PRIMARY KEY,
                        date DATE NULL,
                        symbol TEXT NULL,
                        open DOUBLE PRECISION NULL,
                        high DOUBLE PRECISION NULL,
                        low DOUBLE PRECISION NULL,
                        close DOUBLE PRECISION NULL,
                        adj_close DOUBLE PRECISION NULL,
                        volume BIGINT NULL
);
