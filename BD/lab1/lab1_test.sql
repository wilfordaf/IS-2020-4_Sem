/* Task 1 */
SELECT p.Name, p.Color, p.Size
FROM Production.Product as p
WHERE p.Color is not null AND p.Size is null

/* Task 2 */
SELECT p.Name, p.ProductSubcategoryID
FROM Production.Product as p
WHERE p.ProductSubcategoryID in (1,3)

/* Task 3 */
SELECT p.Name, p.ListPrice
FROM Production.Product as p
WHERE p.ListPrice > 40 AND p.ListPrice < 300
ORDER BY p.ListPrice

/* Task 4 */
SELECT p.Name, p.ListPrice
FROM Production.Product as p
WHERE NOT p.ListPrice BETWEEN 40 AND 300
ORDER BY p.ListPrice

/* Task 5 */
SELECT p.Name, p.ListPrice
FROM Production.Product as p
WHERE p.ListPrice BETWEEN 40 AND 300
ORDER BY p.ListPrice

/* Task 6 */
SELECT ps.Name, ps.ProductSubcategoryID
FROM Production.ProductSubcategory as ps
WHERE ps.ProductSubcategoryID IN (1,3,5)

/* Task 7 */
SELECT pc.Name, pc.ProductCategoryID
FROM Production.ProductCategory as pc
WHERE pc.ProductCategoryID IN (1,3,7)

/* Task 8 */
SELECT p.FirstName, p.MiddleName
FROM Person.Person as p
WHERE LEN(p.MiddleName) > 1

/* Task 9 */
SELECT p.Name
FROM Production.Product as p
WHERE P.Name LIKE '__u%'

/* Task 10 */
SELECT p.Name
FROM Production.Product as p
WHERE p.Name NOT LIKE '%[adgkl]%'

/* Task 11 */
SELECT p.ProductNumber
FROM Production.Product as p
WHERE (LEN(p.ProductNumber) - LEN(REPLACE(p.ProductNumber, '-', ''))) >= 2

/* Task 12 */
SELECT p.FirstName, p.Suffix
FROM Person.Person as p
WHERE p.Suffix LIKE 'Jr.'