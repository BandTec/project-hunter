import React from 'react';
import "./Login.css";
import logo from '../logo.svg';
import Cadastro from './Cadastro';
import SlideDown from './React-slidedown.css';
 
export function SlideDropDown(props) {
  return (
    <SlideDown className={'login-container form'} transitionOnAppear>
      {props.open ? props.children : null}
    </SlideDown>
  )
}

function abrirCadastro(){
  window.location.href = <Cadastro/>;
  }

export default function Login(){
    return(
    
       
     
        <div className="login-container">
            <form>
        <img src={logo} alt="Hunter"></img>
        <input placeholder="Digite seu login"/>
        <input type="password" placeholder="Digite Sua Senha"/>
        <a href=""> Esqueceu sua senha?</a>
        <div>
        <button className="login container btCad" onClick='<Cadastro/>'> Cadastrar</button>
        <button className="login container btLog" type ="submit"> Enviar</button>
       
        </div>
            </form>
        </div>    
        
    );
    
}



