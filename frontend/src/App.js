import logo from './logo.svg';
import './App.css';
import React from 'react';


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

      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({ baseUrl: this.state.value})
      };

      fetch(apiCreate, requestOptions)
      .then(response => {
        if (response.ok) {
          console.log("Response OK");
          return response.json();
        }
        throw response;
      })
      .then(data => {
        console.log(data.shortUrl);
        this.setState({ shortUrl: "Short URL: " + apiBase + data.shortUrl });
      })
      .catch (error => {
        console.error("Error fetching data: ", error);
      })
      //.finally( () => {
        //setLoading(false);//})

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
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Enter a long URL to shorten
        </p>
          <FormComponent />
      </header>
    </div>
  );
}

export default App;
