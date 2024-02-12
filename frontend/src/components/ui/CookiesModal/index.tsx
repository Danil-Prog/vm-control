import React from 'react';
import styles from './CookiesModal.module.scss';
import BaseButton from '~/components/ui/BaseButton';

interface ICookiesModal {
  isModalCookie: boolean;
  setIsModalCookie: () => void;
}

const CookiesModal: React.FC<ICookiesModal> = ({ isModalCookie, setIsModalCookie }) => {
  React.useEffect(() => {
  }, [isModalCookie]);

  return (
    <>
      <div className={styles.container}>
        <p className={styles.description}>Мы используем файлы cookies для улучшения работы сайта. Оставаясь на нашем
          сайте, вы соглашаетесь с условиями
          использования файлов cookies. Чтобы ознакомиться с нашими Положениями о конфиденциальности и об использовании
          файлов cookie, <a href="#" target="_blank">нажмите здесь</a>.</p>
        <BaseButton value={'Я согласен'} onClick={setIsModalCookie} type={'button'} />
      </div>
    </>
  );
};

export default CookiesModal;