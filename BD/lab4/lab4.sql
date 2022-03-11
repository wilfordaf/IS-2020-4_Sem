/* Task 1 */
select p.Name
from Production.Product as p
where p.ProductID = (
	select top 1 sod.ProductID
	from Sales.SalesOrderDetail as sod
	group by sod.ProductID
	order by sum(sod.OrderQty) desc
) 

/* Task 2 */
select soh.CustomerID
from Sales.SalesOrderHeader as soh
where soh.SalesOrderID = (
	select top 1 sod.SalesOrderID
	from Sales.SalesOrderDetail as sod
	group by sod.SalesOrderID
	order by sum(sod.LineTotal) desc
)  

/* Task 3 */
select p.Name
from Production.Product as p
where p.ProductID in (
	select sod.ProductID
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by sod.ProductID
	having count(distinct soh.CustomerID) = 1 
) 

/* Task 4 */
select p1.Name 
from Production.Product as p1
where p1.ListPrice > (
	select avg(p2.ListPrice)
	from Production.Product as p2
	where p2.ProductSubcategoryID = p1.ProductSubcategoryID
	group by p2.ProductSubcategoryID
) 

/* Task 5 */
select p.Name
from Production.Product as p
where p.ProductID in (
	select sod.ProductID
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	where soh.CustomerID in (
		select soh.CustomerID
		from Sales.SalesOrderDetail as sod
		join Sales.SalesOrderHeader as soh
		on sod.SalesOrderID = soh.SalesOrderID
		join Production.Product as p
		on sod.ProductID = p.ProductID
		group by soh.CustomerID
		having count(distinct p.Color) = 1
	)
	group by sod.ProductID
	having count(distinct soh.CustomerID) > 1 
)
and p.ProductID not in (
	select sod.ProductID
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	where soh.CustomerID in (
		select soh.CustomerID
		from Sales.SalesOrderDetail as sod
		join Sales.SalesOrderHeader as soh
		on sod.SalesOrderID = soh.SalesOrderID
		join Production.Product as p
		on sod.ProductID = p.ProductID
		group by soh.CustomerID
		having count(distinct p.Color) = 2
	)
)  

/* Task 6 */
select distinct sod_p.ProductID
from Sales.SalesOrderDetail as sod_p
join Sales.SalesOrderHeader as soh
on sod_p.SalesOrderID = soh.SalesOrderID
where soh.CustomerID in (
	select distinct soh.CustomerID
	from Sales.SalesOrderHeader as soh
	where soh.CustomerID in (
		select soh.CustomerID
		from Sales.SalesOrderDetail as sod
		join Sales.SalesOrderHeader as soh
		on sod.SalesOrderID = soh.SalesOrderID
		where exists (
			select sod_p.ProductID
			from Sales.SalesOrderDetail as sod_p
			join Sales.SalesOrderHeader as soh_p
			on sod_p.SalesOrderID = soh_p.SalesOrderID 
			where sod_p.SalesOrderID != sod.SalesOrderID and
				  soh_p.CustomerID = soh.CustomerID and
				  sod_p.ProductID = sod.ProductID
		)
	)
) 

/* Task 7 */
select distinct soh.CustomerID
from Sales.SalesOrderHeader as soh
where soh.CustomerID in (
	select soh.CustomerID
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	where exists (
		select sod_p.ProductID
		from Sales.SalesOrderDetail as sod_p
		join Sales.SalesOrderHeader as soh_p
		on sod_p.SalesOrderID = soh_p.SalesOrderID 
		where sod_p.SalesOrderID != sod.SalesOrderID and
			  soh_p.CustomerID = soh.CustomerID and
			  sod_p.ProductID = sod.ProductID
	)
) 

/* Task 8 */
select p.Name
from Production.Product as p
where p.ProductID in (
	select sod.ProductID
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by sod.ProductID
	having count(distinct soh.CustomerID) <= 3
) 

/* Task 9 */
select distinct sod.ProductID
from sales.SalesOrderDetail as sod 
where sod.SalesOrderID in (
	select distinct sod.SalesOrderID
	from Sales.SalesOrderDetail as sod
	where sod.ProductID in (
		select p.ProductID
		from Production.Product as p
		where p.ListPrice = (
			select top 1 _p.ListPrice
			from Production.Product as _p
			where _p.ProductSubcategoryID = p.ProductSubcategoryID
		)
	)
) 

/* Task 10 */
select soh.CustomerID
from Sales.SalesOrderHeader as soh
where soh.SalesOrderID in (
	select sod.SalesOrderID
	from Sales.SalesOrderDetail as sod
	where sod.ProductID in (
		select _sod.ProductID
		from Sales.SalesOrderDetail as _sod
		join Sales.SalesOrderHeader as _soh
		on _soh.SalesOrderID = _sod.SalesOrderID
		where _soh.CustomerID != soh.CustomerID
		group by _sod.ProductID
		having count(*) >= 3
	)
	group by sod.SalesOrderID
	having count(*) >= 3
)
group by soh.CustomerID
having count(*) >= 2

/* Task 11 */
select sod.SalesOrderID
from Sales.SalesOrderDetail as sod
where sod.ProductID in (
	select _sod.ProductID
	from Sales.SalesOrderDetail as _sod
	join Sales.SalesOrderHeader as _soh
	on _soh.SalesOrderID = _sod.SalesOrderID
	group by _sod.ProductID, _soh.CustomerID
	having count(*) = 2
)  

/* Task 12 */
select p.Name
from Production.Product as p
where p.ProductID in (
	select sod.ProductID
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	group by sod.ProductID
	having count(distinct soh.CustomerID) >= 3
)

/* Task 13 */
select p.ProductSubcategoryID
from Production.Product as p
where p.ProductID in (
	select sod.ProductID
	from Sales.SalesOrderDetail as sod
	group by sod.ProductID
	having count(*) > 3
)
group by p.ProductSubcategoryID
having count(*) > 3 

/* Task 14 */
select sod.ProductID
from Sales.SalesOrderDetail as sod
where sod.ProductID in (
	select _sod.ProductID
	from Sales.SalesOrderDetail as _sod
	join Sales.SalesOrderHeader as _soh
	on _soh.SalesOrderID = _sod.SalesOrderID
	group by _sod.ProductID, _soh.CustomerID
	having count(*) = 2
)
group by sod.ProductID
having count(*) <= 3