/* Task 6 */
SELECT p.Name, p.Color
FROM Production.Product as p
WHERE p.Name is NOT null AND p.Color IS NOT null
ORDER BY p.StandardCost DESC