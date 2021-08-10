import logo from './logo.svg';
import './App.css';
import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from 'react-router-dom'
import { ShortUrlRedirect } from './ShortUrlRedirect';
import { FormComponent } from './FormComponent';

export const Home = ({apiFailed}) => {
    return (
      <div className="App">
        <header className="App-header">
          {apiFailed && <p>Sorry, the shortened URL could not be mapped. Please create a shortened URL below</p>}
          <h1>Shorter URL</h1>
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
