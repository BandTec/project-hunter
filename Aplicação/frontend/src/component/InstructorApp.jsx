import React, { Component } from 'react';
//import GamerComponent from './GamerComponent';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
// import CourseComponent from './CourseComponent';
import Login from "../pages/Login"
import Cadastro from "../pages/Cadastro"


class InstructorApp extends Component {
    render() {
        return (
            <Router>
                <>
                
                    <Switch>
                        <Route path="/" exact component={Login} />
                        <Route path="/Login" exact component={Login} />
                        <Route path="/Cadastro" component={Cadastro} />
                    </Switch>
                </>
            </Router>
        )
    }
}

export default InstructorApp