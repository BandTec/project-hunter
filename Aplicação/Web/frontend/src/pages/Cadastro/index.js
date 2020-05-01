import React, { Component, useState, useMemo} from 'react';
import { Link, useHistory } from 'react-router-dom'; 
import "./styles.css";
import logo from '../../assets/testeLogo3.svg';
import camera from '../../assets/camera.svg';
import '../../routes.js';
import { BrowserRouter as Router} from "react-router-dom";


export default function Cadastro(){ 
 

    const[thumbnail, setThumbnail] = useState(null);
    const preview = useMemo(() =>{return thumbnail ? URL.createObjectURL(thumbnail) : null},
    thumbnail);
    function onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    const history = useHistory('');
    function chamaLogin(){
        
        // this.setState({
        //   redirect: true 
        // })
        /* this.props.*/history.push("/login");
    }
    // render() {

    //     if (this.state.redirect) {
        
    //         // return (
    //         //     <BrowserRouter>
    //         //       <Switch>
    //         //         <Route path="/" exact component={Login} />
    //         //         <Route path="/Login" exact component={Login} />
    //         //         <Route path="/Cadastro" component={Cadastro} />
    //         //       </Switch>
    //         //     </BrowserRouter>
    //         //   )
        
         
    //     }
    //     else {
            return (
                <div className="cadastro-container">
                    <form>
                        <img src={logo} alt="Hunter"></img>

                        <label id="thumbnail" 
                        style={{ backgroundImage : `url(${preview})`}}
                        className={thumbnail ? 'has-thumbnail' : ''}
                        >
                            <input type="file" onChange={event => setThumbnail(event.target.files[0])}/>
                            <img src={camera} alt="Select your photo"></img>
                        </label>
                        <input placeholder="Digite seu nome" />
                        <input placeholder="Digite seu e-mail" />
                        
                        <input type="password" placeholder="Digite Sua Senha" />
                        <input type="password" placeholder="Confirmar Senha" />
                        <Router>
                        <div>

                            <button className="cadastro container btn Cad" onClick="Cadastrar()" type="submit"> Cadastrar</button>
                            
                           <button className="cadastro container btn Voltar" type="submit"
                                onClick={chamaLogin}> Voltar</button>

                           

                        </div>
                        </Router>


                    </form>
                </div>

            );

        }
    //}
//}
