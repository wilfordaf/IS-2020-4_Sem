/* Task 1 */
SELECT COUNT(*) AS 'Amount'
FROM Production.Product as p
WHERE p.StandardCost >= 30 AND p.Color IS not null
GROUP BY p.Color

/* Task 2 */
SELECT p.Color
FROM Production.Product as p
WHERE p.Color IS not null 
GROUP BY p.Color
HAVING MIN(p.ListPrice) > 100

/* Task 3 */
SELECT p.ProductSubcategoryID, COUNT(*) AS 'Amount'
FROM Production.Product as p
WHERE p.ProductSubcategoryID IS not null
GROUP BY p.ProductSubcategoryID

/* Task 4 */
SELECT s.ProductID, COUNT(*) AS 'Amount'
FROM Sales.SalesOrderDetail as s
WHERE s.ProductID IS not null
GROUP BY s.ProductID

/* Task 5 */
SELECT s.ProductID, COUNT(*) AS 'Amount'
FROM Sales.SalesOrderDetail as s
WHERE s.ProductID IS not null
GROUP BY s.ProductID
HAVING COUNT(*) > 5

/* Task 6 */
SELECT s.CustomerID
FROM Sales.SalesOrderHeader as s
GROUP BY s.CustomerID, s.OrderDate
HAVING COUNT(s.SalesOrderID) > 1

/* Task 7 */
SELECT s.SalesOrderID
FROM Sales.SalesOrderDetail as s 
GROUP BY s.SalesOrderID
HAVING COUNT(*) > 3

/* Task 8 */
SELECT s.ProductID
FROM Sales.SalesOrderDetail as s 
GROUP BY s.ProductID
HAVING COUNT(*) > 3

/* Task 9 */
SELECT s.ProductID
FROM Sales.SalesOrderDetail
GROUP BY s.ProductID
HAVING COUNT(*) IN (3, 5)

/* Task 10 */
SELECT p.ProductSubcategoryID
FROM Production.Product as p
WHERE p.ProductSubcategoryID is not null
GROUP BY p.ProductSubcategoryID
HAVING COUNT(*) > 10 

/* Task 11 */
SELECT s.ProductID
FROM Sales.SalesOrderDetail as s
WHERE s.OrderQty = 1
GROUP BY s.ProductID

/* Task 12 */
SELECT TOP 1 s.SalesOrderID, COUNT(*) as 'GoodAmount'
FROM Sales.SalesOrderDetail as s
GROUP BY s.SalesOrderID
ORDER BY GoodAmount DESC 