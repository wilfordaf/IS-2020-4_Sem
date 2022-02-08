/* Task 7 */
SELECT DISTINCT p.Color
FROM Production.Product as p
WHERE p.StandardCost >= 10 AND p.StandardCost <= 50