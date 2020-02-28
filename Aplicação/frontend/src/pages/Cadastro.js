import React, { Component } from 'react';
import { Redirect } from 'react-router';
import "./Cadastro.css";
import logo from '../logo.svg';
import Login from './Login';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";


class Cadastro extends Component {
    constructor(args) {
        super(args)
        this.state = {
            name: '',
            region: '',
            email: '',
            password: '',
            redirect: false
        }
    }

    onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    chamaLogin = () => {
        this.setState({
          redirect: true
        })
    }
    render() {

        if (this.state.redirect) {
            return <Redirect to="/Login/" />
        }
        else {
            return (
                <div className="cadastro-container">
                    <form>
                        <img src={logo} alt="Hunter"></img>
                        <input placeholder="Digite seu nome" />
                        <input placeholder="Digite seu e-mail" />
                        <select id="regiao">
                            <option value="sp">São Paulo</option>
                            <option value="rj">Rio de Janeiro</option>
                        </select>
                        <input type="password" placeholder="Digite Sua Senha" />
                        <input type="password" placeholder="Confirmar Senha" />

                        <div>
                            <button className="login container btVoltar" type="submit"
                                onClick={() => chamaLogin()}> Voltar</button>

                            <button className="login container btCad" onClick="Cadastrar()" type="submit"> Cadastrar</button>

                        </div>


                    </form>
                </div>

            );

        }
    }
}
export default Cadastro;