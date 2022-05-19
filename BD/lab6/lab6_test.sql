/* Task 1 
Найти для каждого чека его номер, количество категорий и подкатегорий */
with t1(sale_id, subcategory_rank, category_rank) as (
	select sod.SalesOrderID as sale_id,
		   dense_rank() over (partition by sod.SalesOrderId order by p.ProductSubcategoryId) as subcategory_rank,
		   dense_rank() over (partition by sod.SalesOrderId order by ps.ProductCategoryId) as category_rank
	from Sales.SalesOrderDetail as sod
	join Production.Product as p
	on p.ProductID = sod.ProductID
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
)
select distinct t1.sale_id as sale_id,
	   max(t1.subcategory_rank) over (partition by t1.sale_id) as subcategory_amount,
	   max(t1.category_rank) over (partition by t1.sale_id) as subcategory_amount
from t1

/* Task 2
Название товара, название категории, к которой он
относится и общее количество товаров в категории */
select p.Name as product_name,
	   pc.Name as category_name,
	   count(p.ProductID) over (partition by pc.ProductCategoryId) as products_in_category_amount
from Production.Product as p
join Production.ProductSubcategory as ps
on p.ProductSubcategoryID = ps.ProductSubcategoryID
join Production.ProductCategory as pc
on ps.ProductCategoryID = pc.ProductCategoryID

/* Task 3
Найти для каждого товара соотношения количества покупателей, купивших 
товар, к общему количеству покупателей, совершавших когда-либо покупки */
with t1(product_id, customer_rank) as (
	select distinct sod.ProductID as product_id,
		   dense_rank() over (partition by sod.ProductId order by soh.CustomerId) as customer_rank
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
)
select distinct t1.product_id as product_id,
	   max(t1.customer_rank) over (partition by t1.product_id) * 1.0 / (
	       select count(distinct soh.CustomerID)
		   from Sales.SalesOrderHeader as soh
	   ) as customer_ratio
from t1
order by t1.product_id

/* Task 4 
Вывести на экран, для каждого продукта, количество его продаж, и 
соотношение числа покупателей этого продукта, к числу покупателей, 
купивших товары из категории, к которой относится данный товар */
with t1(product_id, sales_amount, category_customers_rank, customer_rank) as (
	select p.ProductID as product_id,
		   sum(sod.OrderQty) over (partition by sod.ProductId),
		   dense_rank() over (partition by ps.ProductCategoryId order by soh.CustomerId) as category_customers_amount,
		   dense_rank() over (partition by sod.ProductId order by soh.CustomerId) as customer_rank
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	join Sales.SalesOrderDetail as sod
	on p.ProductID = sod.ProductID
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
)
select distinct t1.product_id as product_id,
	   t1.sales_amount as sales_amount,
	   max(t1.customer_rank) over (partition by t1.product_id) * 1.0 / 
	   max(t1.category_customers_rank) over (partition by t1.product_id) as customer_ratio
from t1
order by t1.product_id

/* Task 5
Вывести на экран следующую информацию: название товара, 
название подкатегории к которой он относится и общее 
количество товаров в этой подкатегории */
select p.Name as product_name,
	   ps.Name as product_subcategory_name,
	   count(p.ProductID) over (partition by ps.ProductSubcategoryId) as products_in_subcategory_amount
from Production.Product as p
join Production.ProductSubcategory as ps
on ps.ProductSubcategoryID = p.ProductSubcategoryID

/* Task 6
Найти для каждого покупателя все чеки, и для каждого чека вывести 
информацию: номер покупателя, номер чека, и сумма всех 
затрат этого покупателя с момента первой покупки и до данного чека */
/* Если до данного чека, НЕ включая его, что не очень логично, то 
вместо current row нужно вписать 1 following */
with t1(customer_id, sale_id, sum_rank) as (
	select soh.CustomerID as customer_id,
	   sod.SalesOrderID as sale_id,
	   sum(sod.LineTotal) over (partition by soh.CustomerId order by sod.ModifiedDate desc 
								rows between current row and unbounded following) as sum_previous_purchases
	   
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
)
select distinct t1.customer_id as customer_id,
	   t1.sale_id as sale_id,
	   max(t1.sum_rank) over (partition by t1.customer_id, t1.sale_id) as sum_previous
from t1
order by t1.customer_id