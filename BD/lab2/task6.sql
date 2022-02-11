SELECT s.CustomerID
FROM Sales.SalesOrderHeader as s
GROUP BY s.CustomerID, s.OrderDate
HAVING COUNT(s.SalesOrderID) > 1