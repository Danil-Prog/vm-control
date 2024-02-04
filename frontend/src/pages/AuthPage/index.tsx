import React from 'react';
import { Navigate } from 'react-router-dom';
import { observer } from 'mobx-react-lite';

import BaseInput from '~/components/ui/BaseInput';
import BaseButton from '~/components/ui/BaseButton';

import styles from './AuthPage.module.scss';
import AuthStore from '~/core/stores/Auth.store';


const AuthPage = observer(() => {
  const [username, setUsername] = React.useState('');
  const [password, setPassword] = React.useState('');

  const signIn = async (username: string, password: string) => {
    try {
      await AuthStore.authenticate(username, password);
      if (AuthStore.isAuthenticated) {
        <Navigate to={'/'} />;
      }
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

  return (
    <div className={styles.container}>
      <form
        className={styles.formContainer}
        onSubmit={handleSubmit}
      >
        <BaseInput onChange={handleChangeUsername} name={'username'} />
        <BaseInput onChange={handleChangePassword} name={'password'} />
        <BaseButton type={'submit'} value={'Вход'} onClick={handleSubmit} />
      </form>
    </div>
  );
});

export default AuthPage;