import React, { createContext } from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { router } from '~/routes/Routes';
import App from '~/App';
import AuthStore from '~/core/stores/Auth.store';

interface State {
  authStore: AuthStore;
}

const authStore = new AuthStore();

export const Context = createContext<State>({
  authStore
});

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <Context.Provider value={{ authStore }}>
    <App />
  </Context.Provider>
);
