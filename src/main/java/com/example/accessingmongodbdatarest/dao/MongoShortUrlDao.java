package com.example.accessingmongodbdatarest.dao;

import com.example.accessingmongodbdatarest.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "shortUrl")
public interface MongoShortUrlDao extends MongoRepository<ShortUrl, String>
{
    ShortUrl findByBaseUrl(final String baseUrl);

    ShortUrl findByShortUrl(final String shortUrl);
}
