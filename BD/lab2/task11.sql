SELECT s.ProductID
FROM Sales.SalesOrderDetail as s
WHERE s.OrderQty = 1
GROUP BY s.ProductID
