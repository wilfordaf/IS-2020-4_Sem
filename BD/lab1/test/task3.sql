SELECT p.Name, p.ListPrice
FROM Production.Product as p
WHERE p.ListPrice > 40 AND p.ListPrice < 300
ORDER BY p.ListPrice