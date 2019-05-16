/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

import basedatos.entities.Equipos;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Jose
 */
public class Consultas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BaseDatosPU");
        EntityManager em = emf.createEntityManager();
//        
//     // Consulta de objetos con query para la lista de equipos, findAll para toda la base de datos.
//        Query queryEquipos = em.createNamedQuery("Equipos.findAll");
//        List<Equipos> listEquipos = queryEquipos.getResultList();
//       
//        for(Equipos Equipos : listEquipos) {
//        System.out.println(Equipos.getNombre());
//        }
//        
//     // Modificación de objetos. Otro query para buscar por nombre(findByNombre), no busca en toda la base de datos.
//        Query queryEquipoFerrari = em.createNamedQuery("Equipos.findByNombre");
//        queryEquipoFerrari.setParameter("nombre", "Ferrari");
//        List<Equipos> listEquipoFerrari = queryEquipoFerrari.getResultList();
//        
//        em.getTransaction().begin();
//        for(Equipos Equipos : listEquipoFerrari) {
//            Equipos.setNombre("Mercedes");
//            em.merge(Equipos);
//        }
//        em.getTransaction().commit();
        
     // Eliminación de objetos.
//        Equipos equipoId1 = em.find(Equipos.class, 1);
//        if (equipoId1 != null) {
//            em.remove(equipoId1);
//        } else {
//            System.out.println("No hay ningún equipo con ID=1");
//          }

     // Cerrar la conexión con la base de datos
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:BDProgramacion;shutdown=true"); 
        } catch (SQLException ex) { 
          }
    }
}
