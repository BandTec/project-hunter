import React from 'react';
import "./Login.css";
import logo from '../logo.svg';

export default function Cadastro(){
    return(
    
        <div className="login-container">
            <form>
        <img src={logo} alt="Hunter"></img>
        <input placeholder="Digite seu nome"/>
        <input placeholder="Digite seu e-mail"/>
        <select id= "regiao">
            <option value="sp">São Paulo</option>
            <option value="rj">Rio de Janeiro</option>
        </select>
        <input type="password" placeholder="Digite Sua Senha"/>
        <input type="password" placeholder="Confirmar Senha"/>
        
        <div>
        <button className="login container btLog" type ="submit"> Voltar</button>
        <button className="login container btCad" onClick="Cadastrar()" type ="submit"> Cadastrar</button>
       
        </div>
            </form>
        </div>    
        
    );

}