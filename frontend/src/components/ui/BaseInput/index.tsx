import styles from './baseInput.module.scss'

interface IBaseInput {
    onInput: () => void;
    inputMode?: "none" | "text" | "tel" | "url" | "email" | "numeric" | "decimal" | "search" | undefined;
    name: string;
    autoComplete?: string;
}
const BaseInput = (props: IBaseInput) => {
    return (
        <div className={styles.container}>
            <input
                type={'text'}
                onInput={props.onInput}
                inputMode={props.inputMode ?? "text"}
                className={styles.baseInput}
                name={props.name}
                autoComplete={props.autoComplete ?? 'true'}
            />
        </div>
    )
}

export default BaseInput;