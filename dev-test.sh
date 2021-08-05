curl http://localhost:8080/shortUrl

curl -i -X POST -H "Content-Type:application/json" -d "{\"shortUrl\": \"hello\"}" http://localhost:8080/shortUrl

curl http://localhost:8080/shortUrl
