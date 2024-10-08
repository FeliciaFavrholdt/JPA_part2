package dk.cph.dao;

import dk.cph.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDaoImpl implements GenericDAO<Course, Integer> {

    private static CourseDaoImpl instance;
    private static EntityManagerFactory emf;

    public static CourseDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CourseDaoImpl();
        }
        return instance;
    }

    @Override
    public Set<Course> findAll() {
        try (EntityManager em = emf.createEntityManager()){
            TypedQuery<Course> query = em.createQuery("SELECT u FROM Course u", Course.class);
            List<Course> courseList = query.getResultList();
            return courseList.stream().collect(Collectors.toSet());
        }
    }

    @Override
    public void persistEntity(Course course) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        }
    }

    @Override
    public void removeEntity(Integer id) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(id);
            em.getTransaction().commit();
        }
    }

    @Override
    public Course findEntity(Integer id) {
        try (EntityManager em = emf.createEntityManager()){
            return em.find(Course.class, id);
        }
    }

    @Override
    public Course updateEntity(Course course) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(course);
            em.getTransaction().commit();
        }
        return course;
    }


}

