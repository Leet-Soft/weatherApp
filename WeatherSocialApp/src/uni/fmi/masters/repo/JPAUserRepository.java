package uni.fmi.masters.repo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.TransactionException;

import uni.fmi.masters.entity.RequestEntity;
import uni.fmi.masters.entity.UserEntity;

public class JPAUserRepository extends BaseRepository<UserEntity> {

	public JPAUserRepository() {
		super(UserEntity.class);
	}

	public EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UserPU");

		return factory.createEntityManager();
	}

	public List<UserEntity> searchByUsername(String filter) {

		EntityManager em = getEntityManager();

		List<UserEntity> result = new ArrayList<UserEntity>();

		try {
			String query = "FROM UserEntity" 
					+ " WHERE username like :filter";

			TypedQuery<UserEntity> q = 
					em.createQuery(query, UserEntity.class);

			q.setParameter("filter", "%"+filter+"%");

			result = q.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}

		return result;
	}

	public boolean createUser(UserEntity user) {

		EntityManager em = getEntityManager();
		user.setPassword(hashPassword(user.getPassword()));

		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (TransactionException e) {

			System.out.println(e.getMessage());

			em.getTransaction().rollback();

			return false;
		} finally {
			em.close();
		}

		return true;
	}

	public UserEntity findUserByUsernameAndPassword(String username, String password) {

		EntityManager em = getEntityManager();

		String query = "SELECT u FROM UserEntity u " + "WHERE u.username = :user AND u.password = :pass";

		TypedQuery<UserEntity> q = em.createQuery(query, UserEntity.class);
		q.setParameter("user", username);
		q.setParameter("pass", hashPassword(password));

		List<UserEntity> result = q.getResultList();

		em.close();

		// return result.size() == 1 ? result.get(0) : null;

		if (result.size() == 1) {
			return result.get(0);
		}

		return null;

	}

	private String hashPassword(String password) {

		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());

			byte[] bytes = md.digest();

			for (int i = 0; i < bytes.length; i++) {
				sb.append((char) bytes[i]);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
