import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { makeStyles } from '@material-ui/styles';
import { Grid } from '@material-ui/core';
import {
  Budget,
  TotalUsers,
  TasksProgress,
  TotalProfit,
  LatestSales,
  UsersByDevice,
  LatestProducts,
  LatestOrders
} from './components';

import './ViewProject.css';

/*const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(4)
  }
}));*/

class ViewProject extends React.Component{
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
      document.title = "ViewProject";
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
  //const classes = useStyles();

        return (
            <div class = "ViewProject">
            <br/>
            <div className>
              <Grid
                container
                spacing={4}
              >
                <Grid
                  item
                  lg={3}
                  sm={6}
                  xl={3}
                  xs={12}
                >
                  <Budget />
                </Grid>
                <Grid
                  item
                  lg={3}
                  sm={6}
                  xl={3}
                  xs={12}
                >
                  <TotalUsers />
                </Grid>
                <Grid
                  item
                  lg={3}
                  sm={6}
                  xl={3}
                  xs={12}
                >
                  <TasksProgress />
                </Grid>
                <Grid
                  item
                  lg={3}
                  sm={6}
                  xl={3}
                  xs={12}
                >
                  <TotalProfit />
                </Grid>
                <Grid
                  item
                  lg={8}
                  md={12}
                  xl={9}
                  xs={12}
                >
                { data.slice(this.props.history.location.state.id, this.props.history.location.state.id+1).map((dat) => (
                  <div><br/><p><label class = "ViewProjects">Project name : </label>
                  {dat.projectname}</p>
                  <br/> <p><label class = "ViewProjects">Summary : </label>
                  {dat.description} </p>
                  <br/><p><label class = "ViewProjects">Manager Email : </label>
                  {dat.manager_email}</p>
                  <br/><p><label class = "ViewProjects">Release date : </label>
                  {dat.release_date}</p>
                  <br/><p><label class = "ViewProjects">Progress : </label>
                  {dat.progress}</p>
                </div>
              ))}
                </Grid>
                <Grid
                  item
                  lg={4}
                  md={6}
                  xl={3}
                  xs={12}
                >
                  <UsersByDevice />
                </Grid>

                <Grid
                  item
                  lg={8}
                  md={12}
                  xl={9}
                  xs={12}
                >
                  <LatestOrders />
                </Grid>
              </Grid>
            </div>
            </div>
        );
    }
}

export default ViewProject;
