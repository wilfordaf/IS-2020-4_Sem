SELECT p.ProductSubcategoryID, COUNT(*) AS 'Amount'
FROM Production.Product as p
WHERE p.ProductSubcategoryID IS not null
GROUP BY p.ProductSubcategoryID