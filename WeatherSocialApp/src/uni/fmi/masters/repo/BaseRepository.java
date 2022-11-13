package uni.fmi.masters.repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.TransactionException;

public class BaseRepository<T> {
	
	private Class<T> typeParameter;
	
	public BaseRepository(Class<T> typeParameter) {
		this.typeParameter = typeParameter;
	}
	
	public EntityManager getEntityManager() {
		EntityManagerFactory factory = 
				Persistence.createEntityManagerFactory("UserPU");
		
		return factory.createEntityManager();
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
	

}
