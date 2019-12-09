import React, { Component } from 'react';
import ReactDelayRender from 'react-delay-render';
import renderHTML from 'react-render-html'
import { Grid, Typography, Avatar } from '@material-ui/core';


  const divStyle ={
  width: '1000px',
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
const SmallRow = () => (

  <div style={divStyle}>
  <br/>
  <br/>
  <br/>
  <Typography
align="center"
gutterBottom
variant="h4" > Results </Typography>
  <table style={tableStyle}>
  <tr>
  <th style={cellStyle}> Status </th>
  <th style={cellStyle}> Application </th>
  <th style={cellStyle}> Test type </th>
  <th style={cellStyle}> Total test cases executed </th>
  <th style={cellStyle}> Passed </th>
  <th style={cellStyle}> Failed </th>
  <th style={cellStyle}> Created on </th>
  </tr>
  <tr>
  <td style={cellStyle}> COMPLETED</td>

  <td style={cellStyle}>
  HelloWorld_v1.0_apkpure.com.apk
  </td>
  <td style={cellStyle}>
  APPIUM_JAVA_TESTNG
  </td>
  <td style={cellStyle}>
  15
  </td>
  <td style={cellStyle}>
  15
  </td>
  <td style={cellStyle}>
  0
  </td>
  <td style={cellStyle}>
  12/10/2019
  </td>
  </tr>
  </table>

      </div>
);
const renderMSG = () => {

return renderHTML('<div style={divStyle}>This should work cross-browser</div>');
};

class RunResults extends Component {

constructor(props){
    super(props);
  this.state = {
    body:[],
    AppName : "",
    platform : "Android",
    createDt : "",
    testCaseTotal:"",
    passed:"",
    failed:"",
    testType: "",
    status:""
  };
}



render(){
    const { body } = this.state;
  return (
    <h1>Loading Results </h1>

    );
}

}

export default ReactDelayRender({ delay: 70000,onRender: renderMSG })(SmallRow);
