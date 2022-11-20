package uni.fmi.masters.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.hibernate.TransactionException;

public abstract class BaseRepository<T> {
	
	private Class<T> typeParameter;
	
	public BaseRepository(Class<T> typeParameter) {
		this.typeParameter = typeParameter;
	}
	
	public EntityManager getEntityManager() {
		EntityManagerFactory factory = 
				Persistence.createEntityManagerFactory("UserPU");
		
		return factory.createEntityManager();
	}
	
	public List<T> getAll(){
		
		EntityManager em = getEntityManager();
		
		try {
			List<T> result = null;
			
			String q = "FROM " + typeParameter.getSimpleName();
			
			TypedQuery<T> query = em.createQuery(q, typeParameter);
	
			
			result = query.getResultList();
			
			return result;		

		}finally {
			em.close();
		}
		
	}
	
	public boolean insert(T entity) {
		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			
			return true;
			
		}catch(TransactionException e) {
			em.getTransaction().rollback();
			return false;
		}finally {
			em.close();
		}				
	}
	
	public boolean insert(List<T> entities) {
		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();
			
			for(T entity : entities) {
				em.persist(entity);
			}			
			
			em.getTransaction().commit();
			
			return true;
			
		}catch(TransactionException e) {
			em.getTransaction().rollback();
			return false;
		}finally {
			em.close();
		}				
	}	
	
	public T getById(int id) {
		EntityManager em = getEntityManager();
		
		T result = em.find(typeParameter, id);
		
		em.close();
		
		return result;
	}
	
	public boolean update(T entity) {
		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();
			
			em.merge(entity);
			
			em.getTransaction().commit();
			
			return true;
		}catch(TransactionException e) {
			em.getTransaction().rollback();
			return false;
		}finally {
			em.close();
		}		
	}
	
	public boolean delete(T entity) {
		
		EntityManager em = getEntityManager();
		
		try {
			em.remove(entity);
			return true;
			
		}catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return false;
		}finally {
			em.close();
		}
		
	}
	
	public List<T> getBy(String column, String value){
		EntityManager em = getEntityManager();
		
		try {
			String q = "SELECT x FROM :type x WHERE :column = ':value'";
			
			TypedQuery<T> query = em.createQuery(q, typeParameter);
			
			query.setParameter("type", typeParameter.getSimpleName());
			query.setParameter("column", column);
			query.setParameter("value", value);
			
			return query.getResultList();
		}finally {
			em.close();
		}

	}
	
	

}
