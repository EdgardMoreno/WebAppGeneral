LOGIN
https://www.journaldev.com/7252/jsf-authentication-login-logout-database-example

-- Create table
create table SIC1USUARIO
(
  ID_USUARIO   NUMBER,
  COD_USUARIO  VARCHAR2(50),
  COD_PWD      VARCHAR2(50),
  FEC_CREACION DATE default sysdate,
  FEC_DESDE    DATE,
  FEC_HASTA    DATE,
  DES_NOTAS    VARCHAR2(4000),
  COD_EMAIL    VARCHAR2(2000)
)
tablespace TBS_SICDBA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table SIC1USUARIO
  add constraint PK_SIC1USUARIO primary key (ID_USUARIO)
  using index 
  tablespace TBS_SICDB
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table SIC1USUARIO
  add constraint FK_SIC1USUARIO_PERS foreign key (ID_USUARIO)
  references SIC1PERS (ID_PERS);
-- Create/Recreate check constraints 
alter table SIC1USUARIO
  add constraint NN_SIC1SIC1USUARIO01
  check ("ID_USUARIO" IS NOT NULL);
-- Create/Recreate indexes 
create index IX_SIC1USUARIO01 on SIC1USUARIO (COD_USUARIO)
  tablespace TBS_SICDBA_IDX
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
