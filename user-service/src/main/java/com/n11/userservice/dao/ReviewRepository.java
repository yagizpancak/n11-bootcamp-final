package com.n11.userservice.dao;

import com.n11.userservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("SELECT AVG(r.score) FROM Review r WHERE r.restaurantId = ?1")
	Double findAveragePointsByRestaurantId(String restaurantId);
}
