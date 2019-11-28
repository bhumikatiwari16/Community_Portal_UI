import React from 'react';
import { makeStyles } from '@material-ui/styles';

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(4)
  },
  iframe: {
    width: '100%',
    minHeight: 640,
    border: 0
  }
}));

const Community = () => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <iframe
        className={classes.iframe}
        src="http://localhost:3000/"
        title="Community Page"
      />
    </div>
  );
};

export default Community;
