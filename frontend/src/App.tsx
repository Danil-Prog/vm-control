import {
  BrowserRouter,
  Route,
  Routes
} from 'react-router-dom';
import AuthPage from './pages/AuthPage';
import HomePage from '~/pages/HomePage';
import PrivateRoute from '~/routes/PrivateRoute';


function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path={'/login'} element={<AuthPage />} />
          <Route path={'/'} element={<PrivateRoute><HomePage /></PrivateRoute>} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
