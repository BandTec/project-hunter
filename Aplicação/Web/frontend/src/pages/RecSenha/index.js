import React, { useState } from 'react';
import './styles.css'
import Logo from "../../assets/logo.svg";
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
        <div className="register-container">
            <div className="content">

                <form onSubmit={handlePassRecover}>
                    <h1>Digite seu email para poder recuperar a senha</h1>
                    <input type="email" placeholder="E-mail" value={email} onChange={e => setEmail(e.target.value)} />
                    
                    <div>
                        <button className="button" type="submit">Enviar</button>
                        <Link to ='/'> <button className="button" type="submit">Voltar</button> </Link> 
                    </div>
                </form>
            </div>
        </div>
    );
}