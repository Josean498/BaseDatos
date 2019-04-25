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
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Jose
 */
public class ConsultaEquipos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BaseDatosPU");
        EntityManager em = emf.createEntityManager();
        
        
//        Query queryEquipos = em.createNamedQuery("Equipos.findAll");
//        List<Equipos> listEquipos = queryEquipos.getResultList();
        
//        for(Equipos equipos : listEquipos) {
//        System.out.println(equipos.getNombre());
//        }
        
//        Query queryEquipos = em.createNamedQuery("Equipos.findByNombre");
//        queryEquipos.setParameter("nombre", "Ferrari");
//        List<Equipos> listEquipos = queryEquipos.getResultList();
//        em.getTransaction().begin();
//        for(Equipos equipoFerrari : listEquipos) {
//        equipoFerrari.setId("11");
//        em.merge(equipoFerrari);
//        }
        
        em.getTransaction().commit();
        
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:BDProgramacion;shutdown=true"); 
        } catch (SQLException ex) { 
          }
        

    }
    
}
