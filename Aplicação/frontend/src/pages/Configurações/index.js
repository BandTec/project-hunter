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
    const [usuario, setUsuario] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [jogo, setJogo] = useState('');
    const [posicao, setPosicao] = useState('');
    const [senha, setSenha] = useState('');

    console.log(localStorage);

    const preview = useMemo(() => { return thumbnail ? URL.createObjectURL(thumbnail) : null },
        thumbnail);
    function onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    React.useEffect(() => {
        async function dadosPerfil() {
          const response = await api.get(`/gamer/${email}/`); //{
        
       let dados = response.data;
  
       let temp = [];
  
       dados.forEach( item => {
         temp.push(
           criaDados(
             item.nome,
             item.usuario,
             item.cpf,
             item.email,
             item.senha,
             item.telefone
           )
         );
       });
       
       setNome(temp[0].nome);
       setUsuario(temp[0].usuario);
       setCpf(temp[0].cpf);
       setEmail(temp[0].email);
       setSenha(temp[0].senha);
       setTelefone(temp[0].telefone);
       localStorage.setItem('nome', temp[0].nome);
       localStorage.setItem('usuario', temp[0].usuario);
       localStorage.setItem('cpf', temp[0].cpf);
       localStorage.setItem('email', temp[0].email);
       localStorage.setItem('senha', temp[0].senha);
       localStorage.setItem('telefone', temp[0].telefone);
     }
     dadosPerfil();
   }, []);

   function criaDados(nome, usuario, cpf, email, senha, telefone){
    return {nome, usuario, cpf, email, senha, telefone}
  }

    async function handleSignUp(e) {
        e.preventDefault();
        if (nome === "" || cpf === "" || email === "" || senha === "" || usuario === "" || telefone === "") {
            alert('Por favor preencha todos os campos!');
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

            const id = localStorage.getItem('idGamer');

            try {
                const response = await api.post(`/gamer/${id}}`, data);
                //alert(`Seu ID de Acesso ${response.data.id}`);
                if (response.status === 200){
                    alert('Dados alterados com sucesso!');
                    history.push('/Home');
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

    history.push("/login");
    }

    return (
        <div className="configuracao-container">
            <form onSubmit={handleSignUp}>
                <img src={logo} alt="Hunter" class = "logo"></img>

                <label id="thumbnail"
                    style={{ backgroundImage: `url(${preview})` }}
                    className={thumbnail ? 'has-thumbnail' : ''}
                >
                    <input type="file" onChange={event => setThumbnail(event.target.files[0])} />
                    <img src={camera} alt="Select your photo"></img>
                </label>
                <p>Nome:</p>
                <input value={nome} onChange={e => setNome(e.target.value)} />
                <p>Usuário:</p>
                <input value = {usuario} onChange={e => setUsuario(e.target.value)} />
                <p>CPF:</p>
                <input value={cpf} onChange={e => setCpf(e.target.value)} />
                <p>Telefone:</p>
                <input value={telefone} onChange={e => setTelefone(e.target.value)} />
                <p>E-mail:</p>
                <input value={email} onChange={e => setEmail(e.target.value)} />
                <p>Senha:</p>
                <input type = "password" value={senha} onChange={e => setSenha(e.target.value)} />
                <p>Jogo:</p>
                <select onChange={e => setJogo(e.target.value)}>
                <option value='0'>Selecione o jogo</option>
                <option value='1'>Counter-Strike: Global Offensive</option>
                <option value='2'>Valorant</option>
                <option value='3'>League of Legends</option>
                <option value='4'>Fortnite</option>
                <option value='5'>DOTA 2</option>
                <option value='6'>Call of Duty: Warzone</option>
                <option value='7'>PlayerUnkown's Battlegrounds</option>
                </select>
                <p>Posição:</p>
                <select onChange={e => setPosicao(e.target.value)}>
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
                <Router>
                    <div>

                        <button className="configuracao container btn Cad"  type="submit">Salvar</button>

                    </div>
                </Router>


            </form>
        </div>

    );

}
    //}
//}
