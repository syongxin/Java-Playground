create database miniproject;

use miniproject;

create table product (
	id int primary key auto_increment,
	pro_name varchar(30),
	pro_description varchar(100),
	pro_price double
);

create table category (
	id int primary key auto_increment,
	cat_name varchar(50)
);

insert into product (pro_name, pro_description, pro_price) values ('iphone 7', 'The newest iphone has the best performance as well as new finishes, water resistance.', 749.99);
insert into category (cat_name) values ('Cell Phones');

insert into product (pro_name, pro_description, pro_price) values ('Harry Potter Paperback 1-7', 'J.K. Rowlings seven bestselling Harry Potter books are available in a stunning paperback boxed set!', 52.16);
insert into category (cat_name) values ('Books');

create table pro_cat (
	pid int,
	cid int,
	constraint fk_pid foreign key (pid) references product(id),
	constraint fk_cid foreign key (cid) references category(id)
);

insert into pro_cat (pid, cid) values (1, 1);
insert into pro_cat (pid, cid) values (2, 2);

insert into product (pro_name, pro_description, pro_price) values ('iphone 6', 'Old phone must be died', 449.99);
insert into product (pro_name, pro_description, pro_price) values ('basketball', 'super ball', 20);
insert into product (pro_name, pro_description, pro_price) values ('learn Java', 'best book for learner', 40.99);


insert into pro_cat (pid, cid) values (4, 1);
insert into pro_cat (pid, cid) values (1, 1);
insert into pro_cat (pid, cid) values (5, 3);
insert into pro_cat (pid, cid) values (6, 2);





