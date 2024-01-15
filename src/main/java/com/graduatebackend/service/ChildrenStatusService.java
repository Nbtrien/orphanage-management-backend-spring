package com.graduatebackend.service;

import java.util.List;

import com.graduatebackend.dto.children.ChildrenStatusDto;
import com.graduatebackend.dto.children.GetStatusHistoryResponseDto;

public interface ChildrenStatusService {

	/**
	 * get children status list
	 *
	 * @return
	 */
	List<ChildrenStatusDto> fetchAll();

	/**
	 * get transaction options
	 *
	 * @param id
	 * @return
	 */
	List<ChildrenStatusDto> fetchStatusOptions(Integer id);

	/**
	 * get status history
	 *
	 * @param id
	 * @return
	 */
	List<GetStatusHistoryResponseDto> getChildrenStatusHistory(Integer id);
}
