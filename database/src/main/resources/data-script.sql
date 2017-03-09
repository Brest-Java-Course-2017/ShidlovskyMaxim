INSERT INTO producer (producer_id, producer_name, country)
VALUES
(1, 'Mercedes', 'Germany'),
(2, 'BMW', 'Germany');

INSERT INTO car (car_id, model, release_date, amount)
VALUES
(1, 'C-class Sedan', '2014-01-01', 10),
(2, 'S-class Sedan', '2013-01-01', 20),
(3, 'X5', '2003-01-01', 30);

INSERT INTO producer_binds_car (producer_id, car_id)
VALUES
(1, 1),
(1, 2),
(2, 3);