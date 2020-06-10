import React, { Component, useState, useMemo } from 'react';
import { Link, useHistory } from 'react-router-dom';
import "./styles.css";
import Logo from "../../assets/testeLogo3.png";
import camera from '../../assets/camera.svg';
import '../../routes.js';
import api from '../../services/api';
import { BrowserRouter as Router } from "react-router-dom";


export default function Cadastro() {



    const [thumbnail, setThumbnail] = useState(null);
    const [nome, setNome] = useState('');
    const [usuario, setUsuario] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [senha, setSenha] = useState('');
    const [confirmarSenha, setConfirmarSenha] = useState('');
    const [idJogo,setIdJogo] = useState('');
    const [idPosicao,setIdPosicao] = useState('')
    const [idGamer, setIdGamer] = useState('');

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
                const response = await api.post('/gamer/criar', data);
                //alert(`Seu ID de Acesso ${response.data.id}`);
                if (response.status === 200){
                handleSignUp2();
                }else{
                    alert('Erro no cadastro, tente novamente');
                }
            } catch (err) {
                alert('Erro no cadastro, tente novamente');
            }
        }
    }

    async function handleSignUp2(e) {
        e.preventDefault();
        if (idJogo === "" || idPosicao === "") {
            alert('Preencha seu jogo e posição!');
        } else {

            const data = {
                idJogo,
                idPosicao,
            };

            try {
                const response = await api.post('/gamerinfo', data);
                //alert(`Seu ID de Acesso ${response.data.id}`);
                if (response.status === 200){
                history.push('/home');
                }else{
                    alert('Erro no cadastro de seu jogo favorito e/ou posição, tente novamente!');
                }
            } catch (err) {
                alert('Erro no cadastro de seu jogo favorito e/ou posição, tente novamente!');
            }
        }
    }

    const history = useHistory('');
    function chamaLogin() {
        history.push("/login");
    }
    
    return (
        <div className="cadastro-container">

<header>
        <img src={Logo} alt="HunterProject" ></img>
        
</header>

            <form onSubmit={handleSignUp}>
               <center><label id="thumbnail"
                    style={{ backgroundImage: `url(${preview})` }}
                    className={thumbnail ? 'has-thumbnail' : ''}
                >
                    <input type="file" onChange={event => setThumbnail(event.target.files[0])} />
                    <img src={camera} alt="Select your photo"></img>
                </label></center>
                <div className="campos">
                <p class = "campo">Nome:</p>
                <input onChange={e => setNome(e.target.value)} />
                <p class = "campo">Usuário:</p>
                <input onChange={e => setUsuario(e.target.value)} />
                <p class = "campo">CPF:</p>
                <input onChange={e => setCpf(e.target.value)} />
                <p class = "campo">Telefone:</p>
                <input onChange={e => setTelefone(e.target.value)} />
                <p class = "campo">Email:</p>
                <input onChange={e => setEmail(e.target.value)} />
                
                <p class = "campo">Senha:</p>
                <input type="password" onChange={e => setSenha(e.target.value)} />
                
                <p class = "campo">Confirme sua senha:</p>
                <input type="password" onChange={e => setConfirmarSenha(e.target.value)} />
                <p class = "campo">Jogo:</p>
                <select onChange={e => setIdJogo(e.target.value)}>
                <option value='0'>Selecione o jogo</option>
                <option value='1'>Counter-Strike: Global Offensive</option>
                <option value='2'>Valorant</option>
                <option value='3'>League of Legends</option>
                <option value='4'>Fortnite</option>
                <option value='5'>DOTA 2</option>
                <option value='6'>Call of Duty: Warzone</option>
                <option value='7'>PlayerUnkown's Battlegrounds</option>
                </select>
                <p class = "campo">Posição:</p>
                <select onChange={e => setIdPosicao(e.target.value)}>
                <option value='0'>Selecione a sua posição</option>
                <option value='2'>Atirador</option>
                <option value='3'>Suporte</option>
                <option value='4'>Jungle</option>
                <option value='5'>Top</option>
                <option value='6'>Mid</option>
                <option value='7'>Entry Fragger</option>
                <option value='8'>Lurker</option>
                <option value ='9'>Capitão</option>
                <option value ='10'>Sniper</option>
                </select>
                </div>
                <br></br>
                <Router>
                    <div>

                        

                        <button className="cadastro container btn Voltar" type="submit"
                            onClick={chamaLogin}> Voltar</button>

                        <button className="cadastro container btn Cad"  type="submit"> Cadastrar</button>

                    </div>
                </Router>


            </form>
        </div>

    );

}
    //}
//}
