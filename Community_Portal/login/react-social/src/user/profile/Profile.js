import React, { Component } from 'react';
import './Profile.css';

class Profile extends Component {
    constructor(props) {
        super(props);
        console.log(props);
    }
    render() {
        return (
            <div className="profile-container">
                <div className="container">
                    <div className="profile-info">
                        <div className="profile-avatar">
                            { 
                                this.props.currentUser.imageUrl ? (
                                    <img src={this.props.currentUser.imageUrl} alt={this.props.currentUser.name}/>
                                ) : (
                                    <div className="text-avatar">
                                        <span>{this.props.currentUser.name && this.props.currentUser.name[0]}</span>
                                    </div>
                                )
                            }
                        </div>
                        <div class="sidenav">
                          <a href="#">My Community</a>
                          <a href="#">Current Projects</a>
                          <a href="#">Find Projects</a>
                          <a href="#">Payment</a>
                        </div>
                        <div className="profile-name">
                           <h2>{this.props.currentUser.name}</h2>
                        </div>
                        <div className="profile-type"><h3>Designation : Tester</h3></div>
                        <div className="profile-email"><h3>Email: {this.props.currentUser.email}</h3></div>
                        <div className="profile-resume"><h3>Resume Link : wwww.linkedin.com/james.bond</h3></div>
                        <div className="profile-status"><h3>Status : Available</h3></div>
                        <div className="profile-skill1"><h3>Skill 1 : Android testing</h3></div>
                        <div className="profile-skill2"><h3>Skill 2 : iOS testing</h3></div>
                        <div className="profile-skill3"><h3>Skill 3 : Java</h3></div>
                        <button onclick="myFunction()">Update Profile</button>

                        <script>
                        function myFunction() {

                        }


                        </script>
                    </div>
                </div>    
            </div>
        );
    }
}

export default Profile

