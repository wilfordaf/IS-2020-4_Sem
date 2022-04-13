/* Task 1 */
select p.Name, p.ProductID
from Production.Product as p
where p.Color in (
	select _p.Color
	from Production.Product as _p
	where _p.ListPrice < 5000
) 

/* Task 2 */
select p.Name, p.ProductID
from Production.Product as p
where p.Color in (
	select top 1 _p.Color
	from Production.Product as _p
	order by _p.ListPrice desc
) 

/* Task 3 */
select p.Name, p.ProductID
from Production.Product as p
where p.Color in (
	select _p.Color
	from Production.Product as _p
	where _p.ListPrice < 4000
) 

/* Task 4 */
select ps.Name
from Production.ProductSubcategory as ps
where ps.ProductSubcategoryID = (
	select top 1 p.ProductSubcategoryID
	from Production.Product as p
	where p.Color = 'Red'
	order by p.ListPrice desc
) 

/* Task 5 */
select ppc.Name
from Production.ProductCategory as ppc
where ppc.ProductCategoryID = (
	select top 1 ps.ProductCategoryID
	from Production.ProductSubcategory as ps
	join Production.Product as p
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	group by ps.ProductCategoryID
	order by count(*) desc
) 

/* Task 6 */
select p.Name, p.ProductID
from Production.Product as p
where p.Color in (
	select _p.Color
	from Production.Product as _p
	where _p.ListPrice > 2000
) 

/* Task 7 */
select distinct soh.CustomerID, sod.UnitPrice
from Sales.SalesOrderDetail as sod
join Sales.SalesOrderHeader as soh
on sod.SalesOrderID = soh.SalesOrderID
where sod.UnitPrice = (
	select max(_sod.UnitPrice)
	from Sales.SalesOrderDetail as _sod
	join Sales.SalesOrderHeader as _soh 
	on _sod.SalesOrderID = _soh.SalesOrderID
	where _soh.CustomerID = soh.CustomerID
) 

/* Task 8 */
select p.ProductSubcategoryID, max(p.Name)
from Production.Product as p
where p.Color = 'Red' and p.ListPrice = (
	select max(_p.ListPrice)
	from Production.Product as _p
	where _p.ProductSubcategoryID = p.ProductSubcategoryID
)
group by p.ProductSubcategoryID 

/* Task 9 */
select p.ProductSubcategoryID, max(p.Name)
from Production.Product as p
where p.ListPrice = (
	select max(_p.ListPrice)
	from Production.Product as _p
	where _p.ProductSubcategoryID = p.ProductSubcategoryID
)
group by p.ProductSubcategoryID 

/* Task 10 */
select soh.SalesOrderID
from Sales.SalesOrderHeader as soh
where soh.CustomerID in (
	select _soh.CustomerID
	from Sales.SalesOrderHeader as _soh
	group by _soh.CustomerID
	having count(distinct _soh.SalesOrderID) > 3
) 

/* Task 11 */
select distinct ps.ProductCategoryID
from Production.ProductSubcategory as ps
where (
	select count(*)
	from Production.ProductSubcategory as _ps
	join Production.Product as _p
	on _p.ProductSubcategoryID = _ps.ProductSubCategoryID
	where _p.Color = 'Red' and _ps.ProductCategoryID = ps.ProductCategoryID
	group by _ps.ProductCategoryID
) > (
	select count(*)
	from Production.ProductSubcategory as _ps
	join Production.Product as _p
	on _p.ProductSubcategoryID = _ps.ProductSubCategoryID
	where _p.Color = 'Black' and _ps.ProductCategoryID = ps.ProductCategoryID
	group by _ps.ProductCategoryID
) 

/* Task 12 */
select pc.Name
from Production.Product as p
join Production.ProductSubcategory as ps
on ps.ProductSubcategoryID = p.ProductSubcategoryID
join Production.ProductCategory as pc
on pc.ProductCategoryID = ps.ProductCategoryID
where p.ProductID in (
	select top 1 sod.ProductID
	from Sales.SalesOrderDetail as sod
	group by sod.ProductID
	order by count(*) desc
) 

