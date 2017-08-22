/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/8/16 10:50:59                           */
/*==============================================================*/


drop table if exists Role;

drop table if exists file;

drop table if exists message;

drop table if exists operator_history;

drop table if exists project;

drop table if exists project_user_relation;

drop table if exists project_user_wallet_relation;

drop table if exists project_wallet;

drop table if exists token_detail;

drop table if exists token_money;

drop table if exists user;

drop table if exists user_role_relation;

drop table if exists user_wallet;

/*==============================================================*/
/* Table: Role                                                  */
/*==============================================================*/
create table Role
(
  id                   int(11) unsigned not null auto_increment,
  role_name            varchar(32),
  role_name_code       varchar(32),
  description          varchar(32),
  primary key (id)
);

alter table Role comment '角色';

/*==============================================================*/
/* Table: file                                                  */
/*==============================================================*/
create table file
(
  id                   int(11) unsigned not null auto_increment,
  file_url             text,
  project_id           int(11) unsigned,
  file_type            varchar(32),
  primary key (id)
);

alter table file comment '附件';

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table message
(
  id                   int(11) unsigned not null auto_increment,
  title                varchar(120),
  des                  varchar(255),
  content              text,
  state                tinyint,
  create_date          datetime,
  type                 tinyint,
  create_user_id       int(11) unsigned,
  pictureUrl           varchar(255),
  primary key (id)
);

alter table message comment '新闻消息';

/*==============================================================*/
/* Table: operator_history                                      */
/*==============================================================*/
create table operator_history
(
  id                   int(11) unsigned not null auto_increment,
  user_id              int(11) unsigned,
  message              varchar(255),
  ip                   varchar(32),
  ip_address           varchar(32),
  operator_time        timestamp,
  des                  text,
  primary key (id)
);

alter table operator_history comment '操作记录';

/*==============================================================*/
/* Table: project                                               */
/*==============================================================*/
create table project
(
  id                   int(11) unsigned not null auto_increment,
  name                 varchar(200) not null,
  name_cn              varchar(100),
  start_time           timestamp not null default CURRENT_TIMESTAMP,
  end_time             timestamp default CURRENT_TIMESTAMP,
  state                tinyint,
  third_endorsement    bool,
  output_token_money_detail_id int(11) unsigned,
  part_person_number   int,
  des                  text,
  create_user_id       int(11) unsigned,
  content              text,
  primary key (id)
);

alter table project comment 'ico项目';

/*==============================================================*/
/* Table: project_user_relation                                 */
/*==============================================================*/
create table project_user_relation
(
  id                   int(11) unsigned not null auto_increment,
  user_id              int(11) unsigned,
  project_id           int(11) unsigned,
  primary key (id)
);

alter table project_user_relation comment '用户项目关系';

/*==============================================================*/
/* Table: project_user_wallet_relation                          */
/*==============================================================*/
create table project_user_wallet_relation
(
  id                   int(11) unsigned not null auto_increment,
  user_wallet          int(11) unsigned,
  project_wallet       int(11) unsigned,
  transaction_number   varchar(255) DEFAULT NULL COMMENT '交易编号',
  primary key (id)
);

alter table project_user_wallet_relation comment '用户钱包项目钱包关系';

/*==============================================================*/
/* Table: project_wallet                                        */
/*==============================================================*/
create table project_wallet
(
  id                   int(11) unsigned not null auto_increment,
  wallet_address       varchar(255),
  wallet_private_key   varchar(255),
  token_money_id       int(11) unsigned,
  project_id           int(11) unsigned,
  des                  text,
  primary key (id)
);

alter table project_wallet comment '项目钱包';

/*==============================================================*/
/* Table: token_detail                                          */
/*==============================================================*/
create table token_detail
(
  id                   int(11) unsigned not null auto_increment,
  token_money_id       int(11) unsigned,
  current_number       int,
  ico_number           int,
  min_target_number    int,
  target_number        int,
  token_money_whitePaper_cn_url varchar(255),
  token_money_whitePaper_en_url varchar(255),
  input_token_money_project_id int(11) unsigned,
  primary key (id)
);

alter table token_detail comment '代币详情';

