SELECT p.Color, COUNT(*) as 'Amount'
FROM Production.Product as p
WHERE p.Color is not null
GROUP BY p.Color
ORDER BY Amount DESC