import React, { Component } from 'react';
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom'
import Login from "./pages/Login/Login"
import Cadastro from "./pages/Cadastro/Cadastro"
import { isAuthenticated } from './auth';

// function bd(){
// const connStr = "Server=jdbc:sqlserver://banco01191117.database.windows.net:1433;Database=Banco01191117;User Id=Gustavo01191117;Password=#Gf50422207802;";
// const sql = require("mssql");

// sql.connect(connStr)
//    .then(conn => console.log("conectou!"))
//    .catch(err => console.log("erro! " + err));

   
// }


const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        isAuthenticated() ? (
            <Component {...props} />
        ) : (<Redirect to={{ pathname: '/',state : {from: props.location} }}/>
            )
    )} />
)


class routes extends Component {
    render() {
        return (
            <BrowserRouter>
                <>

                    <Switch>
                        <Route exact path="/" component={Login} />
                        <Route exact path="/login" component={Login} />
                        <Route exact path="/cadastro" component={Cadastro} />
                        <PrivateRoute path="/app" component={() => <h1>Você está autenticado</h1>}/>
                    </Switch>
                </>
            </BrowserRouter>
        )
    }
}

export default routes;