import React from 'react';
import { Navigate } from 'react-router-dom';
import { observer } from 'mobx-react-lite';
import AuthStore from '~/core/stores/Auth.store';
import { inject } from 'mobx-react';

interface IPrivateRoute {
  children: any;
  authStore: AuthStore;
}

const PrivateRoute: React.FC<IPrivateRoute> = ({ children, authStore }) => {
  return authStore.isAuthenticated ? children : <Navigate to="/login" />;
};

export default inject('authStore')(observer(PrivateRoute));