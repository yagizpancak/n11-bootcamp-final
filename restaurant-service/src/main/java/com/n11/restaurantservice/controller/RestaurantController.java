package com.n11.restaurantservice.controller;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.dto.RestaurantDistanceDTO;
import com.n11.restaurantservice.general.RestResponse;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
	private final RestaurantControllerContract restaurantControllerContract;

	public RestaurantController(RestaurantControllerContract restaurantControllerContract) {
		this.restaurantControllerContract = restaurantControllerContract;
	}

	@GetMapping
	public ResponseEntity<RestResponse<List<RestaurantDTO>>> findAll(){
		List<RestaurantDTO> restaurants = restaurantControllerContract.getAllRestaurants();
		return ResponseEntity.ok(RestResponse.of(restaurants));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<RestaurantDTO>> findById(@PathVariable String id){
		RestaurantDTO restaurant = restaurantControllerContract.getRestaurantById(id);
		return ResponseEntity.ok(RestResponse.of(restaurant));
	}

	@GetMapping("/distance-lt")
	public ResponseEntity<RestResponse<List<RestaurantDistanceDTO>>> getRestaurantByDistanceLt(@RequestParam BigDecimal latitude,
																					  @RequestParam BigDecimal longitude){
		List<RestaurantDistanceDTO> restaurants = restaurantControllerContract.findByDistanceLessThan(latitude, longitude);
		return ResponseEntity.ok(RestResponse.of(restaurants));
	}

	@PutMapping
	public ResponseEntity<RestResponse<RestaurantDTO>> update(@RequestBody RestaurantUpdateRequest restaurantUpdateRequest){
		RestaurantDTO restaurant = restaurantControllerContract.updateRestaurant(restaurantUpdateRequest);
		return ResponseEntity.ok(RestResponse.of(restaurant));
	}

	@PostMapping
	public ResponseEntity<RestResponse<RestaurantDTO>> save(@RequestBody RestaurantSaveRequest restaurantSaveRequest){
		RestaurantDTO restaurant = restaurantControllerContract.saveRestaurant(restaurantSaveRequest);
		return ResponseEntity.ok(RestResponse.of(restaurant));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Object>> delete(@PathVariable String id){
		restaurantControllerContract.deleteRestaurant(id);
		return ResponseEntity.ok(RestResponse.empty());
	}
}
