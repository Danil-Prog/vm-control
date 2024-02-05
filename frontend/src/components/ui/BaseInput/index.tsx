import styles from './baseInput.module.scss';
import React, { FormEvent } from 'react';

interface IBaseInput {
  onChange: (value: string) => void;
  inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'numeric' | 'decimal' | 'search' | undefined;
  name: string;
  autoComplete?: string;
  type?: string;
}

const BaseInput = (props: IBaseInput) => {
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    props.onChange(e.target.value);
  };
  return (
    <div className={styles.container}>
      <input
        type={props.type ?? 'text'}
        onChange={handleInputChange}
        inputMode={props.inputMode ?? 'text'}
        className={styles.baseInput}
        name={props.name}
        autoComplete={props.autoComplete ?? 'true'}
      />
    </div>
  );
};

export default BaseInput;