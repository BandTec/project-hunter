import React, { useEffect, useState } from 'react';
import './styles.css'
import Logo from "../../assets/testeLogo3.png";

import TeamPicture from "../../assets/team-icon.svg";
import { FiArrowLeft, FiStar, FiTrash2, FiSearch, FiUser, FiPlusCircle } from 'react-icons/fi'
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

export default function MyTeam() {
    // Botão Usuário 
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



    const [nomeEquipe, setNomeEquipe] = useState('');
    const [fotoEquipe, setFotoEquipe] = useState([]);
    const [idEquipe, setIdEquipe] = useState('');

    localStorage.setItem('nomeEquipe', "keyd");

    const email = localStorage.getItem('email');
    const [equipes, setEquipes] = useState([]);

    useEffect(() => {
        api.get(`/equipegamer/gamer/${email}/`

        ).then(response => {

            const { data = [] } = response || {};
            // verify response.data is an array
            const isArray = Array.isArray(data)
            isArray && setEquipes(data);
        })
    }, [email]);





    const history = useHistory('');
    // const userId = localStorage.getItem('userId');
    const userName = localStorage.getItem('nome');


    function handleProfile() {

        history.push('/profile');
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

    async function handleEquipe() {
        history.push('/equipes');
    }


    function handleHome() {

        history.push('/home');
    }

    function handleTeamProfile(name) {
        localStorage.setItem('nomeEquipe', name);
        history.push('/perfil-equipe');
    }

    return (
        <div className="profile-container">

            <header>
                <img src={Logo} alt="HunterProject" onClick={handleHome}></img>
                {/* <span>Bem vindo, {userName}</span> */}

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
                                        <MenuItem onClick={handleEquipe}>Minhas Equipes</MenuItem>
                                        <MenuItem onClick={handleConfig}>Configurações</MenuItem>
                                        <MenuItem onClick={handleLogout}>Logout</MenuItem>
                                    </MenuList>
                                </ClickAwayListener>
                            </Paper>
                        </Grow>
                    )}
                </Popper>



            </header>

            <div className="div-principal">
                {equipes.map(team => (
                    <div key={team.idEquipe.idEquipe} className="div-equipes">
                        <img src={require(`../../assets/${team.idEquipe.fotoEquipe}`)} onClick={() => handleTeamProfile(team.idEquipe.nomeEquipe)} alt="Icone Equipe"></img>
                        <p onClick={() => handleTeamProfile(team.idEquipe.nomeEquipe)}>{team.idEquipe.nomeEquipe}</p>
                    </div>
                ))}



                
            </div>

        </div>
    );
}

//<div className='div-criar-equipe'>
  //                  <button className="btn-criar" /*onClick={handleOpenModal}*/> <FiPlusCircle size={64} color="#000000" /> Criar Equipe</button>
//
  //              </div>
    //            <div className='div-entrar-equipe'>
      //              <button className="btn-criar" /*onClick={handleOpenModal}*/>  Entrar em Equipe </button>
//
  //              </div>
