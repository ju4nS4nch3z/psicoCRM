package com.psicocrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psicocrm.model.Option;

public interface OptionDAO extends JpaRepository<Option, Long>  {

}
