package com.revature.daos;

import com.revature.Models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {
    Reimbursement findByName(String name);

    List<Reimbursement> findByNameContainingIgnoreCase(String pattern);

    @Query("FROM Reimbursement WHERE name LIKE %:pattern% ")
    List<Reimbursement> findByNamesearch(@Param("pattern") String pattern);
}
