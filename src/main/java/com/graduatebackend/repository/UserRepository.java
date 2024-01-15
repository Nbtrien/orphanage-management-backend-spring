package com.graduatebackend.repository;

import com.graduatebackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
	/**
	 * find user by mail address
	 * 
	 * @param mailAddress
	 * @return
	 */
	Optional<User> findByUserMailAddressAndIsDeleteIsFalse(String mailAddress);

	/**
	 * check email is existed
	 * 
	 * @param mailAddress
	 * @return
	 */
	Optional<User> findByUserMailAddress(String mailAddress);

	/**
	 * check username is exists
	 * 
	 * @param userName
	 * @param email
	 * @return
	 */
	@Query(value = "SELECT u.userId FROM User u WHERE u.userName = ?1")
	Optional<Integer> checkUserIsExists(String userName);

	/**
	 * soft delete user by ids
	 * 
	 * @param ids
	 */
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.isDelete = TRUE WHERE u.userId in(?1)")
	void softDeleteByIds(List<Integer> ids);

	/**
	 * get user is not deleted
	 * 
	 * @param pageable
	 * @return
	 */
	Page<User> findByIsDeleteIsFalse(Pageable pageable);

	/**
	 * find all
	 * 
	 * @param pageable
	 * @param key
	 * @param roleId
	 * @return
	 */
	@Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE (:keyword IS NULL OR u.userName LIKE %:keyword%) AND (:roleId IS NULL OR r.roleId = :roleId) AND u.isDelete IS FALSE")
	Page<User> findAll(Pageable pageable, @Param("keyword") String keyword, @Param("roleId") Integer roleId);

	/**
	 * search users
	 * 
	 * @param pageable
	 * @param key
	 * @return
	 */
	@Query(value = "SELECT u FROM User u "
			+ "WHERE ((u.userName LIKE %:key%) OR (u.userMailAddress LIKE %:key%)) AND u.isDelete IS FALSE ")
	Page<User> searchUsers(Pageable pageable, @Param("key") String key);
}
