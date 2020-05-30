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
                history.push('/continuacaocadastro');
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
        history.push("/login");
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

<header>
        <img src={Logo} alt="HunterProject" ></img>
        <p className="trilha">Cadastro</p>
</header>

            <form onSubmit={handleSignUp}>
                <label id="thumbnail"
                    style={{ backgroundImage: `url(${preview})` }}
                    className={thumbnail ? 'has-thumbnail' : ''}
                >
                    <input type="file" onChange={event => setThumbnail(event.target.files[0])} />
                    <img src={camera} alt="Select your photo"></img>
                </label>
                <p class = "campo">Nome:</p>
                <input onChange={e => setNome(e.target.value)} />
                <p class = "campo">Usuário:</p>
                <input onChange={e => setUsuario(e.target.value)} />
                <p class = "campo">CPF:</p>
                <input onChange={e => setCpf(e.target.value)} />
                <p class = "campo">Telefone:</p>
                <input onChange={e => setTelefone(e.target.value)} />
                <p class = "campo">E-mail:</p>
                <input onChange={e => setEmail(e.target.value)} />
                <p class = "campo">Senha:</p>
                <input type="password" onChange={e => setSenha(e.target.value)} />
                <p class = "campo">Confirme seua senha:</p>
                <input type="password" onChange={e => setConfirmarSenha(e.target.value)} />
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
