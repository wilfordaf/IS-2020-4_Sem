/* Task 3 */
SELECT p.Name, p.Color, p.Size
FROM Production.Product as p
WHERE p.StandardCost < 100 AND p.Color = 'Black'