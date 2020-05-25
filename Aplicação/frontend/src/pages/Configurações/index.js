import React, { Component, useState, useMemo } from 'react';
import { Link, useHistory } from 'react-router-dom';
import "./styles.css";
import logo from '../../assets/testeLogo3.svg';
import camera from '../../assets/camera.svg';
import '../../routes.js';
import api from '../../services/api';
import { BrowserRouter as Router } from "react-router-dom";


export default function Configurações() {



    const [thumbnail, setThumbnail] = useState(null);
    const [nome, setNome] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [jogo, setJogo] = useState('');
    const [posicao, setPosicao] = useState('');
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
                cpf,
                email,
                telefone,
                posicao,
                jogo,
                senha,
            };

            try {
                const response = await api.post('/usuario/criar', data);
                //alert(`Seu ID de Acesso ${response.data.id}`);
                if (response.status === 200){
                history.push('/DefinicaoCadPerfil');
                }else{
                    alert('Falha na atualização, tente novamente');
                }
            } catch (err) {
                alert('Falha na atualização, tente novamente');
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
        <div className="configuracao-container">
            <form onSubmit={handleSignUp}>
                <img src={logo} alt="Hunter"></img>

                <label id="thumbnail"
                    style={{ backgroundImage: `url(${preview})` }}
                    className={thumbnail ? 'has-thumbnail' : ''}
                >
                    <input type="file" onChange={event => setThumbnail(event.target.files[0])} />
                    <img src={camera} alt="Select your photo"></img>
                </label>
                <input placeholder="Digite seu nome" onChange={e => setNome(e.target.value)} />
                <input placeholder="Digite seu CPF (1234567890)" onChange={e => setCpf(e.target.value)} />
                <input placeholder="Digite seu e-mail" onChange={e => setEmail(e.target.value)} />
                <input placeholder="Digite seu telefone (11999999999)" onChange={e => setTelefone(e.target.value)} />
                <input placeholder="Digite seu jogo principal" onChange={e => setJogo(e.target.value)} />
                <input placeholder="Digite sua posição" onChange={e => setPosicao(e.target.value)} />
                <input type="password" placeholder="Digite Sua Senha" onChange={e => setSenha(e.target.value)} />
                <select placeholder="Digite seu jogo principal" onChange={e => setJogo(e.target.value)}>
                <option value='0'>Selecione o jogo</option>
                <option value='1'>Counter-Strike: Global Offensive</option>
                <option value='2'>Valorant</option>
                <option value='3'>League of Legends</option>
                <option value='4'>Fortnite</option>
                <option value='5'>DOTA 2</option>
                <option value='6'>Call of Duty: Warzone</option>
                <option value='7'>PlayerUnkown's Battlegrounds</option>

                </select>


                <input placeholder="Digite sua posição" onChange={e => setPosicao(e.target.value)} />
                <Router>
                    <div>
                        <button className="configuracao container btn Cas" type= "submit">Editar</button>
                        <button className="configuracao container btn Cad"  type="submit">Salvar</button>

                    </div>
                </Router>


            </form>
        </div>

    );

}
    //}
//}
