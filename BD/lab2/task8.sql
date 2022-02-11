SELECT s.ProductID
FROM Sales.SalesOrderDetail as s 
GROUP BY s.ProductID
HAVING COUNT(*) > 3