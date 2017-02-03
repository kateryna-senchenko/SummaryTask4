drop database testDB;

create database testDB DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;


use testDB;

create table roles(
	id int auto_increment,
	name varchar(25) unique not null,
	primary key(id)
);

create table statuses(
	id int auto_increment,
	name varchar(25) unique not null,
	primary key(id)
);

create table users(
	id bigint auto_increment,
	name varchar(50) not null,
	email varchar(50) unique not null,
	password varchar(50) not null,
	role_id int not null,
	status_id int not null,
	average double not null,
	primary key(id),
	foreign key (role_id) references roles(id),
	foreign key (status_id) references statuses(id)
);

create table subjects(
	id int auto_increment,
	name varchar(50) unique not null,
	primary key(id)
);

create table complexity_levels(
	id int auto_increment,
	name varchar(20) unique not null,
	level int unique not null,
	primary key(id)
);

create table tests(
	id bigint auto_increment,
	name varchar(50) not null,
	duration int not null,
	subject_id int not null,
	level_id int not null,
	primary key(id),
	foreign key (subject_id) references subjects(id),
	foreign key (level_id) references complexity_levels(id)
);

create table questions(
	id bigint auto_increment,
	question_text varchar(500) not null,
	test_id bigint not null,
	primary key(id),
	foreign key (test_id) references tests(id) on delete cascade
);

create table answers(
	id bigint auto_increment,
	content varchar(500) not null,
	correct boolean not null,
	question_id bigint not null,
	primary key(id),
	foreign key (question_id) references questions(id) on delete cascade
);

create table results(
	id bigint auto_increment,
	user_id bigint not null,
	test_id bigint not null,
	result double not null,
	primary key(id),
	foreign key (user_id) references users(id) on delete cascade,
	foreign key (test_id) references tests(id) on delete cascade
);


insert into roles (name) values
	('admin'), ('client');
	
insert into statuses (name) values
	('unblocked'), ('blocked');	
	
insert into subjects (name) values
	('Introduction to Computer Science'), ('Data Structures and Algorithms'), ('Discrete Mathematics'), ('English');	

insert into complexity_levels(name, level) values
	('Low', 1);
insert into complexity_levels(name, level) values
	('Medium', 2);	
insert into complexity_levels(name, level) values
	('High', 3);		
	