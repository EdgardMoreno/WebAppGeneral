/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import com.general.util.exceptions.CustomizerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.poi.util.IOUtils;

/**
 *
 * @author emoreno
 */
public class UtilClass implements Serializable{
    
    
    public static void addInfoMessage(/*String summary,*/ String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);        
    }
    
    public static void addErrorMessage(String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void addWarnMessage(String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static String getCurrentDay(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(new Date());
    }
    
    
    public static Date getCurrentDateTime(){        
        return new Date();
    }
    
    public static Date getCurrentDate() throws ParseException{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String var = df.format(new Date());        
        df = new SimpleDateFormat("dd/MM/yyyy");        
        return df.parse(var);
    }
    
    public static String getCurrentTime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(new Date());
    }
    
    public static String getCurrentTime_YYYYMMDD(){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }
    
    public static Integer getCurrentTime_YYYYMM(){
        DateFormat df = new SimpleDateFormat("yyyyMM");
        return Integer.valueOf(df.format(new Date()));
    }
    
    public static String getCurrentTime_YYYYMMDDHHMISS(){
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }
    
    public static BigDecimal convertDateToNumber(Date value){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return new BigDecimal(df.format(value));
    }
    
    public static Integer convertSringToNumber_YYYYMMDD(String value) throws ParseException{
        
        /*Convertir a fecha*/
        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        Date dtFecha = df1.parse(value);
        
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strFecha = df.format(dtFecha);
        
        return Integer.valueOf(strFecha);
    }
    
    public static BigDecimal convertDateToNumberYYYYMM(Date value){
        DateFormat df = new SimpleDateFormat("YYYYMM");
        return new BigDecimal(df.format(value));
    }    
    
    public static String convertDateToString(Date value){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(value);
    }
    
    public static String convertDateToStringDDMMYYYYHHMMSS(Date value){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(value);
    }
    
    public static String convertDateToStringHHMMSS(Date value){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(value);
    }
    
    public static Date convertStringToDate(String value) throws ParseException{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");        
        return df.parse(value);
    }
    
    
    /*Devuelve la fecha infinito*/
    public static Date getObtFecInf() throws ParseException{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");        
        return df.parse("31/12/2400");
    }
    
    /*Devuelve la fecha inicial*/
    public static Date getObtFecIni() throws ParseException{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");        
        return df.parse("01/01/1900");
    }
    
    /*Devuelve la fecha inicial*/
    public static int getObtFecIni_YYYYMMDD() throws ParseException{        
        return 19000101;
    }
    
     /*Devuelve la fecha infinito*/
    public static int getObtFecInf_YYYYMMDD() throws ParseException{
        return 24001231;
    }
    
    public static byte[] getBytes(InputStream is) throws IOException {

           int len;
           int size = 1024;
           byte[] buf;

           if (is instanceof ByteArrayInputStream) {
               size = is.available();
               buf = new byte[size];
               len = is.read(buf, 0, size);
           } else {
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               buf = new byte[size];
               while ((len = is.read(buf, 0, size))!= -1)
                   bos.write(buf, 0, len);
               buf = bos.toByteArray();
           }
           return buf;
    }
    
    
    /*MANEJDO DE ARCHIVOS*/
    
    /*Metodo que convierte de INPUTSTREAM -> FILE*/
    public static File stream2file (InputStream in, String fileName) throws Exception {        
        
        File tempFile = null;
        try {
            
            int i = fileName.lastIndexOf(".");
            int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

            String SUFFIX = "";
            if (i > p) {
                SUFFIX = fileName.substring(i);
            }else
                SUFFIX = ".unkonwed";
            
            String PREFIX = fileName.substring(0, i);
            tempFile = File.createTempFile(PREFIX, SUFFIX);
            tempFile.deleteOnExit();        
        
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(in, out);
        }catch(IOException ex){
            throw new Exception(ex.getMessage());
        }
        return tempFile;
    }
    
    /*Metodo que permite obtener la extension del archivo.*/
    public static String getExtensionArchivo(String nombreArchivo) throws Exception{
        
        String strExtension = "";
        try {
            
            int i = nombreArchivo.lastIndexOf(".");
            int p = Math.max(nombreArchivo.lastIndexOf('/'), nombreArchivo.lastIndexOf('\\'));

            if (i > p) {
                strExtension = nombreArchivo.substring(i + 1);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        
        return strExtension;        
    }
    
    
    
    /**
     * ELIMINA TODOS LOS ARCHIVOS DE UN DIRECTORIO
     * @param rutaDirectorio Ruta Absoluta del directorio del cual se borrara los archivos
     * @throws Exception 
     */
    public static void eliminarAchivosDirectorio(String rutaDirectorio) throws Exception{
        
        try{
            
            System.out.println(">>> ELIMINANDO ARCHIVOS DEL DIRECTORIO: " + rutaDirectorio);
            File directorio = new File(rutaDirectorio);
            File f;
            String[] archivos = directorio.list();
            if (archivos.length > 0) {
                for (String archivo : archivos) {
                    System.out.println(archivo);                
                    f = new File(rutaDirectorio + File.separator + archivo);
                    f.delete();
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }        
    }
    
    /**
     * ELIMINA UN ARCHIVO PASANDOLE LA RUTA ABSOLUTA
     * @param rutaArchivo Ruta Absoluta del directorio del cual se borrara los archivos
     * @throws Exception
     */
    public static void eliminarAchivo(String rutaArchivo) throws Exception{
        
        try{
            
            System.out.println(">>> ELIMINANDO ARCHIVO: " + rutaArchivo);
            File archivo = new File(rutaArchivo);
            if(archivo.exists())
                archivo.delete();
            
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }        
    }
    
    /**
     * ELIMINA UN ARCHIVO DE UN DIRECTORIO ESPECIFICO
     * @param rutaDirectorio Ruta Absoluta del directorio del cual se borrara los archivos
     * @param nomArchivo Nombre del archivo que a borrar
     * @throws Exception
     */
    public static void eliminarAchivo(String rutaDirectorio, String nomArchivo) throws Exception{
        
         try{
            
            System.out.println(">>> ELIMINANDO ARCHIVOS DEL DIRECTORIO: " + rutaDirectorio);
            File directorio = new File(rutaDirectorio);
            File f;
            String[] archivos = directorio.list();
            if (archivos.length > 0) {
                for (String archivo : archivos) {                    
                    if(archivo.contains(nomArchivo)){
                        f = new File(rutaDirectorio + File.separator + archivo);
                        if(f.exists())
                            f.delete();
                    }
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * PERMITE COPIAR EL CONTENIDO DE UN DIRECTORIO A HACIA OTRO DIRECTORIO
     * @param rutaOrigen Recibe la ruta absoluta del directorio desde donde se quiere copiar su contenido
     * @param rutaDestino Recibe la ruta absoluta del directorio hacia donde se quiere copiar su contenido
     * @throws CustomizerException
     * @throws Exception 
     */
    public static void copiarContenidoDirectorio(String rutaOrigen, String rutaDestino) throws CustomizerException, Exception{        
        
        try {

            System.out.println(">>> COPIANDO ARCHIVOS A DIRECTORIO: " + rutaDestino);
            File directorio = new File(rutaOrigen);
            String[] archivos = directorio.list();
            if (archivos.length > 0) {
                for (String archivo : archivos) {
                    System.out.println("Archivo:" + archivo);
                    Path origenPath = Paths.get(rutaOrigen + "\\" + archivo);
                    Path destinoPath = Paths.get(rutaDestino + "\\" + archivo);    
                    Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);                                                
                }
            }

        } catch (FileNotFoundException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (IOException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (Exception ex){
            throw new Exception(ex.getMessage());
        }            
    }
    
    /**
     * PERMITE COPIAR UN ARCHIVO DESDE UN DIRECTORIO HACIA OTRO DIRECTORIO
     * @param rutaOrigen Recibe la ruta absoluta del directorio desde donde se quiere copiar su contenido
     * @param rutaDestino Recibe la ruta absoluta del directorio hacia donde se quiere copiar su contenido
     * @param nomArchivo Recibe el nombre del archivo que se quiere copiar
     * @throws CustomizerException
     * @throws Exception 
     */
    public static void copiarArchivo(String rutaOrigen, String rutaDestino, String nomArchivo) throws CustomizerException, Exception{        
        
        try {

            System.out.println(">>> COPIANDO ARCHIVOS A DIRECTORIO: " + rutaDestino);
            File directorio = new File(rutaOrigen);
            String[] archivos = directorio.list();
            if (archivos.length > 0) {
                for (String archivo : archivos) {
                    if(archivo.contains(nomArchivo)){                                            
                        Path origenPath = Paths.get(rutaOrigen + "\\" + archivo);
                        Path destinoPath = Paths.get(rutaDestino + "\\" + archivo);    
                        Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);                                                
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (IOException ex) {
            throw new CustomizerException(ex.getMessage());
        } catch (Exception ex){
            throw new Exception(ex.getMessage());
        }            
    }
    
}
