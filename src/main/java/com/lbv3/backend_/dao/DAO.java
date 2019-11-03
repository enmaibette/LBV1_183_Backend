/*
package com.lbv3.backend_.dao;

import com.lbv3.backend_.model.Personen;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DAO {


    private static SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory;
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");

        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        sessionFactory = config.buildSessionFactory(serviceRegistryObj);
        return sessionFactory;
    }

    public void save(Personen person){
        Session session = null;
        try{
            session = buildSessionFactory().openSession();
            session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();
        } catch(Exception sqlException) {
            System.out.println(sqlException);
            if(null != session.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                session.getTransaction().rollback();
            }
            System.out.println("-----------");
            sqlException.printStackTrace();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }
}
*/
