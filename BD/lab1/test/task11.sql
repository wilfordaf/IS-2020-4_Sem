SELECT p.ProductNumber
FROM Production.Product as p
WHERE (LEN(p.ProductNumber) - LEN(REPLACE(p.ProductNumber, '-', ''))) >= 2