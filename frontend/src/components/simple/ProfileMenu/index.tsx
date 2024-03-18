import { IconUser } from '../../icons/IconUser';
import { inject } from 'mobx-react';
import { observer } from 'mobx-react-lite';
import AuthStore from '~/core/stores/Auth.store';
import React from 'react';
import ThemeToggle from '~/components/simple/ThemeToggle';
import styles from './ProfileMenu.module.scss';
import { IconLogout } from '~/components/icons/IconLogout';

interface IProfileMenuProps {
  authStore?: AuthStore;
}

const ProfileMenu: React.FC<IProfileMenuProps> = ({ authStore }) => {
  const [isShow, setIsShow] = React.useState(true);
  const handleClickLogout = async () => {
    await localStorage.removeItem('token');
    await authStore?.setAuth(false);
  };

  return (
    <div style={{ position: 'relative' }}>
      <div onClick={() => setIsShow(!isShow)} style={{ cursor: 'pointer' }}>
        <IconUser />
      </div>
      {isShow ?
        <div className={styles.menuContainer}>
          <ThemeToggle />
          <a className={styles.logout} onClick={handleClickLogout}>
            <IconLogout />
            <p>Выход</p>
          </a>
          <p>{process.env.REACT_APP_VERSION}</p>
        </div>
        : null}
    </div>
  );
};

export default inject('authStore')(observer(ProfileMenu));