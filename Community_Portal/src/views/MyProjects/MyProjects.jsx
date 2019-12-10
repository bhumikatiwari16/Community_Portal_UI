import React, {Component} from 'react';
import {
  Link
} from '@material-ui/core';
import { Link as RouterLink } from 'react-router-dom';
import axios from 'axios';

import './MyProjects.css';

class MyProjects extends React.Component{
  state = {
    data: [],
    id: 0,
    message: null,
    intervalIsSet: false,
    idToDelete: null,
    idToUpdate: null,
    objectToUpdate: null,
  };

    componentDidMount() {
      document.title = "My Projects";
      this.getDataFromDb();
      if (!this.state.intervalIsSet) {
        let interval = setInterval(this.getDataFromDb, 1000);
        this.setState({ intervalIsSet: interval });
      }
    }

    // never let a process live forever
    // always kill a process everytime we are done using it
    componentWillUnmount() {
      if (this.state.intervalIsSet) {
        clearInterval(this.state.intervalIsSet);
        this.setState({ intervalIsSet: null });
      }
    }

  getDataFromDb = () => {
    fetch('http://ec2-54-173-147-93.compute-1.amazonaws.com:3001/api/getData')
      .then((data) => data.json())
      .then((res) => this.setState({ data: res.data }));
  };
    render() {
      const { data } = this.state;
        return (
          <div>
            <div class = "NewProject">
            <br/>
            </div>
            <div class = "card">
            <p class = "MyProjects">Active Projects</p>
            <br/>
              <ul class = "MyProjects">
                {data.length <= 0
                  ? <label class = "label"><br/>You don't have any active projects</label>
                  : data.map((dat) => (
                    <div class="card">
                    <h2><b><li style={{ padding: '20px' }} >
                    <Link
                    component={RouterLink}
                    to={{ pathname: '/ViewProject', state: { id: dat.id, message: dat.message} }}>{dat.projectname}</Link>
                    </li></b></h2>
                    </div>
                    ))}
              </ul>
            </div>
            </div>
        );
    }
}

export default MyProjects;
