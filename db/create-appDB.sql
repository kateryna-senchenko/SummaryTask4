drop database appDB;

create database appDB DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;


use appDB;

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
	name varchar(50) not null collate 'utf8_general_ci',
	email varchar(50) unique not null,
	password varchar(50) not null,
	role_id int not null,
	status_id int not null,
	average double not null,
	primary key(id),
	foreign key (role_id) references roles(id),
	foreign key (status_id) references statuses(id)
)collate='utf8_general_ci';

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
	
insert into users(name, email, password, role_id, status_id, average) values
	('Scout Finch', 's.f@gmail.com', '123', 1, 1, 0);	
insert into users(name, email, password, role_id, status_id, average) values
	('Алиса', 'boo@gmail.com', 'foo', 2, 1, 0);	
	
insert into subjects (name) values
	('Science'), ('Fiction');	

insert into complexity_levels(name, level) values
	('Low', 1);
insert into complexity_levels(name, level) values
	('Medium', 2);	
insert into complexity_levels(name, level) values
	('High', 3);	
	
insert into tests(name, duration, subject_id, level_id) values
	('Introduction to computer science with java', 1, 1, 2);
insert into tests(name, duration, subject_id, level_id) values
	('Arrays', 1, 1, 1);
insert into tests(name, duration, subject_id, level_id) values
	('Graphs', 1, 1, 3);
insert into tests(name, duration, subject_id, level_id) values
	('Array, ArrayList, LinkedList', 1, 1, 2);	
insert into tests(name, duration, subject_id, level_id) values
	('Getting to know Atish Bagchi', 1, 2, 3);
insert into tests(name, duration, subject_id, level_id) values
	('Theory of nearly everything', 1, 2, 1);
insert into tests(name, duration, subject_id, level_id) values
	('Essay basics', 1, 2, 1);
insert into tests(name, duration, subject_id, level_id) values
	('Punctuation', 1, 2, 2);	
	
insert into questions(question_text, test_id) values
	('Why on earth have you chosen such a horrible thing?', 1);
insert into questions(question_text, test_id) values
	('What are you doing?', 1);	
insert into questions(question_text, test_id) values
	('Are you alright?', 1);
insert into questions(question_text, test_id) values
	('Do you like sweets?', 1);	
	
insert into questions(question_text, test_id) values
	('Why on earth have you chosen such a horrible thing?', 2);
insert into questions(question_text, test_id) values
	('What are you doing?', 2);	
insert into questions(question_text, test_id) values
	('Are you alright?', 2);

insert into questions(question_text, test_id) values
	('What are you doing?', 3);	
insert into questions(question_text, test_id) values
	('Are you alright?', 3);
	
insert into questions(question_text, test_id) values
	('Why on earth have you chosen such a horrible thing?', 4);
insert into questions(question_text, test_id) values
	('What are you doing?', 4);	
insert into questions(question_text, test_id) values
	('Are you alright?', 4);
insert into questions(question_text, test_id) values
	('Do you like sweets?', 4);	
	
insert into questions(question_text, test_id) values
	('Why on earth have you chosen such a horrible thing?', 5);
insert into questions(question_text, test_id) values
	('What are you doing?', 5);	
insert into questions(question_text, test_id) values
	('Are you alright?', 5);
insert into questions(question_text, test_id) values
	('Do you like sweets?', 5);	
	
insert into questions(question_text, test_id) values
	('Why on earth have you chosen such a horrible thing?', 6);
insert into questions(question_text, test_id) values
	('What are you doing?', 6);	
insert into questions(question_text, test_id) values
	('Are you alright?', 6);

insert into questions(question_text, test_id) values
	('What are you doing?', 7);	
insert into questions(question_text, test_id) values
	('Are you alright?', 7);
	
insert into questions(question_text, test_id) values
	('Why on earth have you chosen such a horrible thing?', 8);
insert into questions(question_text, test_id) values
	('What are you doing?', 8);	
insert into questions(question_text, test_id) values
	('Are you alright?', 8);
insert into questions(question_text, test_id) values
	('Do you like sweets?', 8);		
	
insert into answers(content, correct, question_id) values
	('to have fun', true, 1);
insert into answers(content, correct, question_id) values
	('to make friends', false, 1);	
insert into answers(content, correct, question_id) values
	('to go mad', true, 1);	
	
insert into answers(content, correct, question_id) values
	('having fun', true, 2);
insert into answers(content, correct, question_id) values
	('making friends', false, 2);	
insert into answers(content, correct, question_id) values
	('going mad', true, 2);	
	
insert into answers(content, correct, question_id) values
	('yeap', false, 3);
insert into answers(content, correct, question_id) values
	('nah', false, 3);	
insert into answers(content, correct, question_id) values
	("I'll be ok", true, 3);
	
insert into answers(content, correct, question_id) values
	('very much', true, 4);
insert into answers(content, correct, question_id) values
	('not today', false, 4);	
insert into answers(content, correct, question_id) values
	('sometimes', false, 4);	
insert into answers(content, correct, question_id) values
	('hate them', false, 4);		
	
insert into answers(content, correct, question_id) values
	('to have fun', true, 5);
