import React, { Component, useState } from 'react';
import { Container, Form, Dropdown, FormGroup, Label, Row, Col, Card, Button } from 'reactstrap';
import { FilePond } from 'react-filepond';
import {
  Link
} from '@material-ui/core';

import { Link as RouterLink, withRouter } from 'react-router-dom';
const divStyle = {
  width: '100%',
  borderRadius: '8px',
  float: 'center',
  backgroundColor: 'White',
  paddingBottom: "20px",
  borderBottom: '5px solid Grey',
  borderTop: '5px solid Grey',
  borderLeft: '5px solid Grey',
  borderRight: '5px solid Grey',
  verticalAlign: "center"
}
const tableStyle ={
  width :"900px",
  borderRadius: '8px',
  float: 'center',
  borderWidth: "1px",
  backgroundColor: 'White',
  paddingTop:"10px",
  paddingBottom: "10px",
  verticalAlign: "center"
}

const cellStyle ={
  borderRadius: '8px',
  float: 'center',
  borderWidth: "1px",
  paddingRight : "5px",
  backgroundColor: 'White',
  borderBottom: '1px solid #ccc',
  borderLeft: '1px solid #ccc',
  borderRight: '1px solid #ccc',
  borderTop: '1px solid #ccc',
  verticalAlign: "center"
}
const head = {
  fontSize: '20px',
  float: 'center',

}
const headDiv = {
  fontSize: '25px',
  float: 'center',
  backgroundColor: '#ccc'
}
class Automated extends Component {
  constructor(props) {
    super(props);
    this.state = {
      body:"",
      appName: "",
      appFile:'',
      testFile:'',
      createDt: "",

      passed: "",
      fail: "",
      error:"",
      msg:"",
      testTypes:[],
      createDt : "",
      testCaseTotal:"",
      passed:"",
      failed:"",
      testType: "",
      status:"",
      isLoading:"",
      isResultsVisible:'hidden'
    };
  }
  componentDidMount() {
    this.setState({
		testTypes: [
			{id: 'APPIUM_JAVA_TESTNG', name: 'APPIUM_JAVA_TESTNG'},
			{id: 'APPIUM_JAVA_JUNIT', name: 'APPIUM_JAVA_JUNIT'},
			{id: 'APPIUM_PYTHON', name: 'APPIUM_PYTHON'},
      	{id: 'APPIUM_RUBY', name: 'APPIUM_RUBY'},
        	{id: 'INSTRUMENTATION', name: 'INSTRUMENTATION'},
          	{id: 'UIAUTOMATION:', name: 'UIAUTOMATION:'}

		]
	});
  }
  onFileChange = (event) => {
    this.setState({
      appFile: event.target.files[0]
    });
  }
  onTestPackageChange = (event) => {
    this.setState({
      testFile: event.target.files[0]
    });
  }
  goToResults= () => {
   //just put your request directly in the method
   fetch('http://localhost:5000/RunResults?appName='+this.state.appName
  )
     .then(response => {
       //do something with response
       const body = response.json();

 });
}




  uploadFile = (event) => {
    event.preventDefault();
    this.setState({error: '', msg: ''});

    if(!this.state.file) {
      this.setState({error: 'Please upload a file.'})
      return;
    }



    let data = new FormData();
    data.append('file', this.state.file);
    data.append('name', this.state.file.name);

    fetch('http://localhost:8080/RunResults/appName='+this.state.appName+
    '&appFile='+this.state.appFile+
    '&testType=APPIUM_JAVA_TESTNG&testPackageFile='
    +this.state.testFile, {
      method: 'GET',
      body: data
    }).then(response => {
      this.setState({error: '', msg: 'Sucessfully uploaded file'});
    }).catch(err => {
      this.setState({error: err});
    });

}
  updateName = (event) => {
    event.preventDefault();
    this.setState({appName: event.target.value});
  }
  updateTestType = (event) => {
    event.preventDefault();
    this.setState({testType: event.target.value});
  }

  sayHello = async (event) => {
      event.preventDefault();
      let response = await fetch('/RunResults1?appName=' + this.state.appName);
      let body = await response.json();
      this.setState({ appName: body.appName, testCaseTotal:body.totalTests });
    }



  render() {
    const { testTypes,body } = this.state;

	let testTypesList = testTypes.length > 0
		&& testTypes.map((item, i) => {
		return (
			<option key={i} value={item.id}>{item.name}</option>
		)
	}, this);
    return (
<div>
      <div align = "center" style={divStyle}>
      <center>
      <br/>
        <div style={headDiv} >
          <Label ><b>Create a new session </b></Label>
          <hr>
          </hr>
        </div>
        <table>

          <tr>
            <th style={head} align="middle"> Configure your device </th>
          </tr>
          <br/>
          <tr>
          <td>
            <Label  >Project name</Label>
          </td>
          <td>
           <input onChange={(event)=>this.updateName(event)} placeholder="Text"></input>
          </td>
          </tr>
          <br/>
          <tr>
            <td>
              <Label  >Upload application file(.apk)</Label>
            </td>
           <td width="400px">
              <input onChange={this.onFileChange} type="file"></input>
            </td>
          </tr>
          <tr class="blank_row">
    <td colspan="3"></td>
</tr>
<tr>
</tr><br/>
          <tr>
            <td>
              <Label> Select device pool</Label>
            </td>
            <td>
              <select multiple>
                  <option value="S9+">Default device pool</option>
                <option value="S9+">Google Pixel</option>
                <option value="S6">Google Pixel 2</option>
                <option value="pixel2">Samsung Galaxy  S6</option>
                <option value="s6">Samsung Galaxy S6 (T-Mobile)</option>
                <option value="pixel">Samsung Galaxy S9+</option>
                <option value="op6">One Plus 6</option>
              </select>
            </td>
          </tr>
          <hr></hr>
          <tr>
            <th style={head} align="middle"> Configure your test </th>
          </tr>
          <br />
          <tr>
            <td >
              Test Type
</td>
            <td>

              <select id="testTypeList" name="testTypeList">
            {testTypesList}
              </select>
            </td>
          </tr>
          <br />
          <tr>
            <td>
              Upload test script package
</td>
            <td>
            <input onChange={this.onTestPackageChange} type="file"></input>
            </td>
          </tr>
        </table>
<br/>
		      <center><b><Label>You'll be redirected to results page on click of submit </Label>
        <Label>which might be blank till all the tests are executed, don't panic!</Label>
        <br/>
        <Label>Results will be visible as soon as execution is finished.</Label></b>
</center>
        <br/><br/>

        <Link
                  component={RouterLink}
                  to="/testing"
                  variant="h8"
                >  <Button style={{height:'50px',width:'80px'}}> Back </Button></Link>

       &nbsp;&nbsp;
<Link component={RouterLink}
to="/RunResults"
variant="h8">
      <Button style={{height:'50px',width:'80px'}} onClick={this.goToResults}>
          Submit </Button></Link>

 <br/>


</center>
      </div>

      </div>
    );
  }

}

export default Automated;
