import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from 'react-router-dom'

const Home = (props) => {
  /*
  This prop is used to determine whether we've arrived at the home page
  after a failed API call. If we did, we'll display a helpful message
  */
  const [apiFailed, ] = useState(props.apiFailed)

  //TODO: Refactor this to be more DRY
  if (apiFailed) {
    return (
      <div className="App">
        <header className="App-header">
          <p>Sorry, the shortened URL could not be mapped. Please create a shortened URL below</p>
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Enter a long URL to shorten
          </p>
            <FormComponent />
        </header>
      </div>
      )
  }

  return (
  <div className="App">
    <header className="App-header">
      <img src={logo} className="App-logo" alt="logo" />
      <p>
        Enter a long URL to shorten
      </p>
        <FormComponent />
    </header>
  </div>
  )
}

// We will attempt to redirect any calls under / by the shortUrl provided
const RedirectFromShortUrl = (props) => {
 return (
   <ShortUrlRedirect shortUrl={props.shortUrl}/>
 )
}

function ShortUrlRedirect(props) {
  const [shortUrl, ] = useState(props.shortUrl);
  const [returnHome, setReturnHome] = useState(false);

  useEffect(() => {
    async function fetchLongUrl() {
      const shortUrlToReq = shortUrl.replace(/^\/|\/$/g, '');
      const apiBase = "http://localhost:8080/";
      const apiGetLongUrl = "shortUrl/getLongFromShort";
      
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({ shortUrl: shortUrlToReq})
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
        else
        {
          let longUrl = data.longUrl
          // Text will start with " and end with " so trim this
          //data = data.substring(1, data.length - 1);
  
          // If the URL returned doesn't start with http:// we append it
          if (!longUrl.startsWith("http://"))
          {
            longUrl = "http://" + longUrl;
          }
  
          window.location.href = longUrl;
        }
      })
      .catch( err => {
        console.log("API call failed");
        /*
        This could be improved to set different state values for apiFailed vs
        a shortUrl not existing in the db. Would mean that a more helpful msg
        could be shown on the home page after redirecting
        */
        setReturnHome(true);
      })  
    }

    fetchLongUrl();
  }, [shortUrl]);

  if (returnHome)
  {
    return (
      <Home apiFailed={true}/>
    )
  }
  else{
    return (
      <p> Loading ... </p>
    )
  }
}

/*
Component used to send a call to the API
*/
class FormComponent extends React.Component {

  constructor(props) {
      super(props);
      this.state = {
        value: '',
        shortUrl: ''
      };
  
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
      this.setState({value: event.target.value});
    }
  
    handleSubmit(event) {
 
      const apiBase = "http://localhost:8080/";
      const apiCreate = apiBase + "shortUrl/create";

      const redirectBase = "http://localhost:3000/";

      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({ longUrl: this.state.value})
      };

      fetch(apiCreate, requestOptions)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw response;
      })
      .then(data => {
        this.setState({ shortUrl: "Short URL: " + redirectBase + data.shortUrl });
      })
      .catch (error => {
        console.error("Error fetching data: ", error);
      })

      event.preventDefault();
    }
  
    render() {
      return (
        <div>
          <form onSubmit={this.handleSubmit}>
            <label>
              <input type="text" value={this.state.value} onChange={this.handleChange} />
            </label>
            <input type="submit" value="Shorten"/>
          </form>
          <div className="show-short-url">
            {this.state.shortUrl}
          </div>
        </div>
      );
    }    
}

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact>
          <Home />
        </Route>
        <Route path='*' render={(props) => (
          <RedirectFromShortUrl shortUrl={props.match.url} />
         )} />
      </Switch>
    </Router>
  )
}

export default App;
