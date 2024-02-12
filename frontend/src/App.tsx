import {
  BrowserRouter, Navigate,
  Route,
  Routes
} from 'react-router-dom';
import AuthPage from './pages/AuthPage';
import HomePage from '~/pages/HomePage';
import PrivateRoute from '~/routes/PrivateRoute';
import React, { useContext } from 'react';
import { Context } from '~/index';
import { observer } from 'mobx-react-lite';
import Header from '~/components/simple/Header/Header';
import { Toaster } from 'react-hot-toast';
import { Colors } from '~/core/constants/Colors';


function App() {
  const { authStore } = useContext(Context);
  React.useEffect(() => {
    if (localStorage.getItem('token')) {
      authStore.checkAuth();
      authStore.setAuth(true);
    } else {
      authStore.setAuth(false);
    }
  }, [authStore.isAuthenticated]);
  return (
    <div className="App">
      <Toaster
        toastOptions={{
          success: {
            iconTheme: {
              primary: Colors.success,
              secondary: Colors.text_light
            },
            style: {
              color: Colors.text_light,
              background: Colors.background
            },
            position: 'bottom-right'
          },
          error: {
            style: {
              color: Colors.text_light,
              background: Colors.background
            },
            position: 'bottom-right'
          }
        }}
      />
      <BrowserRouter>
        <Routes>
          <Route path={'/'} element={<Header />}>
            <Route path={'/'} element={<PrivateRoute><HomePage /></PrivateRoute>} />
          </Route>
          <Route path={'/login'} element={<AuthPage />} />
          <Route path={'*'} element={<Navigate to={'/'} />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default observer(App);
