SELECT TOP 1 s.SalesOrderID, COUNT(*) as 'GoodAmount'
FROM Sales.SalesOrderDetail as s
GROUP BY s.SalesOrderID
ORDER BY GoodAmount DESC 