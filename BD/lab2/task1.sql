SELECT COUNT(*) AS 'Amount'
FROM Production.Product as p
WHERE p.StandardCost >= 30 AND p.Color IS not null
GROUP BY p.Color
