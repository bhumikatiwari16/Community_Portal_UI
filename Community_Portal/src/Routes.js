import React from 'react';
import { Switch, Redirect } from 'react-router-dom';

import { RouteWithLayout } from './components';
import { Main as MainLayout, Minimal as MinimalLayout } from './layouts';

import {
  Community as CommunityView,
  Account as AccountView,
  Settings as SettingsView,
  SignUp as SignUpView,
  SignIn as SignInView,
  NotFound as NotFoundView,
  TestRunner as TestingView,
  Automated as AutomatedView,
  RunResults as RunResultsView,
  MyProjects as MyProjectsView,
  NewProject as NewProjectView,
  ViewProject as ViewProjectView
} from './views';

const Routes = () => {
  return (
    <Switch>
      <Redirect
        exact
        from="/"
        to="/community"
      />
        <RouteWithLayout
        component={NewProjectView}
        exact
        layout={MainLayout}
        path="/newproject"
      />
      <RouteWithLayout
        component={MyProjectsView}
        exact
        layout={MainLayout}
        path="/myprojects"
      />
       <RouteWithLayout
        component={ViewProjectView}
        exact
        layout={MainLayout}
        path="/viewproject"
      />

      <RouteWithLayout
        component={CommunityView}
        exact
        layout={MainLayout}
        path="/community"
      />
      <RouteWithLayout
        component={AccountView}
        exact
        layout={MainLayout}
        path="/account"
      />
      <RouteWithLayout
        component={SettingsView}
        exact
        layout={MainLayout}
        path="/settings"
      />
      <RouteWithLayout
        component={SignUpView}
        exact
        layout={MinimalLayout}
        path="/sign-up"
      />
      <RouteWithLayout
        component={SignInView}
        exact
        layout={MinimalLayout}
        path="/sign-in"
      />
       <RouteWithLayout
        component={AutomatedView}
        exact
        layout={MainLayout}
        path="/Automated"
      />

   <RouteWithLayout
        component={RunResultsView}
        exact
        layout={MainLayout}
        path="/RunResults"
      />
      <RouteWithLayout
        component={TestingView}
        exact
        layout={MainLayout}
        path="/testing"
      />
      <RouteWithLayout
        component={NotFoundView}
        exact
        layout={MinimalLayout}
        path="/not-found"
      />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default Routes;
