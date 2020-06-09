import React, { useEffect, useState, useMemo, Component } from 'react';
import './styles.css'
import Logo from "../../assets/testeLogo3.png";
import { FiArrowLeft, FiPower, FiTrash2, FiSearch, FiUser, FiPlusCircle } from 'react-icons/fi'
import { Link, useHistory } from 'react-router-dom';
import api from '../../services/api';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Grow from '@material-ui/core/Grow';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import camera from '../../assets/camera.svg';
import '../../routes.js';
import { BrowserRouter as Router } from "react-router-dom";
import Modal from '@material-ui/core/Modal';

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  paper: {
    marginRight: theme.spacing(2),
    width: "150px",
    height: '100px',
  },
  button: {
    height: "60px",
    width: "60px",
    borderRadius: "100%",
    border: "1px solid #dcdce6",
    background: "transparent",
    marginLeft: "300px",
    transition: "border-color 0.2s",
  }
}));

const useStyles2 = makeStyles((theme) => ({
  paper: {
    position: 'absolute',

    backgroundColor: '#000',
    font: 'Roboto, Arial, Helvetica, sans-serif',
    color: '#fff',
    border: '2px solid #000',
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),


  },

  divAbove: {
    display: 'grid',
    gridTemplateColumns: 'repeat(2, 1fr)',
    alignItems: 'center',
    justifyContent: 'center',
  },

  divOpcional: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',

  },

  comboOpcional: {
    marginLeft: '5px'
  },

  buttonClose: {
    backgroundColor: '#000',
    color: '#00FF00',
    marginTop: '15px',
    border: '1px solid #00FF00',
    borderRadius: '4px',
    height: '48px',
    width: '112px',
    padding: '0 20px',
    fontSize: '16px',
    fontStyle: 'bold',
    cursor: 'pointer',
  },
  buttonCreate: {
    backgroundColor: '#00FF00',
    color: '#000',
    marginTop: '15px',
    marginLeft: '30px',
    border: '1px solid #000',
    borderRadius: '4px',
    height: '48px',
    width: '112px',
    padding: '0 20px',
    fontSize: '16px',
    fontStyle: 'bold',
    cursor: 'pointer',
  },
  select: {
    marginTop: '10px',
    marginBottom: '10px',
    border: '1px solid #ddd',
    borderRadius: '4px',
    height: '48px',
    padding: '0 20px',
    fontSize: '16px',
    color: '#666',
  },
  input: {
    marginTop: '10px',
    marginBottom: '10px',
    border: '1px solid #ddd',
    borderRadius: '4px',
    height: '48px',
    padding: '0 20px',
    fontSize: '16px',
    color: '#666',
  }
}));

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

  // Botão Usuário 

  const preview = useMemo(() => { return thumbnail ? URL.createObjectURL(thumbnail) : null },
  thumbnail);
function onChange(e) {
  this.setState({
      [e.target.name]: e.target.value
  })
}

  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const anchorRef = React.useRef(null);

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event) => {
    if (anchorRef.current && anchorRef.current.contains(event.target)) {
      return;
    }

    setOpen(false);
  };

  function handleListKeyDown(event) {
    if (event.key === 'Tab') {
      event.preventDefault();
      setOpen(false);
    }
  }

  // return focus to the button when we transitioned from !open -> open
  const prevOpen = React.useRef(open);
  React.useEffect(() => {
    if (prevOpen.current === true && open === false) {
      anchorRef.current.focus();
    }

    prevOpen.current = open;
  }, [open]);


  // Botão Usuário 

  const history = useHistory('');

  const emailSelect = localStorage.getItem('email')

  React.useEffect(() => {
    async function dadosPerfil() {
      const response = await api.get(`/gamer/${emailSelect}/`); //{
    
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

  function handleProfile() {

    history.push('/profile');
  }

  async function handleEquipe() {
    history.push('/equipe');
  }

  function handleAgendamento(){
    history.push('/agendamento');
    }

  async function handleLogout() {
    try {
      const response = await api.post('/gamer/logoff');
      if (response.status === 200) {
        localStorage.clear();
        history.push('/');
      } else {
        alert('Estamos encontrando problemas na conexão com o servidor');
      }
    } catch (err) {
      alert('Estamos encontrando problemas na conexão com o servidor');
    }

  }

  async function handleConfig() {
    history.push('/config');
  }

  function handleHome() {

    history.push('/home');
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
            senha
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

function chamaLogin() {

history.push("/login");
}

  return (
    <div className="configuracao-container">

      <header>
        <img src={Logo} alt="HunterProject"  onClick={handleHome}></img>


        <div className="input-pesquisa">
          <input type="text" placeholder="Busque por jogos, equipes..."></input>
          <button className="btn-pesquisa"><FiSearch size={18} color="#000000" /></button>
        </div>


        <Button
          ref={anchorRef}
          aria-controls={open ? 'menu-list-grow' : undefined}
          aria-haspopup="true"
          onClick={handleToggle}
          className={classes.button}

        >
          <FiUser size={36} color="#FFFFFF" />
        </Button>
        <Popper open={open} anchorEl={anchorRef.current} role={undefined} transition disablePortal >
          {({ TransitionProps, placement }) => (
            <Grow
              {...TransitionProps}
              style={{ transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom' }}
            >
              <Paper className={classes.root} >
                <ClickAwayListener onClickAway={handleClose}>
                  <MenuList autoFocusItem={open} id="menu-list-grow" onKeyDown={handleListKeyDown}>
                  <MenuItem onClick={handleProfile}>Perfil</MenuItem>
                                    <MenuItem onClick={handleEquipe}>Equipes</MenuItem>
                                    <MenuItem onClick={handleAgendamento}>Agendamentos</MenuItem>
                                    <MenuItem onClick={handleConfig}>Configurações</MenuItem>
                                    <MenuItem onClick={handleLogout}>Logout</MenuItem>
                  </MenuList>
                </ClickAwayListener>
              </Paper>
            </Grow>
          )}
        </Popper>
      </header>
<div className="formulario">
      <form onSubmit={handleSignUp}>
                <label id="thumbnail"
                    style={{ backgroundImage: `url(${preview})` }}
                    className={thumbnail ? 'has-thumbnail' : ''}
                >
                   {/* <img className="profile-pic" src = {require(`../../assets/${localStorage.getItem('nome')}-icon.jpg`)} alt="Foto de Perfil"></img> */}
                    <input type="file" onChange={event => setThumbnail(event.target.files[0])} />
                    <img src={camera} alt="Select your photo"></img>
                    
                </label>
                <p class = "campo">Nome:</p>
                <input value={nome} onChange={e => setNome(e.target.value)} />
                <p class = "campo">Usuário:</p>
                <input value = {usuario} onChange={e => setUsuario(e.target.value)} />
                <p class = "campo">CPF:</p>
                <input value={cpf} onChange={e => setCpf(e.target.value)} />
                <p class = "campo">Telefone:</p>
                <input value={telefone} onChange={e => setTelefone(e.target.value)} />
                <p class = "campo">E-mail:</p>
                <input value={email} onChange={e => setEmail(e.target.value)} />
                <p class = "campo">Senha:</p>
                <input type = "password" value={senha} onChange={e => setSenha(e.target.value)} />
                <p class = "campo">Jogo:</p>
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
                <p class = "campo">Posição:</p>
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
    </div>
  );
}