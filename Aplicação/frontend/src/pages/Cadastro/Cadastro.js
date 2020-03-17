import React, { Component} from 'react';
import "./Cadastro.css";
import logo from '../../assets/logo.svg';
import '../../routes.js';
import { BrowserRouter as Router} from "react-router-dom";


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
        this.props.history.push("/login");
    }
    render() {

        if (this.state.redirect) {
        
            // return (
            //     <BrowserRouter>
            //       <Switch>
            //         <Route path="/" exact component={Login} />
            //         <Route path="/Login" exact component={Login} />
            //         <Route path="/Cadastro" component={Cadastro} />
            //       </Switch>
            //     </BrowserRouter>
            //   )
        
         
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
                        <Router>
                        <div>
                            <button className="login container btVoltar" type="submit"
                                onClick={() => this.chamaLogin()}> Voltar</button>

                            <button className="login container btCad" onClick="Cadastrar()" type="submit"> Cadastrar</button>

                        </div>
                        </Router>


                    </form>
                </div>

            );

        }
    }
}
export default Cadastro;