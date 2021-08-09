import React, { useEffect, useState } from 'react';
import {fetchLongUrl} from './api/ApiFunctions.js';
import { Home } from './App';

export function ShortUrlRedirect({ shortUrl }) {
  const [returnHome, setReturnHome] = useState(false);

  useEffect(() => {
    fetchLongUrl(shortUrl, setReturnHome);
  }, [shortUrl]);

  return (
    <>
      {returnHome ?
        <Home apiFailed={true} /> :
        <p>Loading ...</p>}
    </>
  );
}
