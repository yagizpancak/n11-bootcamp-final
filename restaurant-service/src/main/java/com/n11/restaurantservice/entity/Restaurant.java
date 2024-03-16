package com.n11.restaurantservice.entity;

import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "n11_restaurants")
@Getter
@Setter
public class Restaurant {
	@Id
	@Indexed(name = "id", type = "string")
	private String id;

	@Field
	private String name;

	@Field
	private String latitude;

	@Field
	private String longitude;

}
