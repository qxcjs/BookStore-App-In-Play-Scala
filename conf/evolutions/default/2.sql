# --- Sample dataset

# --- !Ups

insert into "book"("title","price","author") values('Java',15,'wangwu');
insert into "book"("title","price","author") values('C++',18,'lisi');
insert into "book"("title","price","author") values('PHP',12,'zhangsan');

# --- !Downs

delete from "book";

