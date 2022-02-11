SELECT s.SalesOrderID
FROM Sales.SalesOrderDetail as s 
GROUP BY s.SalesOrderID
HAVING COUNT(*) > 3