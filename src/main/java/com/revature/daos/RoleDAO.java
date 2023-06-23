package com.revature.daos;

import com.revature.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer>
{
	Optional<Role> findByName(String name);
	Role getByName(String name);
}
