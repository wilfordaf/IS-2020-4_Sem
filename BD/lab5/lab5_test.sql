/* Task 1
Вывести на экран, для каждого продукта, количество его продаж, 
и соотношение числа покупателей этого продукта, к числу покупателей, 
купивших товары из категории, к которой относится данный товар (2 запроса??) */
select t1.product_id as product_id,
	   t1.sales_amount as sales_amount,
	   t1.customers_amount * 1.0 / t2.product_category_customers_amount as customer_ratio
from (
	select  sod.ProductID as product_id,
			psc.ProductCategoryID as product_category_id,
			sum(sod.OrderQty) as sales_amount,
			count(distinct soh.CustomerID) as customers_amount
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	join Production.Product as p
	on p.ProductID = sod.ProductID
	join Production.ProductSubcategory as psc
	on p.ProductSubcategoryID = psc.ProductSubcategoryID
	group by sod.ProductID, psc.ProductCategoryID
) as t1
join (
	select psc.ProductCategoryID as product_category_id,
		   count(distinct soh.CustomerID) as product_category_customers_amount
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	join Production.Product as p
	on p.ProductID = sod.ProductID
	join Production.ProductSubcategory as psc
	on p.ProductSubcategoryID = psc.ProductSubcategoryID
	group by psc.ProductCategoryID
) as t2
on t1.product_category_id = t2.product_category_id
order by t1.product_id

/* Task 2 
Найти всех покупателей, их номера, для которых верно утверждение - 
они ни разу не покупали товары более чем из трёх подкатегорий на один чек. 
Для данных покупателей вывести следующую информацию: номер покупателя, номер чека, 
количество подкатегорий к которым относятся товары данного чека, и количество 
подкатегорий, из которых покупатель приобретал товары за все покупки */
select t1.customer_id as customer_id,
	   t1.sale_id as sale_id,
	   t1.product_subcategory_amount as product_subcategory_amount,
	   t2.product_subcategory_amount as customer_product_subcategory_amount
from (
	select soh.CustomerID as customer_id,
		   sod.SalesOrderId as sale_id,
		   count(distinct p.ProductSubcategoryID) as product_subcategory_amount
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	join Production.Product as p
	on p.ProductID = sod.ProductID
	group by sod.SalesOrderID, soh.CustomerId
) as t1
join (
	select soh.CustomerID as customer_id,
		   count(distinct p.ProductSubcategoryID) as product_subcategory_amount
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	join Production.Product as p
	on p.ProductID = sod.ProductID
	group by soh.CustomerId
) as t2
on t1.customer_id = t2.customer_id
where 3 >= all (
	select count(distinct p.ProductSubcategoryID)
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	join Production.Product as p
	on p.ProductID = sod.ProductID
	where t1.customer_id = soh.CustomerID
	group by sod.SalesOrderID, soh.CustomerId
) 
order by t1.customer_id

/* Task 3 
Вывести на экран следующую информацию: название товара, название 
категории к которой он относится и общее количество товаров в этой категории */
select t1.product_name as product_name,
	   t1.product_category_name as product_category_name,
	   t2.products_amount as products_in_category_amount
from (
	select p.Name as product_name,
		   pc.ProductCategoryID as product_category_id,
		   pc.Name as product_category_name
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	join Production.ProductCategory as pc
	on ps.ProductCategoryID = pc.ProductCategoryID
) as t1
join (
	select pc.ProductCategoryID as product_category_id,
		   count(distinct p.ProductID) as products_amount
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	join Production.ProductCategory as pc
	on ps.ProductCategoryID = pc.ProductCategoryID
	group by pc.ProductCategoryID
) as t2
on t1.product_category_id = t2.product_category_id 

/* Task 4 
Вывести на экран номера покупателей, количество 
купленных ими товаров, и количество чеков, которые у них были */
select t1.customer_id as customer_id,
	   count(distinct t1.sale_id) as sales_amount,
	   sum(t1.sale_qty) as products_amount 
from (
	select soh.CustomerID as customer_id,
		   sod.SalesOrderID as sale_id,
		   sod.OrderQty as sale_qty
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
) as t1
group by t1.customer_id
order by t1.customer_id

/* Task 5 
Найти номера покупателей, у которых все купленные ими товары 
были куплены как минимум дважды, т.е. на два разных чека */
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

/* Task 6 
Найти номера покупателей, которые покупали товары из более чем 
половины подкатегорий товаров, и для них вывести информацию: 
номер покупателя, количество чеков, средняя сумма на один чек */
select t1.customer_id as customer_id,
	   t2.sales_amount as sales_amount,
	   t2.average_purchase as average_purchase
