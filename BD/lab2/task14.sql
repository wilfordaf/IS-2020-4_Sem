SELECT COUNT(*)
FROM Production.Product as p
WHERE p.Color is not null AND p.ProductSubcategoryID is not null
GROUP BY p.ProductSubcategoryID