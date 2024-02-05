import { useRouteError } from 'react-router-dom';
import styles from './ErrorPage.module.scss';

export default function ErrorPage() {
  const error = useRouteError();
  console.error(error);

  return (
    <div className={styles.page}>
      <div className={styles.container}>
        <h1 className={styles.title}>Oops!</h1>
        <p className={styles.labelText}>Sorry, an unexpected error has occurred.</p>
        <p className={styles.description}>
          <i>{error.statusText || error.message || ''}</i>
        </p>
      </div>
    </div>
  );
}