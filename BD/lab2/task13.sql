SELECT TOP 1 s.SalesOrderID, SUM(s.UnitPrice * s.OrderQty) as 'TotalPrice'
FROM Sales.SalesOrderDetail as s
GROUP BY s.SalesOrderID
ORDER BY TotalPrice DESC