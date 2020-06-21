import React, { Component, useState, useMemo } from 'react';
import { Link, useHistory } from 'react-router-dom';
import "./styles.css";
import Logo from "../../assets/testeLogo3.png";
import camera from '../../assets/camera.svg';
import '../../routes.js';
import apiGamer from '../../services/apiGamer';
import { BrowserRouter as Router } from "react-router-dom";
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';


export default function Cadastro() {

    const [thumbnail, setThumbnail] = useState(null);
    const [nome, setNome] = useState('');
    const [usuario, setUsuario] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [senha, setSenha] = useState('');
    const [confirmarSenha, setConfirmarSenha] = useState('');
    const [confirmarEmail, setConfirmarEmail] = useState('');
    const [idJogo, setIdJogo] = useState('');
    const [idPosicao, setIdPosicao] = useState('');
    const [openAlerta, setOpenAlerta] = React.useState(false);
    const [dadosAlerta, setDadosAlerta] = useState('');

    function handleOpenAlert (resposta) {
        setDadosAlerta(resposta);
        setOpenAlerta(true);
      };
    
      const handleCloseAlert = () => {
        setOpenAlerta(false);
      };

    const preview = useMemo(() => { return thumbnail ? URL.createObjectURL(thumbnail) : null },
        thumbnail);
    function onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    async function handleSignUp(e) {
        e.preventDefault();
        if (email != confirmarEmail) {
            handleOpenAlert('Digite seu email corretamente!');
        } else if (senha != confirmarSenha) {
            handleOpenAlert('Digite sua senha corretamente!')
        }
        else {
            const data = {
                nome,
                usuario,
                cpf,
                email,
                telefone,
                senha,
            };

            try {
                const response = await apiGamer.post(`/gamer/criar`, data);
                //alert(`Seu ID de Acesso ${response.data.id}`);
                if (response.status === 201) {
                    handleSignUp2();
                } else {
                    handleOpenAlert('Erro no cadastro de seus dados, tente novamente');
                }
            } catch (err) {
                handleOpenAlert('Erro no cadastro de seus dados, tente novamente');
            }
        }
    }

    function handleKeyPress(event) {
        if (event.key === 'Enter') {
            console.log('enter press here! ')
        }
    }

    async function handleSignUp2() {
        if (idJogo === "" || idPosicao === "") {
            handleOpenAlert('Preencha seu jogo e posição!');
        } else {

            const data2 = {
                'idJogo': {
                    'idJogo': idJogo
                },
                'idPosicao': {
                    'idPosicao': idPosicao
                }
            };

            try {
                const response = await apiGamer.post(`/gamerinfo/${email}`, data2);
                //alert(`Seu ID de Acesso ${response.data.id}`);
                if (response.status === 201) {
                    handleOpenAlert('Cadastro realizado com sucesso!');
                    history.push('/login');
                } else {
                    handleOpenAlert('Erro no cadastro de seu jogo e/ou posição, tente novamente!');
                }
            } catch (err) {
                handleOpenAlert('Erro no cadastro de seu jogo e/ou posição, tente novamente!');
            }
        }
    }

    const history = useHistory('');
    function chamaLogin() {
        history.push("/login");
    }

    function handleVoltar() {
        history.push('/')
    }

    return (
        <div className="cadastro-container">
            <header>
                <img src={Logo} alt="HunterProject" onClick={handleVoltar}></img>
            </header>

            <form onSubmit={handleSignUp}>
                <center><label id="thumbnail"
                    style={{ backgroundImage: `url(${preview})` }}
                    className={thumbnail ? 'has-thumbnail' : ''}
                >
                    <input type="file" onChange={event => setThumbnail(event.target.files[0])} />
                    <img src={camera} alt="Select your photo"></img>
                </label></center>
                <rows>
                    <br></br>
                    <div className="campos3">
                        <div className="campos">
                            <p className="campo">Nome:</p>
                            <input onChange={e => setNome(e.target.value)} />
                            <p className="campo">CPF:</p>
                            <input onChange={e => setCpf(e.target.value)} />
                            <p className="campo">Email:</p>
                            <input onChange={e => setEmail(e.target.value)} />
                            <p className="campo">Senha:</p>
                            <input type="password" onChange={e => setSenha(e.target.value)} />
                            <p className="campo">Jogo:</p>
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
                        </div>
                        <div className="campos2">
                            <p className="campo">Usuário:</p>
                            <input onChange={e => setUsuario(e.target.value)} />
                            <p className="campo">Telefone:</p>
                            <input onChange={e => setTelefone(e.target.value)} />
                            <p className="campo">Confirme seu email:</p>
                            <input onChange={e => setConfirmarEmail(e.target.value)} />
                            <p className="campo">Confirme sua senha:</p>
                            <input type="password" onChange={e => setConfirmarSenha(e.target.value)} />
                            <p className="campo">Posição:</p>
                            <select onChange={e => setIdPosicao(e.target.value)}>
                                <option value='0'>Selecione a sua posição</option>
                                <option value='2'>Atirador</option>
                                <option value='3'>Suporte</option>
                                <option value='4'>Jungle</option>
                                <option value='5'>Top</option>
                                <option value='6'>Mid</option>
                                <option value='7'>Entry Fragger</option>
                                <option value='8'>Lurker</option>
                                <option value='9'>Capitão</option>
                                <option value='10'>Sniper</option>
                            </select>
                        </div>
                    </div> </rows>
                    <Router>
                <center>
                    <div className="btns">
                        <button className="cadastro container btn Voltar" type="button" onClick={chamaLogin}> Voltar</button>
                        <button className="cadastro container btn Cad" type="submit" onKeyPress={handleKeyPress}> Cadastrar</button>
                    </div>
                </center>
            </Router>
            </form>

            <div>
              <Dialog
                open={openAlerta}
                onClose={handleCloseAlert}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
              >
                <DialogTitle id="alert-dialog-title">Alerta</DialogTitle>
                <DialogContent>
                  <DialogContentText id="alert-dialog-description">
                    {dadosAlerta}
                  </DialogContentText>
                </DialogContent>
                <DialogActions>
                  <Button onClick={handleCloseAlert} color="primary" autoFocus>
                    OK
                  </Button>
                </DialogActions>
              </Dialog>
          </div>
            
        </div>

    );

}
    //}
//}