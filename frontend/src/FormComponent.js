import React from 'react';

/*
Component used to send a call to the API
*/
export class FormComponent extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      longUrlValue: '',
      shortUrl: '',
      submittedEmpty: false
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({ longUrlValue: event.target.value });
  }

  handleSubmit(event) {

    if (this.state.longUrlValue === "") {
      this.setState({ submittedEmpty: true });
    }
    else {
      this.setState( {submittedEmpty: false });
      const apiBase = "http://localhost:8082/";
      const apiCreate = apiBase + "shortUrl/create";

      const redirectBase = "http://localhost:3000/";

      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ longUrl: this.state.longUrlValue })
      };

      fetch(apiCreate, requestOptions)
        .then(response => {
          if (response.ok) {
            return response.json();
          }
          throw response;
        })
        .then(data => {
          this.setState({ shortUrl: redirectBase + data.shortUrl });
        })
        .catch(error => {
          console.error("Error fetching data: ", error);
        });
    }

    event.preventDefault();
  }

  render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <label>
            <input type="text" value={this.state.longUrlValue} onChange={this.handleChange} />
          </label>
          <input type="submit" value="Shorten" />
        </form>
        <div className="show-short-url">
          {this.state.shortUrl && <p>Shortened URL: <a href={this.state.shortUrl}>{this.state.shortUrl}</a></p>}
          {this.state.submittedEmpty && <p>The long URL to shorten must not be empty.</p>}
        </div>
      </div>
    );
  }
}