from (
	select soh.CustomerID as customer_id
	from Sales.SalesOrderHeader as soh
	join Sales.SalesOrderDetail as sod
	on sod.SalesOrderID = soh.SalesOrderID
	join Production.Product as p
	on p.ProductID = sod.ProductID
	group by soh.CustomerID
	having count(distinct p.ProductSubcategoryID) > (
		select count(distinct p.ProductSubcategoryID) as product_subcategory_amount
		from Production.Product as p
	) / 2
) as t1
join (
	select soh.CustomerID as customer_id,
		   count(distinct sod.SalesOrderID) as sales_amount,
		   avg(sod.LineTotal) as average_purchase
	from Sales.SalesOrderHeader as soh
	join Sales.SalesOrderDetail as sod
	on sod.SalesOrderID = soh.SalesOrderID
	group by soh.CustomerID
) as t2
on t1.customer_id = t2.customer_id
order by t1.customer_id 

/* Task 7 
Найти для каждого товара соотношение количества покупателей, купивших этот
товар, к общему количеству покупателей, совершавших когда-либо покупки */
select t1.product_id as product_id, 
	   t1.customers_amount * 1.0 / t2.customers_amount as customer_ratio
from (
	select p.ProductID as product_id,
		   count(distinct soh.CustomerID) as customers_amount
	from Sales.SalesOrderHeader as soh
	join Sales.SalesOrderDetail as sod
	on sod.SalesOrderID = soh.SalesOrderID
	join Production.Product as p
	on p.ProductID = sod.ProductID
	group by p.ProductID
) as t1, (
	select count(distinct soh.CustomerID) as customers_amount
	from Sales.SalesOrderHeader as soh
) as t2
order by t1.product_id

/* Task 8 
Вывести на экран следующую информацию: название товара, 
название категории к которой он относится, общее количество 
товаров в этой категории, количество покупателей данного товара. */
select t1.product_name as product_name,
	   t2.product_category_name as product_category_name,
	   t2.products_amount as products_amount,
	   t1.customers_amount as customers_amount
from (
	select p.Name as product_name,
		   ps.ProductCategoryID as product_category_id,
		   count(distinct soh.CustomerID) as customers_amount
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	join Sales.SalesOrderDetail as sod
	on p.ProductID = sod.ProductID
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	group by p.ProductID, ps.ProductCategoryID, p.Name
) as t1
join (
	select pc.ProductCategoryID as product_category_id,
		   pc.Name as product_category_name,
		   count(distinct p.ProductID) as products_amount
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	join Production.ProductCategory as pc
	on ps.ProductCategoryID = pc.ProductCategoryID
	group by pc.ProductCategoryID, pc.Name
) as t2
on t1.product_category_id = t2.product_category_id

/* Task 9 
Найти для каждого чека вывести его номер, количество 
категорий и подкатегорий, товары из которых есть в чеке */
select t1.sale_id as sale_id,
	   count(distinct t2.product_subcategory_id) as product_subcategories_amount,
	   count(distinct t2.product_category_id) as product_categories_amount
from (
	select sod.SalesOrderID as sale_id,
		   sod.ProductID as product_id
	from Sales.SalesOrderDetail as sod
) as t1 
join (
	select p.ProductID as product_id,
		   p.ProductSubcategoryID as product_subcategory_id,
		   ps.ProductCategoryID as product_category_id
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
) as t2
on t1.product_id = t2.product_id
group by t1.sale_id
order by t1.sale_id

/* Task 10 
Вывести на экран следующую информацию: название товара, название подкатегории, 
к которой он относится, и общее количество товаров в этой подкатегории */
select t1.product_name as product_name,
	   t1.product_subcategory_name as product_subcategory_name,
	   t2.products_amount as products_amount
from (
	select p.Name as product_name,
		   ps.ProductSubcategoryID as product_subcategory_id,
		   ps.Name as product_subcategory_name
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
) as t1 
join (
	select ps.ProductSubcategoryID as product_subcategory_id,
		   count(distinct p.ProductID) as products_amount
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	group by ps.ProductSubcategoryID
) as t2
on t1.product_subcategory_id = t2.product_subcategory_id

/* Task 11
Вывести на экран следующую информацию: название товара, 
название подкатегории к которой он относится и общее количество 
товаров в этой подкатегории, общее количество товаров того же цвета */
select t1.product_name as product_name,
	   t2.product_subcategory_name as product_subcategory_name,
	   t2.products_amount as products_amount,
	   t3.products_amount as products_same_color_amount
