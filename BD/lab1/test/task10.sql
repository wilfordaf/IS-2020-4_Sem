SELECT p.Name
FROM Production.Product as p
WHERE p.Name NOT LIKE '%[adgkl]%'