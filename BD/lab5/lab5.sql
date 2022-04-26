/* Task 1 */
select tmp.customer_id, 
	   tmp.average_purchase
from (
	select soh.CustomerID as customer_id, 
	       count(*) * 1.0 / count(distinct soh.SalesOrderID) as average_purchase
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by soh.CustomerID
) as tmp
order by tmp.customer_id

/* Task 2 */
select t1.customer_id, 
	   t1.product_id, 
	   t1.buy_amount * 1.0 / t2.buy_amount as product_ratio
from (
	select soh.CustomerID as customer_id, 
		   sod.ProductId as product_id, 
		   count(*) as buy_amount
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by soh.CustomerID, sod.ProductID
	
) as t1, (
	select soh.CustomerID as customer_id, 
		   count(*) as buy_amount
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by soh.CustomerID
) as t2
where t1.customer_id = t2.customer_id
order by t1.customer_id

/* Task 3 */
select tmp.product_name, 
	   tmp.buy_amount,
	   tmp.customer_amount
from (
	select p.Name as product_name, 
		   count(distinct sod.SalesOrderID) as buy_amount, 
		   count(distinct soh.CustomerID) as customer_amount
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	join Production.Product as p
	on p.ProductID = sod.ProductID 
	group by sod.ProductID, p.Name
) as tmp 

/* Task 4 */
select tmp.customer_id as customer_id,
	   min(tmp.sale_sum) as min_purchase,
	   max(tmp.sale_sum) as max_purchase
from (
	select soh.CustomerID as customer_id, 
		   sum(sod.LineTotal) as sale_sum
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by soh.CustomerID, sod.SalesOrderID
) as tmp
group by tmp.customer_id
order by tmp.customer_id 

/* Task 5 */
select distinct _soh.CustomerID
from Sales.SalesOrderHeader as _soh
where _soh.CustomerID not in (
	select tmp.customer_id
	from (
		select soh.CustomerID as customer_id,
			   sod.SalesOrderId as sale_id,
			   count(distinct sod.ProductID) as product_amount
		from Sales.SalesOrderDetail as sod
		join Sales.SalesOrderHeader as soh
		on sod.SalesOrderID = soh.SalesOrderID
		group by soh.CustomerID, sod.SalesOrderID
	
	) as tmp
	group by tmp.customer_id, tmp.product_amount
	having count(*) > 1
)
order by _soh.CustomerID 

/* Task 6 */
select distinct _soh.CustomerID
from Sales.SalesOrderHeader as _soh
where 1 < all (
	select tmp.product_amount
	from (
		select soh.CustomerID as customer_id,
				sod.ProductID as product_id,
				count(*) as product_amount
		from Sales.SalesOrderDetail as sod
		join Sales.SalesOrderHeader as soh
		on sod.SalesOrderID = soh.SalesOrderID
		group by soh.CustomerID, sod.ProductID
	) as tmp
	where _soh.CustomerID = tmp.customer_id
)
order by _soh.CustomerID