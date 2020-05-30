import React, { Component, useState, useMemo } from 'react';
import { FiArrowLeft } from 'react-icons/fi'
import { Link, useHistory } from 'react-router-dom';
import Logo from "../../assets/testeLogo3.png";
import lol from "../../assets/lolwp.jpg";
import cs from "../../assets/csgo.jpg";
import over from "../../assets/over.jpg"
import fort from "../../assets/fort.jpg"
import "./styles.css";
import logo from '../../assets/testeLogo3.svg';
import camera from '../../assets/camera.svg';
import '../../routes.js';
import api from '../../services/api';
import { BrowserRouter as Router } from "react-router-dom";


export default function ContCadastro() {



    const [thumbnail, setThumbnail] = useState(null);
    const [nome, setNome] = useState('');
    const [usuario, setUsuario] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [senha, setSenha] = useState('');
    const [confirmarSenha, setConfirmarSenha] = useState('');


    const preview = useMemo(() => { return thumbnail ? URL.createObjectURL(thumbnail) : null },
        thumbnail);
    function onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    async function handleSignUp(e) {
        e.preventDefault();
        if (senha != confirmarSenha) {
            alert('Os campos senha e confirmação de senha estão diferentes');
        } else {

            const data = {
                nome,
                usuario,
                cpf,
                email,
                telefone,
                senha,
            };

            try {
                const response = await api.post('/usuario/criar', data);
                //alert(`Seu ID de Acesso ${response.data.id}`);
                if (response.status === 200){
                history.push('/DefinicaoCadPerfil');
                }else{
                    alert('Erro no cadastro, tente novamente');
                }
            } catch (err) {
                alert('Erro no cadastro, tente novamente');
            }
        }
    }

    const history = useHistory('');
    function chamaLogin() {

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
        <div className="contCadastro-container">

            <header>
                    {/* <img src = {Logo} alt="HunterProject"></img> */}
                    {/* <span>Bem vindo, {userName}</span> */}
                    
                {/* <div className="input-pesquisa"> */}
                    {/* <input type="text" placeholder="Busque por jogos, equipes..."></input> */}
                    {/* <button className="btn-pesquisa"><FiSearch size={18} color="#000000"/></button> */}
                {/* </div> */}

                <Link className="voltar" to='/'><FiArrowLeft className="voltar" size={30} ></FiArrowLeft></Link>

                <p className="trilha">Cadastro/ Background Jogador</p>
                <br></br>
                <h2 className="titulo">Agora selecione seus jogos favoritos do momento:</h2>
            </header>

             <ul className="findGame">
                <li>
                    <center>
                    <figure>
                    <img src = {lol} alt="League of Legends" width="250"></img>
                    </figure>
                    <br></br>
                    <p><b>League of Legends</b></p>
                    </center>
                    <input type="checkbox" value="0" id="campo-checkbox1" />
                    
                </li>

                {/* <li>
                    <center>
                    <figure>
                    <img src = {lol} alt="League of Legends" width="350"></img>
                    </figure>
                    <br></br>
                    <p><b>League of Legends</b></p>
                    </center>
                    <input type="checkbox" value="0" id="campo-checkbox1" />
                    
                </li> */}

                <li>
                    <center>
                    <figure>
                    <img src = {cs} alt="Counter Strike" width="250"></img>
                    </figure>
                    <br></br>
                    <p><b>Counter Strike GO</b></p>
                    </center>
                    <input type="checkbox" value="0" id="campo-checkbox1" />
                    
                </li>

                <li>
                    <center>
                    <figure>
                    <img src = {over} alt="Overwatch" width="250"></img>
                    </figure>
                    <br></br>
                    <p><b>Overwatch</b></p>
                    </center>
                    <input type="checkbox" value="0" id="campo-checkbox1" />
                    
                </li>
                
                <li>
                    <center>
                    <figure>
                    <img src = {fort} alt="Fortnite" width="250"></img>
                    </figure>
                    <br></br>
                    <p><b>Fortnite</b></p>
                    </center>
                    <input type="checkbox" value="0" id="campo-checkbox1" />
                    
                </li>
                
                </ul>
                
                <center><button className="confirm" type="submit">Confirmar</button></center>
                
        </div>

    );

}
    //}
//}
