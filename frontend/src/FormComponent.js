import React from 'react';
import {  Form, Input, Button  } from 'antd';


const Demo = () => {
    const onFinish = (values) => {
        this.handleSubmit(values);
    }    

    return (
        <Form
                name="basic"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                initialValues={{
                    remember: true,
                }}
                onFinish={onFinish}
                >
                <Form.Item
                    label="Enter a long URL to shorten"
                    name="longurl"
                    rules={[
                    {
                        required: true,
                        message: 'Please enter a long URL to shorten!',
                    },
                    ]}
                >
                    <Input />
                </Form.Item>
                    
                <Form.Item
                    wrapperCol={{
                    offset: 8,
                    span: 16,
                    }}
                >
                    <Button type="primary" htmlType="submit">
                    Submit
                    </Button>
                </Form.Item>
                </Form>
    )
}

/*
Component used to send a call to the API
*/
export class FormComponent extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      longUrlValue: '',
      shortUrl: ''
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({ longUrlValue: event.target.value });
  }

  handleSubmit(event) {

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

    event.preventDefault();
  }

  render() {
    return (
        <Demo  handleSubmit={this.handleSubmit} />
    )

/*       <div>
        <form onSubmit={this.handleSubmit}>
          <label>
            <input type="text" value={this.state.longUrlValue} onChange={this.handleChange} />
          </label>
          <input type="submit" value="Shorten" />
        </form>
        <div className="show-short-url">
          {this.state.shortUrl && <p>Shortened URL: <a href={this.state.shortUrl}>{this.state.shortUrl}</a></p>}
        </div>
      </div>
    );
 */  }
}
