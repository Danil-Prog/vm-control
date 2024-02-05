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
import ErrorPage from '~/pages/ErrorPage';
import Header from '~/components/simple/Header/Header';


function App() {
  const { authStore } = useContext(Context);
  React.useEffect(() => {
    if (localStorage.getItem('token')) {
      authStore.checkAuth();
    }
  }, []);
  return (
    <div className="App">
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
