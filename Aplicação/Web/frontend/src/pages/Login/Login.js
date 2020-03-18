import React, { useState, Component } from 'react';
import "./Login.css";
import logo from '../../assets/logo.svg';
import '../../routes.js';
import api from '../../services/api';
import { login } from "../../auth";      

    class Login extends Component {

     constructor(args) {
     super(args)
     this.state = {
       username: '',
       password: '',
     redirect: false
     }
    }
    
    onChange(e) {
      this.setState({
        [e.target.name]: e.target.value
      })
    }

    chamaCadastro = () => {
      this.setState({
        redirect: true
      })
      this.props.history.push('/cadastro');
    }


    handleSignIn = async e => {
      e.preventDefault();
      const { email, password } = this.state;
      if (!email || !password) {
        this.setState({ error: "Preencha e-mail e senha para continuar!" });
      } else {
        try {
          if(email == "henrique@hotmail.com" && password == "123"){
          // const response = await api.post("/sessions", { email, password });
          login(/*response.data.token*/ "@hunter-Token");
          this.props.history.push("/app");
          }else{
            alert("Email ou senha inválidos");
          }
        } catch (err) {
          this.setState({
            error:
              "Houve um problema com o login, verifique suas credenciais. T.T"
          });
        }
      }
    };


    render() {

      
      return (

        <div className="login-container">
          <form onSubmit={this.handleSignIn}>
            <img src={logo} alt="Hunter"></img>
            <input placeholder="Digite seu Email" onChange={e => this.setState({ email: e.target.value })} />
            <input type="password" placeholder="Digite Sua Senha" onChange={e => this.setState({ password: e.target.value })} />
            <a href=""> Esqueceu sua senha?</a>
            <div>
              <button className="login container btCad" onClick={() => this.chamaCadastro()}> Cadastrar</button>
              <button className="login container btLog" type="submit"> Enviar</button>

            </div>
          </form>
        </div>

      );

    }
  }

  
export default Login;