insert into answers(content, correct, question_id) values
	('to make friends', false, 5);	
insert into answers(content, correct, question_id) values
	('to go mad', true, 5);	
	
insert into answers(content, correct, question_id) values
	('having fun', true, 6);
insert into answers(content, correct, question_id) values
	('making friends', false, 6);	
insert into answers(content, correct, question_id) values
	('going mad', true, 6);	
	
insert into answers(content, correct, question_id) values
	('yeap', false, 7);
insert into answers(content, correct, question_id) values
	('nah', false, 7);	
insert into answers(content, correct, question_id) values
	("I'll be ok", true, 7);

insert into answers(content, correct, question_id) values
	('having fun', true, 8);
insert into answers(content, correct, question_id) values
	('making friends', false, 8);	
insert into answers(content, correct, question_id) values
	('going mad', true, 8);	
	
insert into answers(content, correct, question_id) values
	('yeap', false, 9);
insert into answers(content, correct, question_id) values
	('nah', false, 9);	
insert into answers(content, correct, question_id) values
	("I'll be ok", true, 9);

insert into answers(content, correct, question_id) values
	('to have fun', true, 10);
insert into answers(content, correct, question_id) values
	('to make friends', false, 10);	
insert into answers(content, correct, question_id) values
	('to go mad', true, 10);	
	
insert into answers(content, correct, question_id) values
	('having fun', true, 11);
insert into answers(content, correct, question_id) values
	('making friends', false, 11);	
insert into answers(content, correct, question_id) values
	('going mad', true, 11);	
	
insert into answers(content, correct, question_id) values
	('yeap', false, 12);
insert into answers(content, correct, question_id) values
	('nah', false, 12);	
insert into answers(content, correct, question_id) values
	("I'll be ok", true, 12);
	
insert into answers(content, correct, question_id) values
	('very much', true, 13);
insert into answers(content, correct, question_id) values
	('not today', false, 13);	
insert into answers(content, correct, question_id) values
	('sometimes', false, 13);	
insert into answers(content, correct, question_id) values
	('hate them', false, 13);		

insert into answers(content, correct, question_id) values
	('to have fun', true, 14);
insert into answers(content, correct, question_id) values
	('to make friends', false, 14);	
insert into answers(content, correct, question_id) values
	('to go mad', true, 14);	
	
insert into answers(content, correct, question_id) values
	('having fun', true, 15);
insert into answers(content, correct, question_id) values
	('making friends', false, 15);	
insert into answers(content, correct, question_id) values
	('going mad', true, 15);	
	
insert into answers(content, correct, question_id) values
	('yeap', false, 16);
insert into answers(content, correct, question_id) values
	('nah', false, 16);	
insert into answers(content, correct, question_id) values
	("I'll be ok", true, 16);
	
insert into answers(content, correct, question_id) values
	('very much', true, 17);
insert into answers(content, correct, question_id) values
	('not today', false, 17);	
insert into answers(content, correct, question_id) values
	('sometimes', false, 17);	
insert into answers(content, correct, question_id) values
	('hate them', false, 17);		
	
insert into answers(content, correct, question_id) values
	('to have fun', true, 18);
insert into answers(content, correct, question_id) values
	('to make friends', false, 18);	
insert into answers(content, correct, question_id) values
	('to go mad', true, 18);	
	
insert into answers(content, correct, question_id) values
	('having fun', true, 19);
insert into answers(content, correct, question_id) values
	('making friends', false, 19);	
insert into answers(content, correct, question_id) values
	('going mad', true, 19);	
	
insert into answers(content, correct, question_id) values
	('yeap', false, 20);
insert into answers(content, correct, question_id) values
	('nah', false, 20);	
insert into answers(content, correct, question_id) values
	("I'll be ok", true, 20);

insert into answers(content, correct, question_id) values
	('having fun', true, 21);
insert into answers(content, correct, question_id) values
	('making friends', false, 21);	
insert into answers(content, correct, question_id) values
	('going mad', true, 21);	
	
insert into answers(content, correct, question_id) values
	('yeap', false, 22);
insert into answers(content, correct, question_id) values
	('nah', false, 22);	
insert into answers(content, correct, question_id) values
	("I'll be ok", true, 22);

insert into answers(content, correct, question_id) values
	('to have fun', true, 23);
insert into answers(content, correct, question_id) values
	('to make friends', false, 23);	
insert into answers(content, correct, question_id) values
	('to go mad', true, 23);	
	
insert into answers(content, correct, question_id) values
	('having fun', true, 24);
insert into answers(content, correct, question_id) values
	('making friends', false, 24);	
insert into answers(content, correct, question_id) values
	('going mad', true, 24);	
	
insert into answers(content, correct, question_id) values
	('yeap', false, 25);
insert into answers(content, correct, question_id) values
	('nah', false, 25);	
insert into answers(content, correct, question_id) values
	("I'll be ok", true, 25);
	
insert into answers(content, correct, question_id) values
	('very much', true, 26);
insert into answers(content, correct, question_id) values
	('not today', false, 26);	
insert into answers(content, correct, question_id) values
	('sometimes', false, 26);	
insert into answers(content, correct, question_id) values
	('hate them', false, 26);		
