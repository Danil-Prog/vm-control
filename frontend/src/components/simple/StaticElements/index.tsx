import React from 'react';

import Header from '~/components/ordinary/Header/Header';
import Sidebar from '~/components/ordinary/Sidebar';
import PageContainer from '~/components/ordinary/PageContainer/PageContainer';
import LogPanel from '~/components/ordinary/LogPanel';

import styles from './StaticElements.module.scss';

const StaticElements = () => {
  return (
    <div className={styles.mainContainer}>
      {<Header />}
      <div className={styles.secondContainer}>
        {<Sidebar />}
        {<PageContainer />}
      </div>
      {<LogPanel />}
    </div>
  );
};

export default StaticElements;