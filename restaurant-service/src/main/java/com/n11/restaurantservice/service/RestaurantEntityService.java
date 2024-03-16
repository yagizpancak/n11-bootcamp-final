package com.n11.restaurantservice.service;

import com.n11.restaurantservice.dao.RestaurantRepository;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.errormessage.ErrorMessage;
import com.n11.restaurantservice.exception.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantEntityService {
	private final RestaurantRepository repository;

	protected RestaurantEntityService(RestaurantRepository repository) {
		this.repository = repository;
	}

	public List<Restaurant> findAll(){
		List<Restaurant> restaurants = new ArrayList<>();
		repository.findAll().forEach(restaurants::add);
		return restaurants;
	}

	public Restaurant findByIdWithControl(String id){
		Optional<Restaurant> optional = repository.findById(id);
		Restaurant entity;
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
		}
		return entity;
	}

	public Restaurant update(Restaurant entity){
		this.findByIdWithControl(entity.getId());
		return repository.save(entity);
	}

	public Restaurant create(Restaurant entity){
		return repository.create(entity);
	}

	public void delete(String id){
		repository.deleteById(id);
	}
}
