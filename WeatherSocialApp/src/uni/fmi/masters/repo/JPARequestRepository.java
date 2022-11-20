package uni.fmi.masters.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import uni.fmi.masters.entity.RequestEntity;

public class JPARequestRepository extends BaseRepository<RequestEntity>{

	public JPARequestRepository() {
		super(RequestEntity.class);
	}
	
	

}
