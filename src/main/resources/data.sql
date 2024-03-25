INSERT INTO employer (employer_name, address) VALUES ('Company1', 'Address1');
INSERT INTO employer (employer_name, address) VALUES ('Company2', 'Address2');


INSERT INTO customer (customer_name, email, role, password, age) VALUES ('User1', 'user1@example.com', 'ADMIN', '$2a$10$W9Q0W5nqWYfGGS.QKe6ZHeI2Ud/X7c556t9uy1zWTHrTl7PSLqvdW', 25);
INSERT INTO customer (customer_name, email, password, age) VALUES ('User2', 'user2@example.com', '$2a$10$xSTKJ41vZxh2t4kYbPfcveMXuOc.f6/JBwaYbeHLUqvmBguNXJo0y', 30);


INSERT INTO account (currency, customer_id) VALUES ('USD', 1);
INSERT INTO account (currency, customer_id) VALUES ('EUR', 2);


INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (2, 2);
