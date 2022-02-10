SELECT p.Color
FROM Production.Product as p
WHERE p.Color IS not null 
GROUP BY p.Color
HAVING MIN(p.ListPrice) > 100