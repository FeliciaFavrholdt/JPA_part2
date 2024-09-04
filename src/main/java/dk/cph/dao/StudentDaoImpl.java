package dk.cph.dao;

import dk.cph.model.Course;
import dk.cph.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentDaoImpl implements GenericDAO<Student, Integer> {

    private static StudentDaoImpl instance;
    private static EntityManagerFactory emf;

    public static StudentDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StudentDaoImpl();
        }
        return instance;
    }

    @Override
    public Set<Student> findAll() {
        try (EntityManager em = emf.createEntityManager()){
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            List<Student> studentList = query.getResultList();
            return studentList.stream().collect(Collectors.toSet());
        }
    }

    @Override
    public void persistEntity(Student student) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(student);
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
    public Student findEntity(Integer id) {
        try (EntityManager em = emf.createEntityManager()){
            return em.find(Student.class, id);
        }
    }

    @Override
    public Student updateEntity(Student student) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(student);
            em.getTransaction().commit();
        }
        return student;
    }

}
