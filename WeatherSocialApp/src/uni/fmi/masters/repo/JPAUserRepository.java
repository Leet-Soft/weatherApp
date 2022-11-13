package uni.fmi.masters.repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.TransactionException;

import uni.fmi.masters.entity.UserEntity;

public class JPAUserRepository {

	public EntityManager getEntityManager() {
		EntityManagerFactory factory = 
				Persistence.createEntityManagerFactory("UserPU");
		
		return factory.createEntityManager();
	}
	
	
	public boolean createUser(UserEntity user) {
		
		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		}catch (TransactionException e) {
			
			System.out.println(e.getMessage());
			
			em.getTransaction().rollback();
			
			return false;
		}finally {
			em.close();
		}		
		
		return true;
	}
}
