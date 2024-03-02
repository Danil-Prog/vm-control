import React from 'react';
import ReactDOM from 'react-dom/client';
import App from '~/App';
import AuthStore from '~/core/stores/Auth.store';
import { Provider } from 'mobx-react';
import ThemeStore from '~/core/stores/Theme.store';
import './styles/index.scss';
import './styles/variables.scss';
// Создаем новый экземпляр AuthStore

const authStore = new AuthStore();
const themeStore = new ThemeStore();

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <Provider authStore={authStore} themeStore={themeStore}>
    <App />
  </Provider>
);
