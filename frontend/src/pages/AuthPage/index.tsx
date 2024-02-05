import React, { useContext } from 'react';
import { observer } from 'mobx-react-lite';

import BaseInput from '~/components/ui/BaseInput';
import BaseButton from '~/components/ui/BaseButton';

import styles from './AuthPage.module.scss';
import { Context } from '~/index';
import { Navigate } from 'react-router-dom';


const AuthPage = () => {
  const [username, setUsername] = React.useState('');
  const [password, setPassword] = React.useState('');
  const { authStore } = useContext(Context);

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
  const handleSubmit = async (event) => {
    event.preventDefault();
    await signIn(username, password);
  };

  if (authStore.isAuthenticated || localStorage.getItem('token')) {
    return <Navigate to={'/'} />;
  }

  if (authStore.isLoading) {
    return <div>Загрузка...</div>;
  }

  return (
    <div className={styles.container}>
      <form
        className={styles.formContainer}
        onSubmit={handleSubmit}
      >
        <BaseInput onChange={handleChangeUsername} name={'username'} />
        <BaseInput onChange={handleChangePassword} name={'password'} type={'password'} />
        <BaseButton type={'submit'} value={'Вход'} onClick={handleSubmit} />
      </form>
    </div>
  );
};

export default observer(AuthPage);