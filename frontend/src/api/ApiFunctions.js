
export async function fetchLongUrl(shortUrl, setReturnHome) {
    const shortUrlToReq = shortUrl.replace(/^\/|\/$/g, '');
    const apiBase = "http://localhost:8082/";
    const apiGetLongUrl = "shortUrl/getLongFromShort";

    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ shortUrl: shortUrlToReq })
    };

    fetch(apiBase + apiGetLongUrl, requestOptions)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw response;
      })
      .then(data => {
        // Data holds the URL that we should redirect to.
        // An empty string means that no entry exists for this shortUrl
        if (data.longUrl === "") {
          setReturnHome(true);
        }

        else {
          let longUrl = data.longUrl;
          // If the URL returned doesn't start with http:// we append it
          if (!longUrl.startsWith("http://")) {
            longUrl = "http://" + longUrl;
          }

          window.location.href = longUrl;
        }
      })
      .catch(err => {
        console.log("API call failed");
        /*
        This could be improved to set different state values for apiFailed vs
        a shortUrl not existing in the db. Would mean that a more helpful msg
        could be shown on the home page after redirecting
        */
        setReturnHome(true);
      });
  }
