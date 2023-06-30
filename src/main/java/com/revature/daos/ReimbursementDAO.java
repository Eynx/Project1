package com.revature.daos;

import com.revature.models.Reimbursement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer>
{
	@Query(value = "SELECT * FROM reimbursements WHERE reimbursement_user_id = :id ORDER BY reimbursement_id DESC", nativeQuery = true)
	List<Reimbursement> findByPerson(@Param("id") int id);
}
