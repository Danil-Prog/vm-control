import { IconUser } from '../../icons/IconUser';
import { inject } from 'mobx-react';
import { observer } from 'mobx-react-lite';
import AuthStore from '~/core/stores/Auth.store';
import React from 'react';
import ThemeToggle from '~/components/simple/ThemeToggle';
import styles from './ProfileMenu.module.scss';
import { IconLogout } from '~/components/icons/IconLogout';
import { AnimatePresence, motion } from 'framer-motion';

interface IProfileMenuProps {
  authStore?: AuthStore;
}

const ProfileMenu: React.FC<IProfileMenuProps> = ({ authStore }) => {
  const [isShow, setIsShow] = React.useState(false);
  const pkg = require('../../../../package.json');
  const handleClickLogout = async () => {
    await localStorage.removeItem('token');
    await authStore?.setAuth(false);
  };
  const ref = React.useRef(null);
  const handleClickOutside = (event) => {
    if (ref.current && !ref.current.contains(event.target)) {
      console.log('Клик вне компонента');
      setIsShow(false);
      // Здесь можно добавить логику, которая должна выполняться при клике вне компонента
    }
  };

  React.useEffect(() => {
    // Добавляем обработчик событий при монтировании компонента
    document.addEventListener('mousedown', handleClickOutside);

    // Удаляем обработчик событий при размонтировании компонента
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div style={{ position: 'relative' }} ref={ref}>
      <div onClick={() => setIsShow(!isShow)} style={{ cursor: 'pointer' }}>
        <IconUser />
      </div>
      <AnimatePresence>
        {isShow ?
          <motion.div
            className={styles.menuContainer}
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{
              duration: 0.3,
              ease: 'linear'
            }}
          >
            <a className={styles.menuItem}>
              <ThemeToggle />
            </a>
            <a className={styles.menuItem} onClick={handleClickLogout}>
              <IconLogout />
              <p>Выход</p>
            </a>

            <p className={styles.version}>Версия приложения: {pkg.version}</p>
          </motion.div>
          : null}
      </AnimatePresence>
    </div>
  );
};

export default inject('authStore')(observer(ProfileMenu));