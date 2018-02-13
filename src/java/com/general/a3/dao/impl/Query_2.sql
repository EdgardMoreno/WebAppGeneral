/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Edgard
 * Created: 13/02/2018
 */

  -- PKG_SICMANTDOCU --> HEADER
  PROCEDURE PRC_SICRELADOCUPROD(X_ID_DOCU      NUMBER,
                                X_ID_PROD      NUMBER,                                
                                X_ID_TRELADOCU NUMBER,
                                X_FEC_DESDE    VARCHAR2,
                                X_FEC_HASTA    VARCHAR2,
                                X_DES_NOTAS    VARCHAR2,
                                X_NUM_VALOR    NUMBER,
                                X_NUM_MTODSCTO NUMBER,
                                X_NUM_CANTIDAD NUMBER,                                                                
                                X_ID_ERROR     OUT NUMBER,
                                X_DES_ERROR    OUT VARCHAR2,
                                X_FEC_ERROR    OUT DATE
                                
                                --------------------------------------------------------------------------------------------------------------
                                    --DESCRIPCION:   PROCEDIMIENTO QUE RELACIONA PRODUCTOS CON EL DOCUMENTO
                                    --PARAMETROS:
                                    --               X_ID_DOCU (VARIABLE DE INGRESO QUE CONTIENE EL IDENTIFICADOR DEL DOCUMENTO)
                                    --               X_ID_PROD (VARIABLE DE INGRESO QUE CONTIENE EL ESTADO DE LA RELACION)
                                    --               X_ID_TRELADOCU (VARIABLE DE INGRESO QUE CONTIENE EL IDENTIFICADOR DE LA RELACION DEL DOCUMENTO)                                   
                                    --               X_FEC_DESDE (VARIABLE DE INGRESO QUE CONTIENE LA FECHA INICIAL)
                                    --               X_FEC_HASTA (VARIABLE DE INGRESO QUE CONTIENE LA FECHA FINAL)
                                    --               X_DES_NOTAS (VARIABLE DE INGRESO QUE CONTIENE LA DESCRIPCION DE LAS NOTAS)                                    
                                    --               X_NUM_VALOR (VARIABLE DE INGRESO QUE CONTIENE LA DESCRIPCION DE LAS NOTAS)                                    
                                    --               X_NUM_MTODSCTO (VARIABLE DE INGRESO QUE CONTIENE LA DESCRIPCION DE LAS NOTAS)
                                    --               X_NUM_CANTIDAD (VARIABLE DE INGRESO QUE CONTIENE LA DESCRIPCION DE LAS NOTAS)
                                    --               X_ID_ERROR(VARIABLE DE SALIDA QUE CONTIENE EL CODIGO DEL ERROR)
                                    --               X_DES_ERROR(VARIABLE DE SALIDA QUE CONTIENE LA DESCRIPCION DEL ERROR)
                                    --               X_FEC_ERROR(VARIABLE DE SALIDA QUE CONTIENE LA FECHA DEL ERROR)
                                    --------------------------------------------------------------------------------------------------------------
                                

                                );


--PKG_SICMANTDOCU --> BODY
PROCEDURE PRC_SICRELADOCUPROD(X_ID_DOCU      NUMBER,
                                X_ID_PROD      NUMBER,                                
                                X_ID_TRELADOCU NUMBER,
                                X_FEC_DESDE    VARCHAR2,
                                X_FEC_HASTA    VARCHAR2,
                                X_DES_NOTAS    VARCHAR2,
                                X_NUM_VALOR    NUMBER,
                                X_NUM_MTODSCTO NUMBER,
                                X_NUM_CANTIDAD NUMBER,                                                                
                                X_ID_ERROR     OUT NUMBER,
                                X_DES_ERROR    OUT VARCHAR2,
                                X_FEC_ERROR    OUT DATE) IS

  L_FEC_DESDE DATE;
  L_FEC_HASTA DATE;

  BEGIN

    
    X_ID_ERROR  := 0;
    X_DES_ERROR := '';
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

          MERGE INTO SIC3DOCUPROD S1
          USING ( SELECT  X_ID_DOCU AS ID_DOCU
                         ,X_ID_PROD AS ID_PROD                         
                         ,X_ID_TRELADOCU AS ID_TRELADOCU
                         ,L_FEC_DESDE AS FEC_DESDE
                         ,L_FEC_HASTA AS FEC_HASTA
                         ,X_DES_NOTAS AS DES_NOTAS
                         ,X_NUM_VALOR AS NUM_VALOR
                         ,X_NUM_MTODSCTO AS NUM_MTODSCTO
                         ,X_NUM_CANTIDAD AS NUM_CANTIDAD
                   FROM DUAL ) S2 ON ( S1.ID_PROD = S2.ID_PROD
                                       AND S1.ID_DOCU = S2.ID_DOCU )
           WHEN MATCHED THEN
            UPDATE
               SET  S1.FEC_DESDE    = S2.FEC_DESDE
                   ,S1.FEC_HASTA    = S2.FEC_HASTA
                   ,S1.DES_NOTAS    = S2.DES_NOTAS
                   ,S1.NUM_VALOR    = S2.NUM_VALOR
                   ,S1.NUM_MTODSCTO = S2.NUM_MTODSCTO
                   ,S1.NUM_CANTIDAD = S2.NUM_CANTIDAD
                   
          WHEN NOT MATCHED THEN
            INSERT
              (  ID_DOCU
                ,ID_PROD
                ,ID_TRELADOCU
                ,FEC_DESDE
                ,FEC_HASTA
                ,DES_NOTAS
                ,NUM_VALOR
                ,NUM_MTODSCTO
                ,NUM_CANTIDAD )
            VALUES
              (  
                 S2.ID_DOCU
                ,S2.ID_PROD                
                ,S2.ID_TRELADOCU
                ,S2.FEC_DESDE
                ,S2.FEC_HASTA
                ,S2.DES_NOTAS
                ,S2.NUM_VALOR
                ,S2.NUM_MTODSCTO
                ,S2.NUM_CANTIDAD );
      
    EXCEPTION
      WHEN OTHERS THEN
          X_ID_ERROR  := SQLCODE;
          X_DES_ERROR := SQLERRM;
          X_FEC_ERROR := SYSDATE;
    END;
  END;