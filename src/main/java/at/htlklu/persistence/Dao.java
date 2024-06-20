package at.htlklu.persistence;

import at.htlklu.entities.Parts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Dao {
    public static boolean updatePartCount(String serialnr, int delta) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            TypedQuery<Parts> query = em.createQuery("SELECT p FROM Parts p WHERE p.serialnr = :serialnr", Parts.class);
            query.setParameter("serialnr", serialnr);
            List<Parts> partsList = query.getResultList();

            if (partsList.isEmpty()) {
                return false;
            }

            Parts part = partsList.get(0);
            int newCount = part.getCount() + delta;
            newCount = Math.max(newCount, 0); // Prevent negative stock
            part.setCount(newCount);

            em.merge(part);
            et.commit();
            return true;
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public static List<Parts> searchParts(String keyword) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Parts> query = em.createQuery("SELECT p FROM Parts p WHERE p.serialnr LIKE :keyword OR p.partname LIKE :keyword ORDER BY p.partname", Parts.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static Parts searchPart(String keyword) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Parts> query = em.createQuery("SELECT p FROM Parts p WHERE p.serialnr LIKE :keyword OR p.partname LIKE :keyword ORDER BY p.partname", Parts.class);
            query.setParameter("keyword", "%" + keyword + "%");
            List<Parts> resultList = query.getResultList();

            // Falls kein Ergebnis gefunden wurde, return null oder eine Standard-Rückgabe
            if (resultList.isEmpty()) {
                return null; // oder return new Parts(); oder throw Exception, je nach Anwendungslogik
            }

            return resultList.get(0); // Gib das erste (und hoffentlich einzige) Ergebnis zurück
        } finally {
            em.close();
        }
    }

}
