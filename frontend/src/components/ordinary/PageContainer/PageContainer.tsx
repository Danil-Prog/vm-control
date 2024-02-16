import { observer } from 'mobx-react-lite';
import { inject } from 'mobx-react';
import React from 'react';
import styles from './PageContainer.module.scss';
import { Outlet } from 'react-router-dom';

interface IPageContainer {

}

const PageContainer: React.FC<IPageContainer> = ({}) => {
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <Outlet />
      </div>
    </div>
  );
};

export default inject()(observer(PageContainer));