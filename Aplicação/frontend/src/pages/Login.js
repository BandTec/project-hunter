import React from 'react';
import "./Login.css";
import logo from '../logo.svg';

export default function Login(){
    return(
        <div className="login-container">
            <form>
        <img src={logo} alt="Hunter"></img>
        <input placeholder="Digite seu login"> </input>
        <button type ="submit"> Enviar</button>
            </form>
        </div>    
        
    );
}