/*==============================================================*/
/* Table: token_money                                           */
/*==============================================================*/
create table token_money
(
  id                   int(11) unsigned not null auto_increment,
  name                 varchar(32) not null,
  name_en_short        varchar(32) not null,
  des                  text,
  official_url         varchar(255),
  twitter_url          varchar(255),
  github_url           varchar(255),
  primary key (id)
);

alter table token_money comment '代币信息';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
  id                   int(11) unsigned not null auto_increment,
  email_account        varchar(32) not null,
  password             varchar(255) not null,
  nick_name            varchar(32),
  real_name            varchar(32),
  phone                varchar(32),
  id_card              varchar(32),
  is_validate_email    bool,
  is_validate_phone    bool,
  is_validate_user     bool,
  is_enabled           bool default 1,
  des                  text,
  avator_url           varchar(255),
  verify_code          int(7),
  expire_date          timestamp,
  primary key (id)
);

alter table user comment '用户信息';

/*==============================================================*/
/* Table: user_role_relation                                    */
/*==============================================================*/
create table user_role_relation
(
  id                   int(11) unsigned not null auto_increment,
  user_id              int(11) unsigned,
  role_id              int(11) unsigned,
  primary key (id)
);

alter table user_role_relation comment '用户角色关系表';

/*==============================================================*/
/* Table: user_wallet                                           */
/*==============================================================*/
create table user_wallet
(
  id                   int(11) unsigned not null auto_increment,
  token_money_id       int(11) unsigned,
  user_id              int(11) unsigned,
  token_money_url      varchar(255) comment '需要加密',
  token_private_key    varchar(255),
  des                  text,
  state                tinyint,
  type                 tinyint comment '- 存入钱包
            - 转出钱包',
  primary key (id)
);

alter table user_wallet comment '用户钱包';

alter table file add constraint fk_file_2_project_project_id foreign key (project_id)
references project (id) on delete restrict on update restrict;

alter table message add constraint fk_message_2_user_user_id foreign key (create_user_id)
references user (id) on delete restrict on update restrict;

alter table operator_history add constraint fk_operator_2_user_user_id foreign key (user_id)
references user (id) on delete restrict on update restrict;

alter table project add constraint fk_project_2_user_create_user_id foreign key (create_user_id)
references user (id) on delete restrict on update restrict;

alter table project add constraint fk_project_2_token_money_detail_intput_token_money_detail_id foreign key (output_token_money_datail_id)
references token_detail (id) on delete restrict on update restrict;

alter table project_user_relation add constraint fk_user_project_2_user_user_id foreign key (project_id)
references user (id) on delete restrict on update restrict;

alter table project_user_relation add constraint fk_user_project_2_project_project_id foreign key (user_id)
references project (id) on delete restrict on update restrict;

alter table project_user_wallet_relation add constraint fk_user_project_wallet_2_user_wallet_user_wallet_id foreign key (user_wallet)
references user_wallet (id) on delete restrict on update restrict;

alter table project_user_wallet_relation add constraint fk_user_project_wallet_2_project_wallet_project_wallet_id foreign key (project_wallet)
references project_wallet (id) on delete restrict on update restrict;

alter table project_wallet add constraint fk_project_wallet_2_token_money_id foreign key (token_money_id)
references token_money (id) on delete restrict on update restrict;

alter table project_wallet add constraint FK_Reference_18 foreign key (project_id)
references project (id) on delete restrict on update restrict;

alter table token_detail add constraint fk_token_money_detail_2_project_id foreign key (input_token_money_project_id)
references project (id) on delete restrict on update restrict;

alter table token_detail add constraint fk_token_money_detail_2_token_money_token_money_id foreign key (token_money_id)
references token_money (id) on delete restrict on update restrict;

alter table user_role_relation add constraint fk_user_role_2_role_role_id foreign key (role_id)
references Role (id) on delete restrict on update restrict;

alter table user_role_relation add constraint fk_user_role_2_user_user_id foreign key (user_id)
references user (id) on delete restrict on update restrict;

alter table user_wallet add constraint fk_user_wallet_2_user_user_id foreign key (user_id)
references user (id) on delete restrict on update restrict;

alter table user_wallet add constraint fk_user_wallet_2_token_money_detail_token_money_detail_id foreign key (token_money_id)
references token_money (id) on delete restrict on update restrict;

