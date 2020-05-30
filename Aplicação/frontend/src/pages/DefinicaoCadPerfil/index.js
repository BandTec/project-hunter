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

    const [jogo, setJogo] = useState('');
    const [posicao, setPosicao] = useState('');

    function handleVoltar(){
        history.push('/')
    }

    async function handleSignUp(e) {
        e.preventDefault();
                const data = {
                jogo,
                posicao
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

    const history = useHistory('');
    function chamaLogin() {

        history.push("/login");
    }

    return (
        <div className="contCadastro-container">

            <header>
                <img src={Logo} alt="HunterProject" onClick = {handleVoltar}></img>

                <p className="trilha">Cadastro/ Background Jogador</p>
                <br></br>
                <br></br>
                <h2 className="titulo">Agora selecione seu jogo favorito:</h2>
            </header>
<br></br>
<br></br>
<br></br>
<br></br>
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