from (
	select p.Name as product_name,
		   p.Color as product_color,
		   ps.ProductSubcategoryID as product_subcategory_id 
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
) as t1 
join (
	select ps.ProductSubcategoryID as product_subcategory_id,
		   ps.Name as product_subcategory_name,
		   count(distinct p.ProductID) as products_amount
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	group by ps.ProductSubcategoryID, ps.Name
) as t2
on t1.product_subcategory_id = t2.product_subcategory_id
join (
	select p.Color as product_color,
		   count(distinct p.ProductID) as products_amount
	from Production.Product as p
	group by p.Color
) as t3
on t1.product_color = t3.product_color 

/* Task 12
Вывести на экран имена покупателей(ФИО), количество 
купленных ими товаров, и количество чеков, которые у них были */
select t2.first_name as first_name,
	   t2.middle_name as middle_name,
	   t2.last_name as last_name,
	   t1.sales_amount as sales_amount,
	   t1.products_amount as products_amount 
from (
	select soh.CustomerID as customer_id,
		   count(distinct sod.SalesOrderID) as sales_amount,
		   sum(sod.OrderQty) as products_amount
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by soh.CustomerID
) as t1
join (
	select distinct soh.CustomerID as customer_id, 
		   p.FirstName as first_name,
		   p.MiddleName as middle_name,
		   p.LastName as last_name
	from Sales.SalesOrderHeader as soh
	join Sales.Customer as c
	on soh.CustomerID = c.CustomerID
	join Person.Person as p
	on p.BusinessEntityID = c.PersonID
) as t2
on t2.customer_id = t1.customer_id
order by t1.customer_id

/* Task 13 
Вывести на экран, для каждого продукта, название, 
количество его продаж, общее число покупателей этого продукта, 
название подкатегории, к которой данный продукт */
select t1.product_name as product_name,
       t1.sales_amount as sales_amount,
	   t1.customers_amount as customers_amount,
	   t2.product_subcategory_name as product_subcategory_name	   
from (
	select p.Name as product_name,
		   p.ProductSubcategoryID as product_subcategory_id,
		   count(distinct soh.CustomerID) as customers_amount,
		   sum(OrderQty) as sales_amount
	from Production.Product as p
	join Sales.SalesOrderDetail as sod
	on p.ProductID = sod.ProductID
	join Sales.SalesOrderHeader as soh
	on soh.SalesOrderID = sod.SalesOrderID
	group by p.ProductID, p.ProductSubcategoryID, p.Name
) as t1
join (
	select ps.ProductSubcategoryID as product_subcategory_id,
		   ps.Name as product_subcategory_name
	from Production.Product as p
	join Production.ProductSubcategory as ps
	on p.ProductSubcategoryID = ps.ProductSubcategoryID
	group by ps.ProductSubcategoryID, ps.Name
) as t2
on t1.product_subcategory_id = t2.product_subcategory_id

/* Task 14 
Найти для каждого покупателя количество чеков, 
где присутствуют товары минимум из двух подкатегорий товаров */
select _soh.CustomerID as customer_id,
	   count(distinct _sod.SalesOrderID) as sales_amount
from Sales.SalesOrderDetail as _sod
join Sales.SalesOrderHeader as _soh
on _sod.SalesOrderID = _soh.SalesOrderID
where _sod.SalesOrderID in (
	select t1.sale_id
	from (
		select sod.SalesOrderID as sale_id,
			   p.ProductSubcategoryID as product_subcategory_id
		from Sales.SalesOrderDetail as sod
		join Production.Product as p 
		on p.ProductID = sod.ProductID
	) as t1 
	group by t1.sale_id
	having count(distinct t1.product_subcategory_id) > 1
)
group by _soh.CustomerID 

/* Task 15 
Вывести на экран следующую информацию: номер пользователя, 
продукт, который он купил 3 раза и продукт, который он купил 5 раз */
select coalesce(t1.customer_id, t2.customer_id) as customer_id,
	   t1.product_id as purchase_3_times,
	   t2.product_id as purchase_5_times
from (
	select soh.CustomerID as customer_id,
		   sod.ProductId as product_id
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by soh.CustomerID, sod.ProductID
	having count(distinct sod.SalesOrderID) = 3
) as t1
full join (
	select soh.CustomerID as customer_id,
		   sod.ProductId as product_id
	from Sales.SalesOrderDetail as sod
	join Sales.SalesOrderHeader as soh
	on sod.SalesOrderID = soh.SalesOrderID
	group by soh.CustomerID, sod.ProductID
	having count(distinct sod.SalesOrderID) = 5
) as t2
on t1.customer_id = t2.customer_id
order by customer_id