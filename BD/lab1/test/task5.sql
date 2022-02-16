SELECT p.Name, p.ListPrice
FROM Production.Product as p
WHERE p.ListPrice BETWEEN 40 AND 300
ORDER BY p.ListPrice