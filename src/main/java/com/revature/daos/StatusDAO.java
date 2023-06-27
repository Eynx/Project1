package com.revature.daos;

import com.revature.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusDAO extends JpaRepository<Status, Integer>
{
    Optional<Status> findByName(String name);
}
