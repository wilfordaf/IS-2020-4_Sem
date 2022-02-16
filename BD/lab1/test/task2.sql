SELECT p.Name, p.ProductSubcategoryID
FROM Production.Product as p
WHERE p.ProductSubcategoryID in (1,3)