--------------------------------------------------------
--  File created - Sunday-September-05-2021   
--------------------------------------------------------
REM INSERTING into ANDREI.CATEGORIES
SET DEFINE OFF;
Insert into ANDREI.CATEGORIES (CATEGORYID,CATEGORYNAME) values (1,'FOOD');
Insert into ANDREI.CATEGORIES (CATEGORYID,CATEGORYNAME) values (2,'CLOTHES');
REM INSERTING into ANDREI.CONSUMERS
SET DEFINE OFF;
Insert into ANDREI.CONSUMERS (CONSUMERID,CONSUMERUSERNAME,CONSUMERBALANCE) values (1,'jim',500);
REM INSERTING into ANDREI.PRODUCTS
SET DEFINE OFF;
Insert into ANDREI.PRODUCTS (PRODUCTID,PRODUCTNAME,PRODUCTCATEGORY,PRODUCTQUANTITY,PRODUCTPRICE,PRODUCTMAXQUANTITY) values (1,'MILK','FOOD',3,5,100);
Insert into ANDREI.PRODUCTS (PRODUCTID,PRODUCTNAME,PRODUCTCATEGORY,PRODUCTQUANTITY,PRODUCTPRICE,PRODUCTMAXQUANTITY) values (2,'BREAD','FOOD',6,4,200);
Insert into ANDREI.PRODUCTS (PRODUCTID,PRODUCTNAME,PRODUCTCATEGORY,PRODUCTQUANTITY,PRODUCTPRICE,PRODUCTMAXQUANTITY) values (3,'PANTS','CLOTHES',20,100,100);
Insert into ANDREI.PRODUCTS (PRODUCTID,PRODUCTNAME,PRODUCTCATEGORY,PRODUCTQUANTITY,PRODUCTPRICE,PRODUCTMAXQUANTITY) values (4,'SOCKS','CLOTHES',10,7,100);
REM INSERTING into ANDREI.TRANSACTIONS
SET DEFINE OFF;
