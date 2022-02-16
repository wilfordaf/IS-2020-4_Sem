SELECT p.Name, p.Color, p.Size
FROM Production.Product as p
WHERE p.Color is not null AND p.Size is null