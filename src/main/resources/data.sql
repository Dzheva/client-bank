INSERT INTO employer (address, employer_name) VALUES ('Company1', 'Address1');
INSERT INTO employer (address, employer_name) VALUES ('Company2', 'Address2');


INSERT INTO customer (customer_name, email, age) VALUES ('User1', 'user1@example.com', 25);
INSERT INTO customer (customer_name, email, age) VALUES ('User2', 'user2@example.com', 30);


INSERT INTO account (currency, customer_id) VALUES ('USD', 1);
INSERT INTO account (currency, customer_id) VALUES ('EUR', 2);

INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (2, 2);
