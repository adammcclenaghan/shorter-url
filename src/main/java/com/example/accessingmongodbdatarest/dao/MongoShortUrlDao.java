package com.example.accessingmongodbdatarest.dao;

import com.example.accessingmongodbdatarest.model.UrlDbEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "shortUrl")
public interface MongoShortUrlDao extends MongoRepository<UrlDbEntry, String>
{
    UrlDbEntry findByLongUrl(final String longUrl);

    UrlDbEntry findByShortUrl(final String shortUrl);
}
