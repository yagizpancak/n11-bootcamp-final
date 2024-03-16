package com.n11.restaurantservice.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(
		basePackages = "com.n11.restaurantservice"
)
@ComponentScan
public class SolrConfig {
	@Value("${solr.url}")
	private String solrURL;

	@Bean
	public SolrClient solrClient(){
		return new HttpSolrClient.Builder(solrURL).build();
	}

	@Bean
	public SolrTemplate solrTemplate(SolrClient solrClient){
		return new SolrTemplate(solrClient);
	}
}
