INSERT INTO producer (producer_id, producer_name, country)
VALUES
(1, 'Mercedes', 'Germany'),
(2, 'BMW', 'Germany'),
(3, 'Audi', 'Germany'),
(4, 'Toyota', 'Japan'),
(5, 'Peugeot', 'France');

INSERT INTO car (car_id, model, release_date, amount, producer_id)
VALUES
(1, 'S500', '2002-01-01', 10, 1),
(2, 'E220', '2003-01-01', 20, 1),
(3, 'X5', '2006-01-01', 30, 2),
(4, 'A6', '2011-01-01', 25, 3),
(5, 'A8', '2002-01-01', 20, 3),
(6, 'Corolla', '2008-01-01', 35, 4),
(7, 'Camry', '2006-01-01', 30, 4),
(8, '408', '2012-01-01', 10, 5),
(9, '508', '2014-01-01', 15, 5);