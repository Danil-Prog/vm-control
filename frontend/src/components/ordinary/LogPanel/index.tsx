import { observer } from 'mobx-react-lite';
import { inject } from 'mobx-react';
import React from 'react';
import styles from './LogPanel.module.scss';

interface ILogPanel {

}

const LogPanel: React.FC<ILogPanel> = ({}) => {
  return (
    <div className={styles.container}>
      <div className={styles.header}>Header</div>
      <div className={styles.content}>
        LOGPANEL
      </div>
    </div>
  );
};

export default inject()(observer(LogPanel));