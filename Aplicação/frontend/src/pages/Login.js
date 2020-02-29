import React, { Component } from 'react';
import { Redirect } from 'react-router';
import "./Login.css";
import logo from '../logo.svg';
import Cadastro from './Cadastro';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";


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
  }
  render() {

    if (this.state.redirect) {

      return <Router><Redirect to="/Cadastro/" /></Router>

    } else {

      return (



        <div className="login-container">
          <form>
            <img src={logo} alt="Hunter"></img>
            <input placeholder="Digite seu login" />
            <input type="password" placeholder="Digite Sua Senha" />
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
}
export default Login;



