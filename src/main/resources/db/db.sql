create table donation_user
(
  id int auto_increment,
  email varchar(1000) null,
  password varchar(2000) null,
  first_name varchar(255) null,
  last_name varchar(255) null,
  phone varchar(255) null,
  user_type varchar(255) null,
  flag varchar(255) null,
  created_time timestamp null,
  constraint users_pk
    primary key (id)
);

create table donation
(
  id int auto_increment,
  users_id int null,
  order_id varchar(4000) null,
  donation_target int null,
  donation_channel int null,
  donation_amount int null,
  flag int null,
  created_time datetime null,
  modified_time datetime null,
  constraint donation_pk
    primary key (id)
);

create table donation_channel
(
  id int auto_increment,
  name varchar(255) null,
  constraint donation_channel_pk
    primary key (id)
);

INSERT INTO donation_channel (name) VALUES ('PayPal');
INSERT INTO donation_channel (name) VALUES ('Visa');
INSERT INTO donation_channel (name) VALUES ('MasterCard');
INSERT INTO donation_channel (name) VALUES ('AmericanExpress');


create table donation_target
(
  id int auto_increment,
  name varchar(4000) null,
  description varchar(4000) null,
  constraint donation_target_pk
    primary key (id)
);
