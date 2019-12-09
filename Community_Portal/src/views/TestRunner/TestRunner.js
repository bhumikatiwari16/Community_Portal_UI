import React, { useState } from 'react';
import { TabContent, TabPane, Nav, NavItem, NavLink, Card,  CardTitle, CardText, Row, Col,Button } from 'reactstrap';
import { Grid, Typography, Avatar } from '@material-ui/core';
import {
  Link
} from '@material-ui/core';
import { Link as RouterLink } from 'react-router-dom';
import { MDBContainer, MDBRow, MDBCol } from "mdbreact";
import './testRunner.css';

const TestRunner = (props) => {
  const [activeTab, setActiveTab] = useState('1');

  const toggle = tab => {
    if (activeTab !== tab) setActiveTab(tab);
  }
  return (
    <div valign="top" align="center"
    >
<br/><br/>
      <MDBContainer fluid>
        <MDBRow>
          <MDBCol>
            <Card >
               <Typography
          align="center"
          gutterBottom
          variant="h4" >  Automated Testing </Typography>
              <CardText>Automated testing will allow you to execute built-in
      tests or your own scripts on selected device, generating a detailed report .
			  </CardText>

           <Link
                component={RouterLink}
                to="/Automated"
                variant="h6"
              >  <Button>
                Start Execution
                </Button>  </Link>
            </Card>
          </MDBCol>
          <MDBCol >
            <Card >
            <Typography
          align="center"
          gutterBottom
          variant="h4"> Interactive Testing</Typography>
              <CardText>Live or interactive testing allows you to interact with a device through your web browser in real time in order to perform manual testing.
				</CardText>
            <Link variant="h6" ><Button>  <a href="https://cloud.geny.io/app/default-devices" target="_blank" style={{color:'#fff'}}>Start Session</a></Button></Link>
            </Card>
          </MDBCol>
        </MDBRow>
      </MDBContainer >

    </div>
  );
}

export default TestRunner;
