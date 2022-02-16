SELECT p.FirstName, p.MiddleName
FROM Person.Person as p
WHERE LEN(p.MiddleName) > 1