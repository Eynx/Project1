package com.revature.daos;

import com.revature.Models.Reimbursment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursmentDAO extends JpaRepository<Reimbursment, Integer> {
    Reimbursment findByName(String name);

    List<Reimbursment> findByNameContainingIgnoreCase(String pattern);

    @Query("FROM Reimbursment WHERE name LIKE %:pattern% ")
    List<Reimbursment> findByNamesearch(@Param("pattern") String pattern);
}
