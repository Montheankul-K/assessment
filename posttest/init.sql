-- drop table if exists
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

-- create table
CREATE TABLE lottery (
    lottery_id SERIAL PRIMARY KEY,
    lottery_ticket VARCHAR(6) UNIQUE NOT NULL,
    lottery_price DOUBLE PRECISION NOT NULL,
    lottery_amount INTEGER NOT NULL
);

CREATE TABLE user_ticket (
    purchase_id SERIAL PRIMARY KEY,
    user_id VARCHAR(10),
    lottery_id INTEGER REFERENCES lottery(lottery_id) ON DELETE CASCADE
);

-- Initial data
INSERT INTO lottery(lottery_ticket, lottery_price, lottery_amount) VALUES('123456', 80, 1);
INSERT INTO lottery(lottery_ticket, lottery_price, lottery_amount) VALUES('000001', 80, 1);
INSERT INTO lottery(lottery_ticket, lottery_price, lottery_amount) VALUES('000002', 80, 1);
INSERT INTO lottery(lottery_ticket, lottery_price, lottery_amount) VALUES('000003', 80, 1);
INSERT INTO lottery(lottery_ticket, lottery_price, lottery_amount) VALUES('000004', 80, 1);

INSERT INTO user_ticket(user_id, lottery_id) VALUES('user000001', 1);
INSERT INTO user_ticket(user_id, lottery_id) VALUES('user000001', 2);
