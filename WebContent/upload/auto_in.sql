insert into lime_question(member_id, question_day, question_progname, question_content, question_attach, question_filename) values ('test',now(),'2','3','4','5');

insert into lime_reply (question_id)value (last_insert_id()); 

alter table lime_member modify member_id int not null auto_increment;

alter table lime_reply auto_increment =1;