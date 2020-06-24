import React, { Component } from "react";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import Login from "./pages/Login";
import Cadastro from "./pages/Cadastro";
import RecSenha from "./pages/RecSenha";
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import Configurações from "./pages/Configurações";
import MinhaEquipe from "./pages/MinhaEquipe";
import DefinicaoCadPerfil from "./pages/DefinicaoCadPerfil";
import { isAuthenticated } from "./auth";
import ContCadastro from "./pages/DefinicaoCadPerfil";
import Buscar from "./pages/Busca";
import Equipes from "./pages/Equipes";

const PrivateRoute = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={(props) =>
      isAuthenticated() ? (
        <Component {...props} />
      ) : (
        <Redirect to={{ pathname: "/", state: { from: props.location } }} />
      )
    }
  />
);

class routes extends Component {
  render() {
    return (
      <BrowserRouter>
        <>
          <Switch>
            <Route exact path="/" component={Login} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/cadastro" component={Cadastro} />
            <Route exact path="/contcad" component={ContCadastro} />
            <Route
              exact
              path="/continuacaocadastro"
              component={DefinicaoCadPerfil}
            />
            <Route exact path="/recuperar-senha" component={RecSenha} />
            <PrivateRoute path="/home" component={Home} />
            <PrivateRoute exact path="/profile" component={Profile} />
            <PrivateRoute exact path="/config" component={Configurações} />
            <PrivateRoute exact path="/perfil-equipe/" component={MinhaEquipe} />
            <PrivateRoute exact path="/equipes/" component={Equipes} />
            <PrivateRoute exact path="/busca" component={Buscar} />
          </Switch>
        </>
      </BrowserRouter>
    );
  }
}

export default routes;
