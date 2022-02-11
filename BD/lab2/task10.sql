SELECT p.ProductSubcategoryID
FROM Production.Product as p
WHERE p.ProductSubcategoryID is not null
GROUP BY p.ProductSubcategoryID
HAVING COUNT(*) > 10 