/* Task 1 */
with t1(sales_order_id, product_id, product_sum, sale_sum) as (
	select sod.SalesOrderID as sales_order_id,
		   sod.ProductID as product_id, 
		   sum(sod.LineTotal) over(partition by sod.SalesOrderId, sod.ProductId) as product_sum,
		   sum(sod.LineTotal) over(partition by sod.SalesOrderId) as sale_sum
	from Sales.SalesOrderDetail as sod
)
select t1.sales_order_id as sales_order_id, 
	   t1.product_id as product_id, 
	   t1.product_sum / t1.sale_sum as product_ratio
from t1 
order by t1.sales_order_id 

/* Task 2 */
select p.Name as product_name,
	   p.StandardCost as product_price, 
	   p.StandardCost - min(p.StandardCost) over (partition by p.ProductSubcategoryId) as price_difference
from Production.Product as p

/* Task 3 */
select soh.CustomerID as customer_id,
	   soh.SalesOrderID as sale_id,
	   row_number() over (partition by soh.CustomerId order by soh.OrderDate) as order_number
from Sales.SalesOrderHeader as soh

/* Task 4 */
with t1(product_id, product_price, avg_subcategory_price) as (
	select p.ProductID as product_id,
		   p.StandardCost as product_price,
		   avg(p.StandardCost) over (partition by p.ProductSubcategoryId)
	from Production.Product as p
)
select t1.product_id as product_id
from t1
where t1.product_price > t1.avg_subcategory_price

/* Task 5 */
select sod.ProductID, max(sod.SalesOrderID)
from Sales.SalesOrderDetail as sod
group by sod.ProductID