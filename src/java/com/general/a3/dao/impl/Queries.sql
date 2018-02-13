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

///////////////////////////////////////// SIC3DOCUPROD

-- Create table
create table SIC3DOCUPROD
(
  ID_DOCU      NUMBER,
  ID_PROD      NUMBER, 
  ID_TRELADOCU NUMBER,
  FEC_DESDE    DATE not null,
  FEC_HASTA    DATE,
  DES_NOTAS    VARCHAR2(4000),
  NUM_VALOR    NUMBER(9,3),
  NUM_MTODSCTO NUMBER(9,3),
  NUM_CANTIDAD NUMBER(6,2)
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
-- Add comments to the table 
comment on table SIC3DOCUPROD
  is 'Contiene la relacion entre producto y documentos';
-- Add comments to the columns 
comment on column SIC3DOCUPROD.ID_PROD
  is 'ID del producto';
comment on column SIC3DOCUPROD.ID_DOCU
  is 'Contiene el identificador del documento.';
comment on column SIC3DOCUPROD.ID_TRELADOCU
  is 'Contiene el identificador del tipo de relacion';
comment on column SIC3DOCUPROD.FEC_DESDE
  is 'Fecha de inicio';
comment on column SIC3DOCUPROD.FEC_HASTA
  is 'Fecha para versionamiento';
comment on column SIC3DOCUPROD.DES_NOTAS
  is 'Comentarios y/o observaciones';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SIC3DOCUPROD
  add constraint PK_SIC3DOCUPROD primary key ( ID_DOCU,ID_PROD, FEC_DESDE)
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
alter table SIC3DOCUPROD
  add constraint FK_SIC3DOCUPROD_DOCU foreign key (ID_DOCU)
  references SIC1DOCU (ID_DOCU) on delete cascade;
alter table SIC3DOCUPROD
  add constraint FK_SIC3DOCUPROD_PROD foreign key (ID_PROD)
  references SIC1PROD (ID_PROD) on delete cascade;
alter table SIC3DOCUPROD
  add constraint FK_SIC3DOCUPROD_TRELA foreign key (ID_TRELADOCU)
  references SIC8TRELA (ID_TRELA);
-- Create/Recreate check constraints 
alter table SIC3DOCUPROD
  add constraint NN_SIC3DOCUPROD01
  check ("ID_TRELADOCU" IS NOT NULL);
alter table SIC3DOCUPROD
  add constraint NN_SIC3DOCUPROD02
  check ("ID_DOCU" IS NOT NULL);
alter table SIC3DOCUPROD
  add constraint NN_SIC3DOCUPROD03
  check ("ID_PROD" IS NOT NULL);
-- Create/Recreate indexes 
create unique index IX_SIC3DOCUPROD on SIC3DOCUPROD (ID_PROD, ID_DOCU, ID_TRELADOCU, FEC_DESDE)
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



////////////////////////////////////////

CREATE OR REPLACE PACKAGE BODY "PKG_SICMANTGENERAL" AS
  --------------------------------------------------------------------------------------------------------------
  --DESCRIPCION:   PACKAGE DE MANTENIMIENTO GENERICO.
  --PARAMETROS:          
  --------------------------------------------------------------------------------------------------------------

  PROCEDURE PRC_OBTDOCUCORR ( X_ID_TIPODOCUCORRE NUMBER,
                              X_COD_ANIO         VARCHAR2,
                              X_COD_CKEY         VARCHAR2,
                              X_NUM_ACTUAL         OUT NUMBER,
                              X_ID_ERROR         OUT NUMBER,
                              X_DES_ERROR        OUT VARCHAR2,
                              X_FEC_ERROR        OUT DATE)
   IS
   X_FLG_EXIS NUMBER;
   BEGIN
   
   BEGIN
     SELECT ID_TIPODOCUCORRE
     INTO X_FLG_EXIS
     FROM VI_SICTIPODOCUCORRE
     WHERE ID_TIPODOCUCORRE = X_ID_TIPODOCUCORRE;
   EXCEPTION WHEN OTHERS THEN
      X_ID_ERROR:=-1;
      X_DES_ERROR:='NO SE ENCUENTRA EL TIPO DOCUMENTO';
      X_FEC_ERROR:=SYSDATE;
      RAISE_APPLICATION_ERROR(-20000,X_DES_ERROR);
   END;
   
  
   
   
END;

  PROCEDURE PRC_SICRELAVERSION(X_DES_VERSIONANTE     VARCHAR2,
                               X_DES_VERSIONADO      VARCHAR2,
                               X_ID_VERSIONANTE      NUMBER,
                               X_ID_TROLVERSIONANTE  NUMBER,
                               X_ID_VERSIONADO       NUMBER,
                               X_ID_TROLVERSIONADO   NUMBER,
                               X_ID_TRELAVERSIONANTE NUMBER,
                               X_ID_TIPOINST         NUMBER,
                               X_FEC_DESDE           VARCHAR2,
                               X_FEC_HASTA           VARCHAR2,
                               X_DES_NOTAS           VARCHAR2,
                               X_COD_VALORATRIB      VARCHAR2,
                               X_COD_VALORATRIBEXT   VARCHAR2,
                               X_FLG_CREAEVEN        NUMBER,
                               X_ID_ERROR            OUT NUMBER,
                               X_DES_ERROR           OUT VARCHAR2,
                               X_FEC_ERROR           OUT DATE
                               --------------------------------------------------------------------------------------------------------------
                               --DESCRIPCION:   PROCEDIMIENTO QUE REALIZA EL VERSIONAMIENTO DE LAS TABLAS SIC3 Y SIC7(TABLAS RELACION).
                               --PARAMETROS:    X_DES_VERSIONANTE(VARIABLE DE INGRESO QUE CONTIENE EL NOMBRE DE LA TABLA DEL VERSIONANTE)
                               --               X_DES_VERSIONADO(VARIABLE DE INGRESO QUE CONTIENE EL NOMBRE DE LA TABLA DEL VERSIONADO)
                               --               X_ID_VERSIONANTE(VARIABLE DE INGRESO QUE CONTIENE EL IDENTIFICADOR PRINCIPAL DEL VERSIONANTE)
                               --               X_ID_TROLVERSIONANTE(VARIABLE DE INGRESO QUE CONTIENE EL TIPO DE ROL DEL VERSIONANTE)
                               --               X_ID_VERSIONADO(VARIABLE DE INGRESO QUE CONTIENE EL IDENTIFICADOR PRINCIPAL DEL VERSIONADO)
                               --               X_ID_TROLVERSIONADO(VARIABLE DE INGRESO QUE CONTIENE EL TIPO DE ROL DEL VERSIONADO)
                               --               X_ID_TRELAVERSIONANTE(VARIABLE DE INGRESO QUE CONTIENE EL TIPO DE RELACION DEL VERSIONANTE)
                               --               X_FEC_DESDE(VARIABLE DE INGRESO QUE CONTIENE LA FECHA DE INICIO DE LA VERSION)
                               --               X_FEC_HASTA(VARIABLE DE INGRESO QUE CONTIENE LA FECHA FIN DE LA VERSION)
                               --               X_DES_NOTAS(VARIABLE DE INGRESO QUE CONTIENE DESCRIPCION DEL DATO A VERSIONAR)
                               --               X_ID_ERROR(VARIABLE DE SALIDA QUE CONTIENE EL CODIGO DEL ERROR)          
                               --               X_DES_ERROR(VARIABLE DE SALIDA QUE CONTIENE LA DESCRIPCION DEL ERROR)  
                               --               X_FEC_ERROR(VARIABLE DE SALIDA QUE CONTIENE LA FECHA DEL ERROR)  
                               --------------------------------------------------------------------------------------------------------------
                               ) IS
    L_FEC_PROXIMA       DATE;
    L_FEC_DESDE         DATE;
    L_SQLDINA           VARCHAR2(4000);
    L_DATA              VARCHAR2(4000);
    L_SQLDINA_NOT_EXIST VARCHAR2(4000);
    L_FEC_HASTA         DATE;
    
    L_EXISTE            NUMBER;
    L_DES_LOG      VARCHAR2(4000);
    L_DES_PROCEDIMIENTO    VARCHAR2(80);
    L_COD_SESSION          VARCHAR2(50);

  
  BEGIN    

 
    ------ LOG -----------------------
    L_DES_PROCEDIMIENTO := 'PRC_SICRELAVERSION';
    L_DES_LOG  := SUBSTR('X_DES_VERSIONADO=' || X_DES_VERSIONADO || chr(13)
                         || 'X_DES_VERSIONANTE=' || X_DES_VERSIONANTE || chr(13)
                         || 'X_ID_VERSIONANTE=' || X_ID_VERSIONANTE || chr(13)                         
                         || 'X_ID_TROLVERSIONANTE=' || X_ID_TROLVERSIONANTE || chr(13)
                         || 'X_ID_VERSIONADO=' || X_ID_VERSIONADO || chr(13)
                         || 'X_ID_TROLVERSIONADO=' || X_ID_TROLVERSIONADO || chr(13)
                         || 'X_ID_TRELAVERSIONANTE=' || X_ID_TRELAVERSIONANTE || chr(13)
                         || 'X_ID_TIPOINST=' || X_ID_TIPOINST || chr(13)
                         || 'X_FEC_DESDE=' || X_FEC_DESDE || chr(13)
                         || 'X_FEC_HASTA=' || X_FEC_HASTA || chr(13)
                         || 'X_FEC_HASTA=' || X_COD_VALORATRIB || chr(13)
                         || 'X_COD_VALORATRIBEXT=' || X_COD_VALORATRIBEXT || chr(13)
                         || 'X_FLG_CREAEVEN=' || X_FLG_CREAEVEN
                        , 1,4000);
					   
    PKG_SICMANTGENERAL.PRC_SICCREALOGSPARAM(x_des_proc    => l_des_procedimiento,
                                            x_des_menslog => l_des_log,
                                            x_cod_session => l_cod_session,
                                            x_id_error    => x_id_error,
                                            x_des_error   => x_des_error,
                                            x_fec_error   => x_fec_error);
                                            
   ------ FIN ---------------------



  
    BEGIN
    
      IF X_FEC_DESDE IS NULL THEN
        L_FEC_DESDE := SYSDATE;
      ELSE
        L_FEC_DESDE := TO_DATE(X_FEC_DESDE, 'dd/mm/yyyy hh24:mi:ss');
      END IF;
    
      IF X_FEC_HASTA IS NULL THEN
        L_FEC_HASTA := PKG_SICCONSGENERAL.FNC_SICOBTFECINF;
      ELSE
        L_FEC_HASTA := TO_DATE(X_FEC_HASTA, 'dd/mm/yyyy hh24:mi:ss');
      END IF;
    
      X_ID_ERROR := 0;
      
      --******************** CREAR QUERY **************************************************************
    
      L_SQLDINA := 'SELECT  nvl(min(FEC_DESDE),:L_FEC_HASTA) FROM SIC3' ||X_DES_VERSIONANTE || X_DES_VERSIONADO;
      
      --Identificacion del Versionante
      L_SQLDINA := L_SQLDINA || ' WHERE ID_' || X_DES_VERSIONANTE || ' = :X_ID_VERSIONANTE AND ';
    
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' THEN
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONANTE || ' = :X_ID_TROLVERSIONANTE AND ';
      END IF;

      -- Tipo de Relacion y fecha
      L_SQLDINA := L_SQLDINA || ' ID_TRELA' || X_DES_VERSIONANTE || ' = :X_ID_TRELAVERSIONANTE AND ';
      
      --Campo versionado
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' AND
         UPPER(X_DES_VERSIONADO) = 'PERS' THEN
         
        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || 'REL <> :X_ID_VERSIONADO AND ';
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONADO || 'REL <> :X_ID_TROLVERSIONADO AND ';

      -- 19-JULIO RELACION PRODUCTO-PRODUCTO
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'PROD' AND
            UPPER(X_DES_VERSIONADO) = 'PROD') THEN
        
        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || 'REL <> :X_ID_VERSIONADO AND ';
        
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN

        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || ' <> :X_ID_VERSIONADO AND ';
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONADO || ' <> :X_ID_TROLVERSIONADO AND ';
        
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'ESTA') OR
            (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN
            
        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || X_DES_VERSIONANTE || ' <> :X_ID_VERSIONADO AND ';
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONADO || X_DES_VERSIONANTE || ' = :X_ID_TROLVERSIONADO AND ';
      
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || X_DES_VERSIONANTE || ' <> :X_ID_VERSIONADO AND ';
        L_SQLDINA := L_SQLDINA || ' ID_TIPOINST  = :X_ID_TIPOINST AND ';
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONADO || X_DES_VERSIONANTE || ' = :X_ID_TROLVERSIONADO AND ';

      ELSIF UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'LUGAR' THEN
      
        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || 'REL <> :X_ID_VERSIONADO AND ';

      ELSIF UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'DOCU' THEN
      
        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || 'REL <> :X_ID_VERSIONADO AND ';
        
      ELSIF UPPER(X_DES_VERSIONADO) = 'TATRI' THEN
      
        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || ' = :X_ID_VERSIONADO AND ';
        L_SQLDINA := L_SQLDINA || ' COD_VALORATRIB <> ''' || NVL(X_COD_VALORATRIB, ' ') || ''' AND ';

      ELSE
        
        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || ' <> :X_ID_VERSIONADO AND ';
      
      END IF;
      
      L_SQLDINA := L_SQLDINA || ' FEC_DESDE > :L_FEC_DESDE';
      
      
      --*********************************************************************************
      --********************* EJECUTAR QUERY: SE OBTIENE LA FECHA_PROXIMA ***************
    
      IF (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
         (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
        USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO, L_FEC_DESDE;
      
      ELSIF UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'ESTA' THEN
        
        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
        USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO, L_FEC_DESDE;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'PROD') THEN
            
        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
          USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, L_FEC_DESDE;
          
      ELSIF UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'ESTA' THEN
        
        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
          USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, X_ID_TIPOINST, X_ID_TROLVERSIONADO, L_FEC_DESDE;
          
      ELSIF UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'EVEN' THEN

        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
          USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, L_FEC_DESDE;
          
      ELSIF UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'PRODITEM' THEN

        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
          USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, L_FEC_DESDE;
           
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PROD') THEN

        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
          USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, L_FEC_DESDE;

      ELSIF UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PERS' THEN
            
        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
          USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO, L_FEC_DESDE;

      /*AGREGADO 11/02/2018 - EDGARD*/
      ELSIF UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'PERS' THEN
            
        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
          USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, L_FEC_DESDE;          
      
      ELSE
        
        EXECUTE IMMEDIATE L_SQLDINA INTO L_FEC_PROXIMA
          USING L_FEC_HASTA, X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_TRELAVERSIONANTE, X_ID_VERSIONADO, L_FEC_DESDE;

      END IF;
      
    
      --******************************************************************************************************
      --******* CREAR QUERY: Cambiar la fecha de termino del registro anterior si es que aplica cambio. ******
      
      L_SQLDINA := 'UPDATE SIC3' || X_DES_VERSIONANTE || X_DES_VERSIONADO;
      L_SQLDINA := L_SQLDINA || ' SET FEC_HASTA = :L_FEC_DESDE ';
      
      --Identificacion del Versionante
      L_SQLDINA := L_SQLDINA || ' WHERE ID_' || X_DES_VERSIONANTE || ' = :X_ID_VERSIONANTE AND ';
    
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' THEN
        
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONANTE || ' = :X_ID_TROLVERSIONANTE AND ';

      END IF;
      

      -- Tipo de Relacion y fecha
      L_SQLDINA := L_SQLDINA || ' ID_TRELA' || X_DES_VERSIONANTE || ' = :X_ID_TRELAVERSIONANTE AND ';
      L_SQLDINA := L_SQLDINA || ' FEC_HASTA = :L_FEC_PROXIMA AND ';

      --Campo versionado
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'PERS' THEN
        L_SQLDINA := L_SQLDINA || '( ID_' || X_DES_VERSIONADO || 'REL <> :X_ID_VERSIONADO OR';
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONADO || 'REL <> :X_ID_TROLVERSIONADO )';

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR --19 JULIO
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN

        L_SQLDINA := L_SQLDINA || '( ID_' || X_DES_VERSIONADO || ' <> :X_ID_VERSIONADO OR';
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONADO || ' <> :X_ID_TROLVERSIONADO )';

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'ESTA') OR
            (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || X_DES_VERSIONANTE || ' <> :X_ID_VERSIONADO AND';
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONADO || X_DES_VERSIONANTE || ' = :X_ID_TROLVERSIONADO';

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || X_DES_VERSIONANTE || ' <> :X_ID_VERSIONADO AND';
        L_SQLDINA := L_SQLDINA || ' ID_TIPOINST  = :X_ID_TIPOINST AND';
        L_SQLDINA := L_SQLDINA || ' ID_TROL' || X_DES_VERSIONADO || X_DES_VERSIONANTE || ' = :X_ID_TROLVERSIONADO';

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'PROD'  AND UPPER(X_DES_VERSIONADO) = 'PROD') THEN

        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || 'REL <> :X_ID_VERSIONADO';

      ELSIF UPPER(X_DES_VERSIONADO) = 'TATRI' THEN

        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || ' = :X_ID_VERSIONADO AND';
        L_SQLDINA := L_SQLDINA || ' NVL(COD_VALORATRIB,'' '') <> ''' || NVL(X_COD_VALORATRIB, ' ') || '''';

      ELSE
        
        L_SQLDINA := L_SQLDINA || ' ID_' || X_DES_VERSIONADO || ' <> :X_ID_VERSIONADO';
        
      END IF;
     
      
      --******************************************************************************************************
      --******* EJECUTAR QUERY: Cambiar la fecha de termino del registro anterior si es que aplica cambio. ******
    
      IF (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
         (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO, X_ID_TROLVERSIONADO;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'ESTA') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO, X_ID_TROLVERSIONADO;
      
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO, X_ID_TIPOINST, X_ID_TROLVERSIONADO;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'PROD') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'EVEN') OR
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'DOCU') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND
            UPPER(X_DES_VERSIONADO) = 'PRODITEM') THEN
        
        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PROD') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN
            
        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO, X_ID_TROLVERSIONADO;
  
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN
            
        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO;
    
      ELSE
        
        EXECUTE IMMEDIATE L_SQLDINA
          USING L_FEC_DESDE, X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONADO;
      END IF;
    
      --******************************************************************************************************
      --******* CREAR QUERY: Inserta el nuevo registro si no esta activo el valor versionado. *************
      
      L_SQLDINA := ' INSERT INTO SIC3' || X_DES_VERSIONANTE || X_DES_VERSIONADO;
      L_SQLDINA := L_SQLDINA || ' (ID_' || X_DES_VERSIONANTE;
    
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' THEN
        L_SQLDINA := L_SQLDINA || ',ID_TROL' || X_DES_VERSIONANTE;
      END IF;
    
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'PERS' THEN

        L_SQLDINA := L_SQLDINA || ',ID_' || X_DES_VERSIONADO || 'REL';
        L_SQLDINA := L_SQLDINA || ',ID_TROL' || X_DES_VERSIONADO || 'REL';
        L_SQLDINA := L_SQLDINA || ',FLG_CREAEVEN';

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR --19 JULIO
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN
            
        L_SQLDINA := L_SQLDINA || ',ID_' || X_DES_VERSIONADO;
        L_SQLDINA := L_SQLDINA || ',ID_TROL' || X_DES_VERSIONADO;
        
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'ESTA') OR
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR --EDGARD(11/02/2018)
            (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        L_SQLDINA := L_SQLDINA || ',ID_' || X_DES_VERSIONADO || X_DES_VERSIONANTE;
        L_SQLDINA := L_SQLDINA || ',ID_TROL' || X_DES_VERSIONADO || X_DES_VERSIONANTE;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN
        
        L_SQLDINA := L_SQLDINA || ',ID_' || X_DES_VERSIONADO || X_DES_VERSIONANTE;
        L_SQLDINA := L_SQLDINA || ',ID_TROL' || X_DES_VERSIONADO || X_DES_VERSIONANTE;
        L_SQLDINA := L_SQLDINA || ',ID_TIPOINST';
        
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'EVEN') OR --MODIFICADO 19 JULIO
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PROD') OR --MODIFICADO 19 JULIO
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'DOCU') THEN

        L_SQLDINA := L_SQLDINA || ',ID_' || X_DES_VERSIONADO || 'REL';

      ELSE

        L_SQLDINA := L_SQLDINA || ',ID_' || X_DES_VERSIONADO;

      END IF;

      L_SQLDINA := L_SQLDINA || ',ID_TRELA' || X_DES_VERSIONANTE || ',FEC_DESDE, FEC_HASTA,DES_NOTAS';

      IF UPPER(X_DES_VERSIONADO) = 'TATRI' THEN 
        L_SQLDINA := L_SQLDINA || ',COD_VALORATRIB,COD_VALORATRIBEXT';
      END IF;


      --***** CREAR EL SELECT *****

      L_SQLDINA := L_SQLDINA || ') ';
      L_SQLDINA := L_SQLDINA || 'SELECT * FROM ( ';
      L_SQLDINA := L_SQLDINA || ' SELECT :X_ID_VERSIONANTE ID_' || X_DES_VERSIONANTE || ', ';
    
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' THEN
        L_SQLDINA := L_SQLDINA || ' :X_ID_TROLVERSIONANTE,';
      END IF;
    
      L_SQLDINA := L_SQLDINA || ' :X_ID_VERSIONADO,';

      IF UPPER(X_DES_VERSIONADO) = 'PERS' THEN
        L_SQLDINA := L_SQLDINA || ' :X_ID_TROLVERSIONADO,';
      END IF;
    
      IF (UPPER(X_DES_VERSIONANTE) = 'PERS' AND
         UPPER(X_DES_VERSIONADO) = 'PERS') THEN
         
        L_SQLDINA := L_SQLDINA || ' :X_FLG_CREAEVEN,';
      
      END IF;
      
      IF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'ESTA') OR
         (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
         (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        L_SQLDINA := L_SQLDINA || ' :X_ID_TROLVERSIONADO,';

      END IF;

      IF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN
        L_SQLDINA := L_SQLDINA || ' :X_ID_TROLVERSIONADO,';
        L_SQLDINA := L_SQLDINA || ' :X_ID_TIPOINST,';
      END IF;
      
      L_SQLDINA := L_SQLDINA || ' :X_ID_TRELAVERSIONANTE,';
      L_SQLDINA := L_SQLDINA || ' :L_FEC_DESDE,';
      L_SQLDINA := L_SQLDINA || ' :L_FEC_PROXIMA,';
      L_SQLDINA := L_SQLDINA || ' :X_DES_NOTAS';
      
      IF UPPER(X_DES_VERSIONADO) = 'TATRI' THEN
        L_SQLDINA := L_SQLDINA || ',:X_COD_VALORATRIB,:X_COD_VALORATRIBEXT';
      END IF;
      
      L_SQLDINA := L_SQLDINA || ' FROM DUAL ';
      L_SQLDINA := L_SQLDINA || ') WHERE NOT EXISTS ';
    
      L_SQLDINA_NOT_EXIST := '(SELECT 1 FROM SIC3' || X_DES_VERSIONANTE || X_DES_VERSIONADO;

      -- Tipo Relacion y fecha Hasta
      L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' WHERE ID_TRELA' || X_DES_VERSIONANTE ||
                             ' = :X_ID_TRELAVERSIONANTE AND ';
                             
      L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' FEC_HASTA = :L_FEC_PROXIMA AND ';
      -- Versionante
      L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_' || X_DES_VERSIONANTE ||
                             ' = :X_ID_VERSIONANTE AND ';
    
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' THEN
        
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_TROL' || X_DES_VERSIONANTE ||
                               ' = :X_ID_TROLVERSIONANTE  AND ';
      END IF;

      --Campo que se versiona
      IF UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'PERS' THEN
         
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_' || X_DES_VERSIONADO ||
                               'REL = :X_ID_VERSIONADO AND ';
        
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_TROL' || X_DES_VERSIONADO ||
                               'REL = :X_ID_TROLVERSIONADO)';
                               
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN

        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_' || X_DES_VERSIONADO ||
                               ' = :X_ID_VERSIONADO AND ';
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_TROL' || X_DES_VERSIONADO ||
                               ' = :X_ID_TROLVERSIONADO)';
                               
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'ESTA') OR
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR --EDGARD
            (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_' || X_DES_VERSIONADO || X_DES_VERSIONANTE ||
                               ' = :X_ID_VERSIONADO AND ';
                               
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_TROL' || X_DES_VERSIONADO || X_DES_VERSIONANTE ||
                               ' = :X_ID_TROLVERSIONADO)';
                               
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN

        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_' || X_DES_VERSIONADO || X_DES_VERSIONANTE ||
                               ' = :X_ID_VERSIONADO AND ';
                               
        --CAMBIADO POR JZ PARA QUE NO VERSIONE POR INSTANCIA 
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_TIPOINST = :X_ID_TIPOINST AND ';
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_TROL' || X_DES_VERSIONADO || X_DES_VERSIONANTE ||
                               ' = :X_ID_TROLVERSIONADO)';
                               
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'EVEN') OR
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PROD') THEN

        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_' || X_DES_VERSIONADO ||
                               'REL = :X_ID_VERSIONADO)';
                               
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'TATRI') THEN

        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_' || X_DES_VERSIONADO ||
                               ' = :X_ID_VERSIONADO AND';
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST ||
                               ' COD_VALORATRIB = ''' || X_COD_VALORATRIB ||
                               ''')';
      ELSE
        L_SQLDINA_NOT_EXIST := L_SQLDINA_NOT_EXIST || ' ID_' ||
                               X_DES_VERSIONADO || ' = :X_ID_VERSIONADO)';
      END IF;
      
      L_SQLDINA := L_SQLDINA || L_SQLDINA_NOT_EXIST;
      
    
      
       --******************************************************************************************************
      --******* EJECUTAR QUERY: Inserta el nuevo registro si no esta activo el valor versionado. *************
      
       
      IF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'ESTA') THEN
        EXECUTE IMMEDIATE L_SQLDINA
          
          USING X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO, X_ID_TIPOINST, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TIPOINST, X_ID_TROLVERSIONADO;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'ESTA') OR            
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'PERS') OR
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN
            
            SELECT COUNT(*)
            INTO L_EXISTE
            FROM SIC1PRODITEM
            WHERE ID_PROD = X_ID_VERSIONANTE;            

            
        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO;
      
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'PERS' AND UPPER(X_DES_VERSIONADO) = 'PERS') THEN
        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO, X_FLG_CREAEVEN, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO;
      
      ELSIF UPPER(X_DES_VERSIONADO) IN ('PERS', 'ESTA') THEN
        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_VERSIONADO, X_ID_TROLVERSIONADO;
      
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'LUGAR') OR
            (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'DOCU') OR 
            (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'PROD') OR 
            (UPPER(X_DES_VERSIONANTE) = 'LUGAR' AND UPPER(X_DES_VERSIONADO) = 'PROD') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_VERSIONADO;
      
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'TAREA' AND UPPER(X_DES_VERSIONADO) = 'TATRI') OR
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'TATRI') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, NVL(X_COD_VALORATRIB, ' '), NVL(X_COD_VALORATRIBEXT, ' '), X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_VERSIONADO;
      
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'EVEN' AND UPPER(X_DES_VERSIONADO) = 'EVEN') OR
            (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'DOCU') THEN

        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_VERSIONADO;
      
      ELSIF (UPPER(X_DES_VERSIONANTE) = 'DOCU' AND UPPER(X_DES_VERSIONADO) = 'PRODITEM') THEN
        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_VERSIONADO;

      ELSIF (UPPER(X_DES_VERSIONANTE) = 'PROD' AND UPPER(X_DES_VERSIONADO) = 'DOCU') THEN
        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_VERSIONADO, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_VERSIONADO;

      ELSE
        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_VERSIONADO, X_ID_TRELAVERSIONANTE, L_FEC_DESDE, L_FEC_PROXIMA, X_DES_NOTAS, X_ID_TRELAVERSIONANTE, L_FEC_PROXIMA, X_ID_VERSIONANTE, X_ID_TROLVERSIONANTE, X_ID_VERSIONADO;
      
      END IF;
    
      --X_ID_ERROR := -1993;
      --X_DES_ERROR := L_SQLDINA;
    
    EXCEPTION
      WHEN OTHERS THEN
        IF X_ID_ERROR = 0 THEN
          L_DATA := ' X_DES_VERSIONANTE= ' || X_DES_VERSIONANTE ||
                    ' X_DES_VERSIONADO= ' || X_DES_VERSIONADO ||
                    ' X_ID_VERSIONANTE= ' || X_ID_VERSIONANTE ||
                    ' X_ID_TROLVERSIONANTE= ' || X_ID_TROLVERSIONANTE ||
                    ' X_ID_VERSIONADO= ' || X_ID_VERSIONADO ||
                    ' X_ID_TROLVERSIONADO= ' || X_ID_TROLVERSIONADO ||
                    ' X_ID_TRELAVERSIONANTE= ' || X_ID_TRELAVERSIONANTE ||
                    ' X_ID_TIPOINST= ' || X_ID_TIPOINST || 
                    ' X_FEC_DESDE= ' || X_FEC_DESDE || 
                    'X_FEC_HASTA=' || X_FEC_HASTA ||
                    'X_DES_NOTAS=' || X_DES_NOTAS || 
                    'X_COD_VALORATRIB= ' || X_COD_VALORATRIB || 
                    'X_COD_VALORATRIBEXT= ' || X_COD_VALORATRIBEXT || 
                    'X_FLG_CREAEVEN= ' || X_FLG_CREAEVEN;
        
          X_ID_ERROR  := SQLCODE;
          X_DES_ERROR := 'PROCESO: PRC_SICRELAVERSION ' || '<br/>' || SQLERRM || 
                         '-- QUERY: ' || L_SQLDINA ||
                         '-- PARAMETRO: ' || L_DATA;
          X_FEC_ERROR := SYSDATE;
        END IF;
    END;
  END;

  PROCEDURE PRC_SICCREAIDEN(X_ID_IDEN     VARCHAR2,
                            X_ID_TIPOIDEN NUMBER,
                            X_COD_IDEN    VARCHAR2,
                            X_FEC_DESDE   VARCHAR2,
                            X_FEC_HASTA   VARCHAR2,
                            X_ID_ERROR    OUT NUMBER,
                            X_DES_ERROR   OUT VARCHAR2,
                            X_FEC_ERROR   OUT DATE)
  --------------------------------------------------------------------------------------------------------------
    --DESCRIPCION:   PROCEDIMIENTO QUE CREA EL IDENTIFICADOR EN CUALQUIER ENTIDAD.
    --PARAMETROS:    
    --               X_ID_IDEN (CODIGO INTERNO DEL IDENTIFICADOR DE LA ENTIDAD)
    --               X_ID_TIPOIDEN (CODIGO INTERNO DEL TIPO DE IDENTIFICADOR)
    --               X_COD_IDEN (CODIGO DEL IDENTIFICADOR)
    --               X_FEC_DESDE (FECHA DE INICIO DEL IDENTIFICADOR)
    --               X_FEC_HASTA (FECHA DE TERMINO DE VIGENCIA DEL IDENTIFICADOR)
    --               X_ID_ERROR(VARIABLE DE SALIDA QUE CONTIENE EL CODIGO DEL ERROR)          
    --               X_DES_ERROR(VARIABLE DE SALIDA QUE CONTIENE LA DESCRIPCION DEL ERROR)  
    --               X_FEC_ERROR(VARIABLE DE SALIDA QUE CONTIENE LA FECHA DEL ERROR)  
    --------------------------------------------------------------------------------------------------------------
   IS
  -- Modificacion (La transaccion de la generacion del ID debera ser independiente de la creacion de la Entidad) 
--  pragma autonomous_transaction;
   
    L_FEC_DESDE DATE;
    L_FEC_HASTA DATE;
    L_SQLDINA   VARCHAR2(4000);
    L_ID_MAX    NUMBER;
  BEGIN
  
    BEGIN
    
      IF X_FEC_DESDE IS NULL THEN
        L_FEC_DESDE := SYSDATE;
      ELSE
        L_FEC_DESDE := TO_DATE(X_FEC_DESDE, 'dd/mm/yyyy hh24:mi:ss');
      END IF;
    
      IF X_FEC_HASTA IS NULL THEN
        L_FEC_HASTA := PKG_SICCONSGENERAL.FNC_SICOBTFECINF;
      ELSE
        L_FEC_HASTA := TO_DATE(X_FEC_HASTA, 'dd/mm/yyyy hh24:mi:ss');
      END IF;
    
      X_ID_ERROR := 0;    

    
      IF PKG_SICCONSGENERAL.FNC_SICOBTIDIDEN(X_ID_IDEN,X_ID_TIPOIDEN,X_COD_IDEN) = -1 THEN
      
        L_SQLDINA := 'INSERT INTO SIC1IDEN' || X_ID_IDEN;
        L_SQLDINA := L_SQLDINA ||'(ID_TIPOIDEN,COD_IDEN,FEC_DESDE,FEC_HASTA,ID_' ||X_ID_IDEN || ')';
        L_SQLDINA := L_SQLDINA ||' VALUES(:X_ID_TIPOIDEN,:X_COD_IDEN,:L_FEC_DESDE,:L_FEC_HASTA,PKG_SICCONSGENERAL.FNC_SICOBTMAXID' || X_ID_IDEN ||')';
        
        EXECUTE IMMEDIATE L_SQLDINA
          USING X_ID_TIPOIDEN, X_COD_IDEN, L_FEC_DESDE, L_FEC_HASTA;
      
      END IF;
   
--    COMMIT;
          
    EXCEPTION
      WHEN OTHERS THEN
        IF X_ID_ERROR = 0 THEN
          X_ID_ERROR  := SQLCODE;
          X_DES_ERROR := 'PRC_SICCREAIDEN: ' || SQLERRM;
          X_FEC_ERROR := SYSDATE;
          ROLLBACK;
        END IF;
    END;
  
          


      
  END;

  PROCEDURE PRC_SICCREAPETICIONENVIOEMAIL(X_ID_EMAILPEND NUMBER,
                                          X_FEC_CREACION DATE,
                                          X_DES_EMAIL    VARCHAR2,
                                          X_ID_TIPOMENS  NUMBER,
                                          X_FLG_ATENDIDO NUMBER,
                                          X_DES_ASUNTO   VARCHAR2,
                                          X_DES_MENSAJE  VARCHAR2,
                                          X_NUM_INTENTOS NUMBER,
                                          X_ID_EVEN      NUMBER,
                                          X_FLG_ENVIADO  NUMBER,
                                          X_ID_ERROR     OUT NUMBER,
                                          X_DES_ERROR    OUT VARCHAR2,
                                          X_FEC_ERROR    OUT DATE
                                          --------------------------------------------------------------------------------------------------------------
                                          --DESCRIPCION:   PROCEDIMIENTO QUE CREA UNA PETICION DE ENVIO DE EMAIL
                                          --PARAMETROS:    
                                          --               X_ID_EMAILPEND (ID CORRELATIVO POR EMAIL PEND)
                                          --               X_FEC_CREACION (FECHA DE CREACION)
                                          --               X_DES_EMAIL (DESCRIPCION DEL EMAIL )
                                          --               X_ID_TIPOMENS (TIPO DE MENSAJE)
                                          --               X_FLG_ATENDIDO (INDICA SI ESTA ATENDIDO)
                                          --               X_DES_ASUNTO (ASUNTO DEL EMAIL)
                                          --               X_DES_MENSAJE (MENSAJE DEL EMAIL)
                                          --               X_ID_EVEN (ID DEL EVENTO QUE REALIZA EL ENVIO)
                                          --               X_NUM_INTENTOS (NUMERO DE INTENTOS DE ENVIO)
                                          --               X_FLG_ENVIADO (INDICA SI SE HA ENVIADO EL EMAIL O NO)    
                                          --               X_ID_ERROR (VARIABLE DE SALIDA QUE CONTIENE EL CODIGO DEL ERROR)          
                                          --               X_DES_ERROR (VARIABLE DE SALIDA QUE CONTIENE LA DESCRIPCION DEL ERROR)  
                                          --               X_FEC_ERROR (VARIABLE DE SALIDA QUE CONTIENE LA FECHA DEL ERROR)  
                                          --------------------------------------------------------------------------------------------------------------
                                          ) IS
  
  BEGIN
    X_ID_ERROR := 0;
  
    BEGIN
      IF X_ID_EMAILPEND = 0 THEN
        INSERT INTO SIC4EMAILPEND
          (ID_EMAILPEND,
           FEC_CREACION,
           DES_EMAIL,
           ID_TIPOMENS,
           FLG_ATENDIDO,
           DES_ASUNTO,
           DES_MENSAJE,
           NUM_INTENTOS,
           ID_EVEN,
           FLG_ENVIADO,
           ID_ERROR,
           DES_ERROR)
        VALUES
          ((SELECT MAX(ID_EMAILPEND) + 1 FROM SIC4EMAILPEND),
           X_FEC_CREACION,
           X_DES_EMAIL,
           X_ID_TIPOMENS,
           X_FLG_ATENDIDO,
           X_DES_ASUNTO,
           X_DES_MENSAJE,
           X_NUM_INTENTOS,
           X_ID_EVEN,
           X_FLG_ENVIADO,
           X_ID_ERROR,
           X_DES_ERROR);
      ELSE
      
        UPDATE SIC4EMAILPEND
           SET FEC_CREACION = X_FEC_CREACION,
               DES_EMAIL    = X_DES_EMAIL,
               ID_TIPOMENS  = X_ID_TIPOMENS,
               FLG_ATENDIDO = X_FLG_ATENDIDO,
               DES_ASUNTO   = X_DES_ASUNTO,
               DES_MENSAJE  = X_DES_MENSAJE,
               NUM_INTENTOS = X_NUM_INTENTOS,
               ID_EVEN      = X_ID_EVEN,
               FLG_ENVIADO  = X_FLG_ENVIADO,
               ID_ERROR     = X_ID_ERROR,
               DES_ERROR    = X_DES_ERROR
         WHERE ID_EMAILPEND = X_ID_EMAILPEND;
      
      END IF;
    
    EXCEPTION
      WHEN OTHERS THEN
        IF X_ID_ERROR = 0 THEN
          X_ID_ERROR  := SQLCODE;
          X_DES_ERROR := SQLERRM;
          X_FEC_ERROR := SYSDATE;
        END IF;
    END;
  
  END;

  PROCEDURE PRC_SICCREAGENERAL(X_DES_GENERAL          VARCHAR2,
                               X_COD_VALORTIPOGENERAL VARCHAR2,
                               X_COD_VALORGENERAL     VARCHAR2,
                               X_ID_GENERALREL        NUMBER,
                               X_COD_VALORDEFECTO     NUMBER,
                               X_COD_VALORTIPOENTIDAD VARCHAR2,
                               X_FEC_DESDE            VARCHAR2,
                               X_FEC_HASTA            VARCHAR2,
                               X_ID_ERROR             OUT NUMBER,
                               X_DES_ERROR            OUT VARCHAR2,
                               X_FEC_ERROR            OUT DATE) IS
    --------------------------------------------------------------------------------------------------------------
    --DESCRIPCION:   PROCEDIMIENTO QUE CREA UN ELEMENTO DENTRO DE LA TABLA SIC1GENERAL
    --PARAMETROS:    
    --               X_DES_GENERAL  (DESCRIPCION DEL ELEMENTO)
    --               X_COD_VALORTIPOGENERAL (CODIGO QUE IDENTIFICA EL TIPO DE TABLA GENERAL)
    --               X_COD_VALORGENERAL (CODIGO QUE PERMITE BUSCAR A UN ELEMENTO GENERAL)
    --               X_ID_GENERALREL (IDENTIICADOR DEL ELEMENTO RELACIONADO)
    --               X_COD_VALORDEFECTO (INDICA SI ES VALOR POR DEFECTO)
    --               X_COD_VALORTIPOENTIDAD (INDICA EL NOMBRE DEL TIPO DE ENTIDAD)
    --               X_FEC_DESDE (FECHA DE INICIO DE VIGENCIA DEL ELEMENTO)
    --               X_FEC_HASTA (FECHA DE TERMINO DE VIGENCIA DEL ELEMENTO)
    --               X_ID_ERROR(VARIABLE DE SALIDA QUE CONTIENE EL CODIGO DEL ERROR)          
    --               X_DES_ERROR(VARIABLE DE SALIDA QUE CONTIENE LA DESCRIPCION DEL ERROR)  
    --               X_FEC_ERROR(VARIABLE DE SALIDA QUE CONTIENE LA FECHA DEL ERROR)  
    --------------------------------------------------------------------------------------------------------------
  
    L_ID_GENERAL    NUMBER;
    L_ID_GENERALREL NUMBER;
  
  BEGIN
    X_ID_ERROR := 0;
  
    BEGIN
    
      /* IF X_FEC_DESDE IS NULL THEN
        L_FEC_DESDE := PKG_SICCONSGENERAL.FNC_SICOBTFECINI;
      ELSE
        L_FEC_DESDE := TO_DATE(X_FEC_DESDE, 'dd/mm/yyyy hh24:mi:ss');
      END IF;*/
    
      L_ID_GENERAL := PKG_SICCONSGENERAL.FNC_SICOBTMAXIDGENERAL;
    
      IF NVL(X_ID_GENERALREL, -1) != -1 THEN
        SELECT COUNT(1)
          INTO L_ID_GENERALREL
          FROM SIC1GENERAL
         WHERE ID_GENERAL = X_ID_GENERALREL;
      
        IF L_ID_GENERALREL = 0 THEN
          RAISE_APPLICATION_ERROR(-20000,
                                  'El codigo de relacion no es valido');
        END IF;
      
      END IF;
    
      MERGE INTO SIC1GENERAL S1
      USING (SELECT L_ID_GENERAL           ID_GENERAL,
                    X_DES_GENERAL          DES_GENERAL,
                    X_COD_VALORTIPOGENERAL COD_VALORTIPOGENERAL,
                    X_COD_VALORGENERAL     COD_VALORGENERAL,
                    X_ID_GENERALREL        ID_GENERALREL,
                    X_COD_VALORDEFECTO     COD_VALORDEFECTO,
                    X_COD_VALORTIPOENTIDAD COD_VALORTIPOENTIDAD
               FROM DUAL) S2
      ON (S1.COD_VALORTIPOGENERAL = S2.COD_VALORTIPOGENERAL AND S1.COD_VALORGENERAL = S2.COD_VALORGENERAL)
      WHEN MATCHED THEN
        UPDATE
           SET S1.DES_GENERAL          = S2.DES_GENERAL,
               S1.ID_GENERALREL        = S2.ID_GENERALREL,
               S1.COD_VALORDEFECTO     = S2.COD_VALORDEFECTO,
               S1.COD_VALORTIPOENTIDAD = S2.COD_VALORTIPOENTIDAD
      WHEN NOT MATCHED THEN
        INSERT
          (ID_GENERAL,
           DES_GENERAL,
           COD_VALORTIPOGENERAL,
           COD_VALORGENERAL,
           ID_GENERALREL,
           COD_VALORDEFECTO,
           COD_VALORTIPOENTIDAD)
        VALUES
          (s2.ID_GENERAL,
           S2.DES_GENERAL,
           S2.COD_VALORTIPOGENERAL,
           S2.COD_VALORGENERAL,
           S2.ID_GENERALREL,
           S2.COD_VALORDEFECTO,
           S2.COD_VALORTIPOENTIDAD);
    
    EXCEPTION
      WHEN OTHERS THEN
        IF X_ID_ERROR = 0 THEN
          X_ID_ERROR  := SQLCODE;
          X_DES_ERROR := SQLERRM;
          X_FEC_ERROR := SYSDATE;
        END IF;
    END;
  
  END;

  PROCEDURE PRC_SICCREALOGSPARAM(X_DES_PROC    VARCHAR2,
                                 X_DES_MENSLOG VARCHAR2,
                                 X_COD_SESSION OUT VARCHAR2,
                                 X_ID_ERROR    OUT NUMBER,
                                 X_DES_ERROR   OUT VARCHAR2,
                                 X_FEC_ERROR   OUT DATE) 
                                 
IS
PRAGMA AUTONOMOUS_TRANSACTION;  
    --------------------------------------------------------------------------------------------------------------
    --DESCRIPCION:   PROCEDIMIENTO QUE CREA UN ELEMENTO DENTRO DE LA TABLA SIC1GENERAL
    --PARAMETROS:    
    --------------------------------------------------------------------------------------------------------------
  BEGIN
    X_ID_ERROR := 0;
  
    IF FNC_SICESTAVACIO(X_COD_SESSION) = 1 THEN
      X_COD_SESSION := FNC_SICOBTCOD;
    END IF;
  
    INSERT INTO SIC1LOGS
      (FEC_EJEC,
       DES_PROC,
       DES_CONT,
       DES_MENSLOG,
       COD_SESSIONID,
       FEC_DESDE,
       FEC_HASTA)
    VALUES
      (SYSDATE,
       X_DES_PROC,
       'PARAMETROS',
       X_DES_MENSLOG,
       X_COD_SESSION,
       SYSDATE,
       pkg_sicconsgeneral.FNC_SICOBTFECINF);
       
       COMMIT;
       
  END;

  PROCEDURE PRC_SICACTULOGSPARAM(X_COD_SESSION VARCHAR2) 
    
    IS
  PRAGMA AUTONOMOUS_TRANSACTION; 
    --------------------------------------------------------------------------------------------------------------
    --DESCRIPCION:   PROCEDIMIENTO QUE CREA ACTUALIZA EN EL LOG EL TIEMPO EN QUE TERMINO EL PROCESO
    --PARAMETROS:    
    --------------------------------------------------------------------------------------------------------------
  BEGIN
    
    UPDATE SIC1LOGS
       SET FEC_HASTA = SYSDATE
     WHERE COD_SESSIONID = X_COD_SESSION;
     
     COMMIT;
     
  END;

  

  

  
  
END PKG_SICMANTGENERAL;
