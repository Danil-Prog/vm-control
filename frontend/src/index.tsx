import React from 'react';
import ReactDOM from 'react-dom/client';
import App from '~/App';
import AuthStore from '~/core/stores/Auth.store';
import { Provider } from 'mobx-react';
import ThemeStore from '~/core/stores/ThemeStore';
import './styles/index.scss';
import './styles/variables.scss';
// Создаем новый экземпляр AuthStore


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
const authStore = new AuthStore();
const themeStore = new ThemeStore();
root.render(
  <Provider authStore={authStore} themeStore={themeStore}>
    <App />
  </Provider>
);
