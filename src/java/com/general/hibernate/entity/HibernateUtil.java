/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.hibernate.entity;

import com.general.util.beans.UtilClass;
import javassist.bytecode.analysis.Util;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author emoreno
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            System.out.println("Abre nuevo SESSION FACTORY: " + UtilClass.getCurrentTime());
//            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            
            Configuration configuration = new Configuration()
                        .configure("hibernate.cfg.xml");
            System.out.println("configuration in Utility==" + configuration); //---//syso1

            // Step2 : Creating ServiceRegistry using the
            // StandardServiceRegistryBuilder and Configuration defined in
            // Step 1
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            System.out.println("serviceRegistry==" + serviceRegistry); //syso 2

            // Step3 : Creating the SessionFactory using the Configuration
            // and serviceRegistry.
            sessionFactory = configuration
                    .buildSessionFactory(serviceRegistry);
            System.out.println("sessionFactory===" + sessionFactory);   //syso 3
            
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    
//    public static synchronized SessionFactory getInstnce() {
// 
//        if (sessionFactory == null) {
//            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
//            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
//                    .applySettings(configuration.getProperties());
//            sessionFactory = configuration.buildSessionFactory(builder.build());
//        }
//        return sessionFactory;
//    }
    
    
}
