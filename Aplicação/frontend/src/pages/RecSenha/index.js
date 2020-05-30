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

    function handleVoltar(){
        history.push('/')
    }

    async function handlePassRecover(e) {
        e.preventDefault();

    
    if (!email) {
      alert("Por favor, digite seu e-mail para continuar!");
    } else {
      try {
        
          const response = await api.get(`/email/${email}/`);
          console.log(response.status);
          if (response.status === 200){
          
          localStorage.setItem('email', email);
          
          alert('As instruções de recuperação de senha enviadas para seu email!');

          history.push("/");
          
          }else{
            alert("Email inválido");
          }
      } catch (err) {
        alert("Email inválido");
        
      }
    }
  };

    return (
        <div className="rec-senha-container">
            <header>
                <img src={Logo} alt="HunterProject" onClick = {handleVoltar}></img>

                <p className="trilha">/Recuperação de Senha</p>
            </header>
            <div >
                <form onSubmit={handlePassRecover}>
                    <h1>Digite seu email:</h1>
                    <input type="email" value={email} onChange={e => setEmail(e.target.value)} />

                    <div>
                        <button className="btn env" type="submit">Enviar</button>
                    </div>
                </form>
            </div>
        </div>
    );
}