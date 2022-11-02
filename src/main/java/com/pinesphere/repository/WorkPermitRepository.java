package com.pinesphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pinesphere.domain.WorkPermit;

@Repository
public interface WorkPermitRepository extends JpaRepository<WorkPermit, Integer> {
	
	@Query(value = "SELECT * FROM WORK_PERMIT WHERE mail_sent_ind = 'N' ORDER BY TIME ASC LIMIT 1",nativeQuery=true)
	List<Object[]> findNextWorkPermit();

}
