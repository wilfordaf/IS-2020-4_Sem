/* Task 1 */
select pc.Name
from Production.ProductCategory as pc 
join Production.ProductSubcategory as psc 
on pc.ProductCategoryID = psc.ProductCategoryID
join Production.Product as p
on p.ProductSubcategoryID = psc.ProductSubcategoryID
group by pc.Name
having count(*) > 20

/* Task 2 */
select top 2 pc.Name
from Production.ProductCategory as pc 
join Production.ProductSubcategory as psc 
on pc.ProductCategoryID = psc.ProductCategoryID
join Production.Product as p
on p.ProductSubcategoryID = psc.ProductSubcategoryID
group by pc.Name
order by count(*) asc 

/* Task 3 */
select p.Name
from Sales.SalesOrderDetail as sod
join Production.Product as p
on p.ProductID = sod.ProductID
group by p.Name 

/* Task 4 */
select p.Name
from Sales.SalesOrderDetail as sod
right join Production.Product as p
on p.ProductID = sod.ProductID
where sod.SalesOrderID is null

/* Task 5 */
select p.Name
from Sales.SalesOrderDetail as sod
join Production.Product as p
on p.ProductID = sod.ProductID
group by p.Name 
order by count(*) asc 

/* Task 6 */
select top 3 p.Name
from Sales.SalesOrderDetail as sod
join Production.Product as p
on p.ProductID = sod.ProductID
group by p.Name 
order by count(*) desc 

/* Task 7 */
select pc.Name, count(*)
from Production.ProductCategory as pc
join Production.ProductSubcategory as psc
on pc.ProductCategoryID = psc.ProductSubcategoryID
join Production.Product as p
on p.ProductSubcategoryID = psc.ProductSubcategoryID
join Sales.SalesOrderDetail as sod
on p.ProductID = sod.ProductID
group by pc.Name
order by count(*) asc 

/* Task 8 */
select v.Name, count(*)
from Purchasing.ProductVendor as pv
join Purchasing.Vendor as v
on pv.BusinessEntityID = v.BusinessEntityID
group by v.Name
order by count(*) asc 

/* Task 9 */
select pc.Name, count(distinct p.Size) as 'Amount'
from Production.Product as p
join Production.ProductSubcategory as psc
on p.ProductSubcategoryID = psc.ProductSubcategoryID
join Production.ProductCategory as pc
on psc.ProductCategoryID = pc.ProductCategoryID
group by pc.Name 

/* Task 10 */
select p.Name
from Sales.SalesOrderDetail as sod
join Production.Product as p
on p.ProductID = sod.ProductID
group by p.Name 
having count(*) in (3, 5) 

/* Task 11 */
select psc.Name
from Production.ProductSubcategory as psc 
join Production.Product as p
on p.ProductSubcategoryID = psc.ProductSubcategoryID
group by psc.Name
having count(*) > 5

/* Task 12 */
select p.Name, pc.Name
from Production.ProductCategory as pc 
join Production.ProductSubcategory as psc 
on pc.ProductCategoryID = psc.ProductCategoryID
join Production.Product as p
on p.ProductSubcategoryID = psc.ProductSubcategoryID
join Sales.SalesOrderDetail as sod
on sod.ProductID = p.ProductID
where p.Color = 'Blue'
group by p.Name, pc.Name
having count(*) = 2

