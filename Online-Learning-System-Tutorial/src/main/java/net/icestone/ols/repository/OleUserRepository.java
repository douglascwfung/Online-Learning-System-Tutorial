package net.icestone.ols.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.icestone.ols.model.OleUser;

@Repository
public interface OleUserRepository extends CrudRepository<OleUser, Long>{
	
	OleUser findByUsername(String username);
		
}
