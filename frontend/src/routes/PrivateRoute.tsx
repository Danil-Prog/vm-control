import React from 'react';
import { Navigate } from 'react-router-dom';
import { observer } from 'mobx-react-lite';

import AuthStore from '~/core/stores/Auth.store';

function PrivateRoute({ children }) {
  return AuthStore.isAuthenticated ? children : <Navigate to="/login" />;
}

export default observer(PrivateRoute);