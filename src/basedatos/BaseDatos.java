/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

import basedatos.entities.Equipos;
import basedatos.entities.Patrocinador;
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
        Patrocinador patrocinadorShell = new Patrocinador(0, "Shell");
        Patrocinador patrocinadorAlfaRomeo = new Patrocinador(0, "Alfa Romeo");
        Patrocinador patrocinadorRedBull = new Patrocinador(0, "Red Bull");
        Patrocinador patrocinadorRacingPoint = new Patrocinador(0, "Racing Point");
        Patrocinador patrocinadorTagHeuer = new Patrocinador(0, "Tag Heuger");
        Patrocinador patrocinadorBenetton = new Patrocinador(0, "Benetton");
        Patrocinador patrocinadorCamel = new Patrocinador(0, "Camel");
        Patrocinador patrocinadorRolex = new Patrocinador(0, "Rolex");
        
        em.getTransaction().begin();
        em.persist(patrocinadorShell);
        em.persist(patrocinadorAlfaRomeo);
        em.persist(patrocinadorRedBull);
        em.persist(patrocinadorRacingPoint);
        em.persist(patrocinadorTagHeuer);
        em.persist(patrocinadorBenetton);
        em.persist(patrocinadorCamel);
        em.persist(patrocinadorRolex);
        em.getTransaction().commit();
        
        Equipos equipoFerrari = new Equipos(0, "Ferrari");
        equipoFerrari.setPatrocinador(patrocinadorShell);
        Equipos equipoMclaren = new Equipos(0, "Mclaren");
        equipoMclaren.setPatrocinador(patrocinadorAlfaRomeo);
        
        em.getTransaction().begin();
        em.persist(equipoFerrari);
        em.persist(equipoMclaren);
        em.getTransaction().commit();
//                
        // Cerrar la conexión con la base de datos
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:BDProgramacion;shutdown=true"); 
        } catch (SQLException ex) { 
          }
    }
}