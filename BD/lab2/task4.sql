SELECT s.ProductID, COUNT(*) AS 'Amount'
FROM Sales.SalesOrderDetail as s
WHERE s.ProductID IS not null
GROUP BY s.ProductID