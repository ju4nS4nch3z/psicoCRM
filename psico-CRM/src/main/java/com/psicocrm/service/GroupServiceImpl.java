package com.psicocrm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.psicocrm.dao.GroupDAO;
import com.psicocrm.model.Group;

@Service
public class GroupServiceImpl implements GroupService {
	private static final int PAGE_SIZE = 10;

	@Autowired
	private GroupDAO groupRepository;

	@Override
	public Page<Group> getGroups(int pageNumber, Long administrator_id) {

		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "name");
		return groupRepository.findByAdministrator_Id(administrator_id, pageable);
	}

	@Override
	public void save(Group group) {
		groupRepository.save(group);

	}

	@Override
	public List<Group> getGroups(Long id) {
		return groupRepository.findByAdministrator_Id(id);
	}

	@Override
	public Group getOne(long id) {
		return groupRepository.getOne(id);
	}

	@Override
	public void delete(long id) {
		groupRepository.delete(id);

	}

}
