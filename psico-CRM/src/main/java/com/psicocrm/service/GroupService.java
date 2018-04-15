package com.psicocrm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.psicocrm.model.Group;

public interface GroupService{

	Page<Group> getGroups(int pageNum, Long administrator_id);

	void save(Group group);

	List<Group> getGroups(Long administrator_id);

	Group getOne(long id);

	void delete(long id);
	
}
