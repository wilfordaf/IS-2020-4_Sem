/* Task 4 */
SELECT p.Name, p.Color, p.Size
FROM Production.Product as p
WHERE p.StandardCost > 100 AND p.Color = 'Black'
ORDER BY p.StandardCost ASC