/* Task 13 */
select p.Name
from Production.Product as p
where p.ProductID in (
	select sod.ProductID
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	group by sod.ProductID
	having count(distinct soh.CustomerID) > 3
	/* and count(distinct sod.SalesOrderID) > 3
	but this changes nothing despite being mentioned 
	in the statement */
) 

/* Task 14 */
select ps.Name
from Production.ProductSubcategory as ps
where ps.ProductSubcategoryID = (
	select top 1 p.ProductSubcategoryID
	from Production.Product as p
	where p.ProductSubcategoryID is not null
	group by p.ProductSubcategoryID
	order by count(*) desc
) 

/* Task 15 */
select distinct pc.Name
from Production.ProductCategory as pc
where pc.ProductCategoryID in (
	select ps.ProductCategoryID
	from Production.ProductSubcategory as ps
	join Production.Product as p
	on p.ProductSubcategoryID = ps.ProductSubCategoryID
	where p.Color = 'Red'
	group by ps.ProductCategoryID
	having count(*) > 3
) 

/* Task 16 */
select distinct soh.CustomerID
from Sales.SalesOrderHeader as soh
where exists(
	select *
	from Sales.SalesOrderHeader as _soh
	join Sales.SalesOrderDetail as _sod
	on _sod.SalesOrderID = _soh.SalesOrderID
	join Production.Product as _p
	on _sod.ProductID = _p.ProductID
	where _soh.CustomerID = soh.CustomerID
	group by _soh.CustomerID
	having count(distinct _p.ProductSubcategoryID) >= 2
) 

/* Task 17 */
select soh.CustomerID, soh.SalesOrderID
from Sales.SalesOrderHeader as soh
where soh.SalesOrderID in (
	select top 1 _sod.SalesOrderID
	from Sales.SalesOrderDetail as _sod
	join Sales.SalesOrderHeader as _soh
	on _sod.SalesOrderID = _soh.SalesOrderID
	where _soh.CustomerID = soh.CustomerID
	group by _sod.SalesOrderID
	order by count(*) desc
)

/* Task 18 */
select soh.CustomerID
from Sales.SalesOrderHeader as soh
join Sales.SalesOrderDetail as sod
on soh.SalesOrderID = sod.SalesOrderID
where soh.SalesOrderID not in (
	select _sod.SalesOrderID
	from Sales.SalesOrderDetail as _sod
	where _sod.OrderQty > 1
)
group by soh.CustomerID
having count(*) = count(distinct sod.ProductID)

/* Task 19 */
select soh.CustomerID
from Sales.SalesOrderHeader as soh
group by soh.CustomerID
having count(*) > 1 and count(distinct soh.SalesOrderID) = all (
		select count(*)
		from Sales.SalesOrderDetail as _sod
		join Sales.SalesOrderHeader as _soh
		on _sod.SalesOrderID = _soh.SalesOrderID
		where _soh.CustomerID = soh.CustomerID
		group by _soh.CustomerID, _sod.ProductID
	)

/* Task 20 */
select p.ProductID, p.Name, (
	select count(distinct soh1.CustomerID) 
	from Sales.SalesOrderDetail as sod1
	join Sales.SalesOrderHeader as soh1
	on sod1.SalesOrderID = soh1.SalesOrderID
	where p.ProductID = sod1.ProductID
) as 'Bought', (
	select count(*) - (
		select count(distinct soh1.CustomerID) 
		from Sales.SalesOrderDetail as sod1
		join Sales.SalesOrderHeader as soh1
		on sod1.SalesOrderID = soh1.SalesOrderID
		where p.ProductID = sod1.ProductID
	) 
	from Sales.SalesOrderHeader as soh2
) as 'Didn`t buy'
from Production.Product as p
group by p.ProductID, p.Name
