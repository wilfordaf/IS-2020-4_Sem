SELECT s.ProductID
FROM Sales.SalesOrderDetail
GROUP BY s.ProductID
HAVING COUNT(*) IN (3, 5)