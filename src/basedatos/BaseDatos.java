/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

import basedatos.entities.Equipos;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author Jose
 */
public class BaseDatos {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Conectar con la base de datos
        Map<String, String> emfProperties = new HashMap<String, String>();
        emfProperties.put("javax.persistence.schema-generation.database.action", "create");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BaseDatosPU", emfProperties);
        EntityManager em = emf.createEntityManager();

        // REALIZAR AQUÍ LAS OPERACIONES SOBRE LA BASE DE DATOS
        // Introducción de datos en la base de datos mediante transacciones.
        Equipos equipoFerrari = new Equipos(0, "Ferrari");
        Equipos equipoMclaren = new Equipos(0, "Mclaren");

        em.getTransaction().begin();
        em.persist(equipoFerrari);
        em.persist(equipoMclaren);
        em.getTransaction().commit();
                
        // Cerrar la conexión con la base de datos
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:BDProgramacion;shutdown=true"); 
        } catch (SQLException ex) { 
          }
    }
}