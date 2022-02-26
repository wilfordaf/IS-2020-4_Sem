/* Task 1 */
SELECT p.Name, p.Color, p.Size
FROM Production.Product as p

/* Task 2 */
SELECT p.Name, p.Color, p.Size
FROM Production.Product as p
WHERE p.StandartCost > 100

/* Task 3 */
SELECT p.Name, p.Color, p.Size
FROM Production.Product as p
WHERE p.StandardCost < 100 AND p.Color = 'Black'

/* Task 4 */
SELECT p.Name, p.Color, p.Size
FROM Production.Product as p
WHERE p.StandardCost > 100 AND p.Color = 'Black'
ORDER BY p.StandardCost ASC

/* Task 5 */
SELECT TOP 3 p.Name, p.Size
FROM Production.Product as p
WHERE p.Color = 'Black'
ORDER BY p.StandardCost DESC

/* Task 6 */
SELECT p.Name, p.Color
FROM Production.Product as p
WHERE p.Name is NOT null AND p.Color IS NOT null
ORDER BY p.StandardCost DESC

/* Task 7 */
SELECT DISTINCT p.Color
FROM Production.Product as p
WHERE p.StandardCost >= 10 AND p.StandardCost <= 50

/* Task 8 */
SELECT p.Color
FROM Production.Product as p
WHERE p.Name like 'L_N%'

/* Task 9 */
SELECT p.Name
FROM Production.Product as p
WHERE p.Name like '[DM]%' AND len(p.Name) > 3

/* Task 10 */
SELECT p.Name
FROM Production.Product as p
WHERE p.SellStartDate <= '2012-31-12'

/* Task 11 */
SELECT psc.Name
FROM Production.ProductSubcategory as psc

/* Task 12 */
SELECT pc.Name
FROM Production.ProductCategory as pc

/* Task 13 */
SELECT p.FirstName
FROM Person.Person as p
WHERE p.Title = 'Mr.'

/* Task 14 */
SELECT p.FirstName
FROM Person.Person as p
WHERE p.Title is null
