package com.graduatebackend.service;

import com.graduatebackend.dto.children.AddNewRelativeRequestDto;
import com.graduatebackend.dto.children.GetChildrenRelativesResponseDto;

public interface RelativeService {

	/**
	 * fetch by children
	 *
	 * @param childrenId
	 * @return
	 */
	GetChildrenRelativesResponseDto fetchByChildrenId(Integer childrenId);

	/**
	 * add new relative
	 *
	 * @param requestDto
	 * @return
	 */
	void addNew(Integer childrenId, AddNewRelativeRequestDto requestDto);

	/**
	 * delete relative from children
	 *
	 * @param childrenId
	 * @param relativeId
	 */
	void deleteRelativeFromChildren(Integer childrenId, Integer relativeId);
}
