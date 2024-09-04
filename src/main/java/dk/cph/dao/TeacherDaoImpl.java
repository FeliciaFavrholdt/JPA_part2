package dk.cph.dao;

import dk.cph.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherDaoImpl implements GenericDAO<Teacher, Integer> {

    private static TeacherDaoImpl instance;
    private static EntityManagerFactory emf;

    public static TeacherDaoImpl getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TeacherDaoImpl();
        }
        return instance;
    }

    @Override
    public Set<Teacher> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t", Teacher.class);
            List<Teacher> teacherList = query.getResultList();
            return teacherList.stream().collect(Collectors.toSet());
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void persistEntity(Teacher teacher) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
        }
    }

    @Override
    public void removeEntity(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Teacher teacher = em.find(Teacher.class, id);
            if (teacher == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            em.remove(teacher);
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
        }
    }

    @Override
    public Teacher findEntity(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Teacher.class, id);
        }
    }

    @Override
    public Teacher updateEntity(Teacher teacher) {
        try (EntityManager em = emf.createEntityManager()) {
            Teacher found = em.find(Teacher.class, teacher.getId());
            if (found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            Teacher merged = em.merge(teacher);
            em.getTransaction().commit();
            return merged;
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
        }
        return null;
    }
}
