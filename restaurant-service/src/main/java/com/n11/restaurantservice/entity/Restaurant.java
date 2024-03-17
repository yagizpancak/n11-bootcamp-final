package com.n11.restaurantservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "n11_restaurants")
@Getter
@Setter
@ToString
public class Restaurant {
	@Id
	@Indexed(name = "id", type = "string")
	private String id;

	@Indexed(name = "name", type = "string")
	private String name;

	@Indexed(name = "coordinate", type = "location")
	private String coordinate;

}
