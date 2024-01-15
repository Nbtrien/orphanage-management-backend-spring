package com.graduatebackend.service;

import java.util.List;

import com.graduatebackend.dto.children.OrphanTypeDto;

public interface OrphanTypeService {

	/**
	 * get orphan types list
	 *
	 * @return
	 */
	List<OrphanTypeDto> fetchAll();
}
