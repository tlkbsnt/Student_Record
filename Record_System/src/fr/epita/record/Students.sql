//create database Students

create table Student_Record(
  	Name text not null,
    Faculty text not null,
    Email text not null,
    Contacts text not null,
   	Address text not null,
   	primary key (Email,Contacts)
);

select * from Student_Record;
