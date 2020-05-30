import React, { useEffect, useState } from 'react';
import './styles.css'
import Logo from "../../assets/testeLogo3.png";

import Lol from "../../assets/lol-icon.svg";
import Overwatch from "../../assets/overwatch-icon.svg";
import Team from "../../assets/team-icon.svg"

import UserPicture from "../../assets/user1.jpg";
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

export default function Profile() {
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



    const [matches, setMatches] = useState([]);

    const history = useHistory('');
   
    const userName = localStorage.getItem('nome');
    const equipe = "Equipe E";
    
    useEffect(() => {
        api.get(`/gamer/nome/${userName}`,).then(response =>{
            setMatches(response.data);
        })
    }, [userName]);

    function handleAgendamento(){
        history.push('/agendamento');
    }

    function handleTeamPage(){
        localStorage.setItem('nomeEquipe', equipe);
        history.push('/equipe');
    }

    function handleLogout() {
        localStorage.clear();
        history.push('/');
    }
    function handleHome() {

        history.push('/home');
    }
    function handleProfile() {

        history.push('/profile');
      }

    async function handleConfig() {
        history.push('/config');
      }
    
    async function handleEquipe() {
        history.push('equipe');
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

            <div className="div-profile">
                <img className="profile-pic" src={UserPicture} alt="Foto de Perfil"></img>
                    <h1 className="profile-nic">{userName}</h1>
                <h1 className="profile-rate"> <FiStar size={48} color="#F1DA07" />  4.96</h1>
            </div>


            <body>
                <div>
                    <h2>Jogos Atuais:</h2>
                    <div className="current-games">


                        <div >
                            <img src={Lol} alt="League Of Legends" ></img>
                            <p>League of Legends </p>
                        </div>
                        <div>
                            <img src={Overwatch} alt="Overwatch" ></img>
                            <p>Overwhatch</p>
                        </div>
                    </div>

                    <h2>Equipes Atuais:</h2>
                    <div className="current-teams">

                        <img src={Team} alt="Team-Icon"  onClick={handleTeamPage}></img>
                        <p onClick={handleTeamPage}>Equipe E</p>
                    </div>
                </div>

                <div>
                    <h3>Ultimas Partidas:</h3>
                    <div className="last-games">


                        <p>Jogo</p>   
                        <p>Resultado</p>
                        
                        <div><p> <img src={Lol} alt="League Of Legends" style={{ width : '20px', height: '20px'}} ></img> League of Legends</p></div> 
                        <div><p className="derrota">Derrota</p></div>
                        <div><p> <img src={Lol} alt="League Of Legends" style={{ width : '20px', height: '20px'}} ></img> League of Legends</p></div> 
                        <div><p className="vitoria">Vitória</p></div>


                    </div>
                </div>
            </body>
        </div>
    );
}