/* ¹1 */
select p.ProductID
from Sales.SalesOrderDetail as p
group by p.ProductID
having COUNT(*) > 3

/* ¹2 */
select p.Color
from Production.Product as p
group by p.Color
having COUNT(*) between 2 and 5 

/* ¹3 */
select p.ProductSubcategoryID
from Production.Product as p
where p.Color = 'Red'
group by p.ProductSubcategoryID
having COUNT(*) > 2 

/* ¹4 */
select p.ProductID
from Sales.SalesOrderDetail as p
where p.UnitPrice > 100
group by p.ProductID
having COUNT(*) > 3 

/* ¹5 */
select p.Size
from Production.Product as p
where p.Color = 'Red'
group by p.Size
order by COUNT(*) desc

/* ¹6 */
select p.Size
from Production.Product as p
group by p.Size
having COUNT(*) > 10 

/* ¹7 */
select top 1 p.ProductID
from Sales.SalesOrderDetail as p
where p.UnitPrice <= 100
group by p.ProductID
order by SUM(OrderQty) desc 

/* ¹8 */
select top 1 p.ProductSubcategoryID
from Production.Product as p
where p.Color is not null and p.ProductSubcategoryID is not null
group by p.ProductSubcategoryID
order by COUNT(distinct p.Color) desc 

/* ¹9 */
select p.ProductID
from Sales.SalesOrderDetail as p
where p.OrderQty < 3
group by p.ProductID
having COUNT(*) > 3 

/* ¹10 */
select p.ProductCategoryID
from Production.ProductSubcategory as p
group by p.ProductCategoryID
order by COUNT(*) desc 

/* ¹11 */
select p.ProductSubcategoryID
from Production.Product as p
where p.ProductSubcategoryID is not null
group by p.ProductSubcategoryID
having COUNT(*) > 5 

/* ¹12 */
select p.ProductSubcategoryID
from Production.Product as p
where p.ProductSubcategoryID is not null and p.Color = 'Red'
group by p.ProductSubcategoryID
having COUNT(*) > 5