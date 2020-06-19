import React, { useState } from 'react';
import "./styles.css";
import logo from '../../assets/testeLogo3.svg';
import '../../routes.js';
import api from '../../services/api';
import { login } from "../../auth";
import { Link, useHistory } from 'react-router-dom';

export default function Login(){ 


  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const history = useHistory('');


  function chamaCadastro(){
  
    history.push('/cadastro');
  }

  async function handleSignIn(e){
    e.preventDefault();

    
    if (!email || !password) {
      alert("Preencha e-mail e senha para continuar!");
    } else {
      try {
        
          const response = await api.get(`/gamer/${email}/${password}/`);
          console.log(response.status);
          if (response.status === 200){
          
          login("@hunter-token");
          
          localStorage.setItem('email', email);
          
          history.push("/home");
          
          }else{
            alert("Email ou senha inválidos");
          }
      } catch (err) {
        alert("Email ou senha inválidos");
        
      }
    }
  };

  return (

      <div className="login-container">
        <form onSubmit={handleSignIn}>
          <img src={logo} alt="Hunter"></img>
          <p className = "campo" >Email:</p>
          <br></br>
          <input onChange={e => setEmail( e.target.value)} />
          <p className = "campo">Senha:</p>
          <br></br>
          <input type="password" onChange={e => setPassword(e.target.value)} />
          <Link to='/recuperar-senha'><a href=""> Esqueceu sua senha?</a></Link>
          <div className='login-container formBtn'>
          
           <button className="login-container btn Log" type="submit">Enviar</button>
          
           <button className="login-container btn Cad" onClick={chamaCadastro}>Cadastrar</button>
           
            

          </div>
        </form>
      </div>

    );

  }