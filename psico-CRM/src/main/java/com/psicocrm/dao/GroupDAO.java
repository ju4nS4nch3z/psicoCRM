package com.psicocrm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.psicocrm.model.Group;

public interface GroupDAO extends JpaRepository<Group, Long> {

	Page<Group> findByAdministrator_Id(Long administrator_id, Pageable pageable);

	List<Group> findByAdministrator_Id(Long id);

}
