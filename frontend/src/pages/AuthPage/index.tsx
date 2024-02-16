import React from 'react';
import { observer } from 'mobx-react-lite';

import BaseInput from '~/components/ui/BaseInput';
import BaseButton from '~/components/ui/BaseButton';

import styles from './AuthPage.module.scss';
import { Navigate } from 'react-router-dom';
import Loader from '~/components/ui/Loader';
import CookiesModal from '~/components/ui/CookiesModal';
import AuthStore from '~/core/stores/Auth.store';
import { inject } from 'mobx-react';

interface IAuthPage {
  authStore: AuthStore;
}

const AuthPage: React.FC<IAuthPage> = ({ authStore }) => {
  const [username, setUsername] = React.useState('');
  const [password, setPassword] = React.useState('');
  const initialCookie = !localStorage.getItem('access_cookie');
  const [isModalCookie, setIsModalCookie] = React.useState(initialCookie);

  const signIn = async (username: string, password: string) => {
    try {
      await authStore.login(username, password);
    } catch (error) {
      console.log('error----->', error);
    }
  };
  const handleChangeUsername = (value: string) => {
    setUsername(value);
  };
  const handleChangePassword = (value: string) => {
    setPassword(value);
  };

  if (authStore.isAuthenticated || localStorage.getItem('token')) {
    return <Navigate to={'/'} replace />;
  }

  if (authStore.isLoading) {
    return <div className={styles.loader}><Loader /></div>;
  }

  const handleClickCookie = () => {
    localStorage.setItem('access_cookie', String(true));
    setIsModalCookie(false);
  };

  return (
    <div className={styles.container}>
      <form
        className={styles.formContainer}
      >
        <BaseInput onChange={handleChangeUsername} name={'username'} title={'username'} />
        <BaseInput onChange={handleChangePassword} name={'password'} type={'password'} title={'password'} />
        <BaseButton type={'submit'} value={'Вход'} onClick={() => signIn(username, password)} />

      </form>
      {isModalCookie ?
        <CookiesModal isModalCookie={isModalCookie} setIsModalCookie={handleClickCookie} />
        : null}
    </div>
  );
};

export default inject('authStore')(observer(AuthPage));