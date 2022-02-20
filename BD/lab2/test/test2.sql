/* �1 */
select p.ProductID
from Sales.SalesOrderDetail as p
group by p.ProductID
having COUNT(*) > 3

/* �2 */
select p.Color
from Production.Product as p
group by p.Color
having COUNT(*) between 2 and 5 

/* �3 */
select p.ProductSubcategoryID
from Production.Product as p
where p.Color = 'Red'
group by p.ProductSubcategoryID
having COUNT(*) > 2 

/* �4 */
select p.ProductID
from Sales.SalesOrderDetail as p
where p.UnitPrice > 100
group by p.ProductID
having COUNT(*) > 3 

/* �5 */
select p.Size
from Production.Product as p
where p.Color = 'Red'
group by p.Size
order by COUNT(*) desc

/* �6 */
select p.Size
from Production.Product as p
group by p.Size
having COUNT(*) > 10 

/* �7 */
select top 1 p.ProductID
from Sales.SalesOrderDetail as p
where p.UnitPrice <= 100
group by p.ProductID
order by SUM(OrderQty) desc 

/* �8 */
select top 1 p.ProductSubcategoryID
from Production.Product as p
where p.Color is not null and p.ProductSubcategoryID is not null
group by p.ProductSubcategoryID
order by COUNT(distinct p.Color) desc 

/* �9 */
select p.ProductID
from Sales.SalesOrderDetail as p
where p.OrderQty < 3
group by p.ProductID
having COUNT(*) > 3 

/* �10 */
select p.ProductCategoryID
from Production.ProductSubcategory as p
group by p.ProductCategoryID
order by COUNT(*) desc 

/* �11 */
select p.ProductSubcategoryID
from Production.Product as p
where p.ProductSubcategoryID is not null
group by p.ProductSubcategoryID
having COUNT(*) > 5 

/* �12 */
select p.ProductSubcategoryID
from Production.Product as p
where p.ProductSubcategoryID is not null and p.Color = 'Red'
group by p.ProductSubcategoryID
having COUNT(*) > 5