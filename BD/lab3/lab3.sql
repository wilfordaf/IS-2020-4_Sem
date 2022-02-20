/* �1 */
select p.Name, pc.Name 
from Production.Product as p
join Production.ProductSubcategory as psc
on p.ProductSubcategoryID = psc.ProductSubcategoryID
join Production.ProductCategory as pc
on pc.ProductCategoryID = psc.ProductCategoryID
where p.Color = 'Red' and p.ListPrice >= 100

/* �2 */
select psc1.Name
from Production.ProductSubcategory as psc1
join Production.ProductSubcategory as psc2
on psc1.Name = psc2.Name and psc1.ProductSubcategoryID != psc2.ProductSubcategoryID 

/* �3 */
select pc.Name, COUNT(*) 
from Production.Product as p
join Production.ProductSubcategory as psc
on p.ProductSubcategoryID = psc.ProductSubcategoryID
join Production.ProductCategory as pc
on pc.ProductCategoryID = psc.ProductCategoryID
group by pc.Name  

/* �4 */
select psc.Name, COUNT(*) as 'Amount'
from Production.Product as p 
join Production.ProductSubcategory as psc
on p.ProductSubcategoryID = psc.ProductSubcategoryID
group by psc.ProductSubcategoryID, psc.Name 

/* �5 */
select psc.Name, COUNT(*) as 'Amount'
from Production.Product as p 
join Production.ProductSubcategory as psc
on p.ProductSubcategoryID = psc.ProductSubcategoryID
group by psc.ProductSubcategoryID, psc.Name
order by Amount desc 

/* �6 */
select psc.Name, MAX(p.ListPrice)
from Production.Product as p 
join Production.ProductSubcategory as psc
on p.ProductSubcategoryID = psc.ProductSubcategoryID
where p.Color = 'Red'
group by psc.ProductSubcategoryID, psc.Name 

/* �7 */
select v.Name, COUNT(distinct pv.ProductID)
from Purchasing.ProductVendor as pv
join Purchasing.Vendor as v
on pv.BusinessEntityID = v.BusinessEntityID
group by v.Name 

/* �8 */
select p.Name
from Purchasing.ProductVendor as pv
join Production.Product as p
on pv.ProductID = p.ProductID
group by p.Name
having COUNT(*) > 1 

/* �9 */
select top 1 p.Name
from Purchasing.ProductVendor as pv
join Production.Product as p
on pv.ProductID = p.ProductID
group by p.Name
order by COUNT(*) 

/* �10 */
select top 1 pc.Name
from Production.Product as p
join Production.ProductSubcategory as psc
on p.ProductSubcategoryID = psc.ProductSubcategoryID
join Production.ProductCategory as pc
on psc.ProductCategoryID = pc.ProductCategoryID
join Purchasing.ProductVendor as pv
on p.ProductID = pv.ProductID
group by pc.Name
order by COUNT(*) desc 

/* �11 */
select pc.Name, COUNT(distinct p.ProductSubcategoryID) as 'SubcategoryAmount', COUNT(p.ProductID) as 'ProductAmount'
from Production.Product as p
join Production.ProductSubcategory as psc
on p.ProductSubcategoryID = psc.ProductSubcategoryID
join Production.ProductCategory as pc
on psc.ProductCategoryID = pc.ProductCategoryID
group by pc.Name

/* �12 */
select v.CreditRating, COUNT(*)
from Purchasing.Vendor as v
join Purchasing.ProductVendor as pv
on pv.BusinessEntityID = v.BusinessEntityID
join Production.Product as p 
on pv.ProductID = p.ProductID
group by v.CreditRating
