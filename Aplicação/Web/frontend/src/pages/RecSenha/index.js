import React, { useState } from 'react';
import './styles.css'
import Logo from "../../assets/testeLogo3.svg";
import { FiArrowLeft } from 'react-icons/fi'
import { Link, useHistory } from 'react-router-dom';
import api from '../../services/api';
import { waitForElementToBeRemoved } from '@testing-library/react';

export default function Register() {

    const [email, setEmail] = useState('');

    const history = useHistory();

    async function handlePassRecover(e) {
        e.preventDefault();

        const data = {
            email,
        };

        try {
            const response = await api.post('senha', data);
            alert(`Seu ID de Acesso ${response.data.id}`);
            history.push('/');
        } catch (err) {
            alert('Erro na recuperação, tente novamente');
        }
    }

    return (
        <div className="rec-senha-container">
            <header>
                <Link className="voltar" to='/'><FiArrowLeft className="voltar" size={30} ></FiArrowLeft> </Link>
            </header>
            <div >


                <form onSubmit={handlePassRecover}>
                    <h1>Digite seu email para poder recuperar a senha:</h1>
                    <input type="email" placeholder="E-mail" value={email} onChange={e => setEmail(e.target.value)} />

                    <div>
                        <button className="btn env" type="submit">Enviar</button>

                    </div>
                </form>
            </div>
        </div>
    );
}