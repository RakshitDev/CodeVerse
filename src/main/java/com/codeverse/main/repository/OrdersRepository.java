package com.codeverse.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codeverse.main.entities.Orders;

@Repository
public interface OrdersRepository  extends JpaRepository<Orders, Long>{
	
    String select_query=" Select o.date_of_purchase, c.description, c.image_url,c.name,c.updated_on from orders  o join course c on o.course_name=c.name where o.user_email=:email";
	
	@Query(value = select_query,nativeQuery = true)
	List<Object[]> findPurchasedCourseByEmail(@Param("email") String email);

}
