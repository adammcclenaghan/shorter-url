package com.example.accessingmongodbdatarest.dao;

import com.example.accessingmongodbdatarest.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "shortUrl", path = "shortUrl")
public interface MongoShortUrlDao extends MongoRepository<ShortUrl, String>
{
    List<String> findByBaseUrl(@Param("baseUrl") String baseUrl);
}
