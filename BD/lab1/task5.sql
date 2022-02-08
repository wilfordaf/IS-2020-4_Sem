/* Task 5 */
SELECT TOP 3 p.Name, p.Size
FROM Production.Product as p
WHERE p.Color = 'Black'
ORDER BY p.StandardCost DESC