import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { observer } from 'mobx-react-lite';

import { Context } from '~/index';

function PrivateRoute({ children }) {
  const { authStore } = useContext(Context);
  return authStore.isAuthenticated ? children : <Navigate to="/login" />;
}

export default observer(PrivateRoute);