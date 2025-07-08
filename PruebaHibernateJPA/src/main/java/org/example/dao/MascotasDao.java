package org.example.dao;


import java.util.List;

import org.example.aplicacion.JPAUtil;
import org.example.entidades.Mascota;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class MascotasDao {
    EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    public String registrarMascota(Mascota miMascota) {
        entityManager.getTransaction().begin();
        entityManager.persist(miMascota);
        entityManager.getTransaction().commit();
        return "Mascota Registrada!";
    }

    public Mascota consultarMascota(Long idMascota) {
        return entityManager.find(Mascota.class, idMascota);
    }

    public List<Mascota> consultarListaMascotas() {
        Query query = entityManager.createQuery("SELECT m FROM Mascota m");
        return query.getResultList();
    }

    public List<Mascota> consultarListaMascotasPorSexo(String sexo) {
        Query query = entityManager.createQuery("SELECT m FROM Mascota m WHERE m.sexo = :sexoMascota");
        query.setParameter("sexoMascota", sexo);
        return query.getResultList();
    }

    public String actualizarMascota(Mascota miMascota) {
        entityManager.getTransaction().begin();
        entityManager.merge(miMascota);
        entityManager.getTransaction().commit();
        return "Mascota Actualizada!";
    }

    public String eliminarMascota(Mascota miMascota) {
        entityManager.getTransaction().begin();
        entityManager.remove(miMascota);
        entityManager.getTransaction().commit();
        return "Mascota Eliminada!";
    }

    public void close() {
        entityManager.close();
        JPAUtil.shutdown();
    }
}
