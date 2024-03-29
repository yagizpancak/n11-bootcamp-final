package com.n11.restaurantservice.dao;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.general.IDGenerator;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {

	default Restaurant create(Restaurant entity, Duration commit) {
		entity.setId(IDGenerator.generateID());
		return this.save(entity, commit);
	}

	default Restaurant create(Restaurant entity) {
		return this.create(entity, Duration.ZERO);
	}


	@Query(value = "{!geofilt pt=?0,?1 sfield=coordinate d=?2}")
	List<Restaurant> findByLocationWithinDistance(double latitude, double longitude, double distance);
}
