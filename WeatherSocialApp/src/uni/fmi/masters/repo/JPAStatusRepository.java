package uni.fmi.masters.repo;

import uni.fmi.masters.entity.StatusEntity;

public class JPAStatusRepository extends BaseRepository<StatusEntity>{

	public JPAStatusRepository() {
		super(StatusEntity.class);
	}

}
