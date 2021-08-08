# TODO: For some reason at the moment shortUrls and POST to shortUrls works...
curl http://localhost:8080/shortUrls

curl -i -X POST -H "Content-Type:application/json" -d "{\"longUrl\": \"hello.com\"}" http://localhost:8080/shortUrls

curl http://localhost:8080/shortUrls
