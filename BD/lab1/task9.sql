/* Task 9 */
SELECT p.Name
FROM Production.Product as p
WHERE p.Name like '[DM]%' AND len(p.Name) > 3