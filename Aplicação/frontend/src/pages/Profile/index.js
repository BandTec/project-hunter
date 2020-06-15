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
import User from "../../assets/default-user.png"
import CSGO from "../../assets/csgo-icon.svg";


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

    const history = useHistory('');
    const nome = localStorage.getItem('nome');
    const email = localStorage.getItem('email');
    const idGamer = localStorage.getItem('idGamer');
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

      useEffect(() => {
        api.get(`/gamerinfo/gamer/${email}/`
  
        ).then(response => {

            const { data = [] } = response || {};
            // verify response.data is an array
            const isArray = Array.isArray(data)
            isArray && setTeam(data);

            if (isArray){
            setUserGames(data);
}

            // setUserGames(response.data);
            console.log(userGames);
        });
      }, []);

      useEffect(() => {
        api.get(`/partida/gamer/antes/${idGamer}/`

        ).then(response => {
            //setTeamGames(response.data);
            const { data = [] } = response || {};
            // verify response.data is an array
            const isArray = Array.isArray(data)
            isArray && setTeamHistory(data);

            console.log(teamHistory);
        });
    }, [idGamer]);

    const [team, setTeam] = useState([]);
    const [userGames, setUserGames] = useState([]);
    const [teamHistory, setTeamHistory] = useState([]);

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
        history.push('/equipes');
      }
    return (
        <div className="profile-container">

            <header>
                <img src={Logo} alt="HunterProject" onClick={handleHome}></img>
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

            <div className="div-profile">
                <img className="profile-pic" src = {require(`../../assets/${localStorage.getItem('nome')}-icon.jpg`)} alt="Foto de Perfil"></img>
                <h1 className="profile-nic">{nome}</h1>
                <h1 className="profile-rate"> <FiStar size={48} color="#F1DA07" />  4.96</h1>
            </div>

            <body>
                <div>
                    <h2>Jogos:</h2>
                    <div className="current-games">

                        {userGames.map(team => (
                            <div key={team.idJogo.idJogo} >
                                <img src = {require(`../../assets/${team.idJogo.fotoJogo}`)} alt="Icone Jogo" ></img>
                                <p>{team.idJogo.nomeJogo}</p>
                            </div>
                            ))}
                    </div>

                    <h2>Equipes:</h2>
                    <div className="current-teams">
                    {equipes.map(team => (
                        <div key={team.idEquipe.idEquipe}>
                            <img src = {require(`../../assets/${team.idEquipe.fotoEquipe}`)} alt="Icone Jogo"></img>
                            <p>{team.idEquipe.nomeEquipe}</p>
                        </div>
                       ))}
                    </div>
                </div>

                <div>
                    <h3>Últimas Partidas:</h3>
                    <div className="last-games">


                        <p>Jogo</p>   
                        <p>Resultado</p>
                        {teamHistory.map(history => (
                            <>
                                <div key={history.idPartida} ><p> <img src={require(`../../assets/${history.idJogo.fotoJogo}`)} alt="League Of Legends" style={{ width: '20px', height: '20px' }} ></img>{history.idJogo.nomeJogo}</p></div>
                                <div>
                                    <p
                                        className={history.winner == false ? "derrota" : "vitoria"}
                                        value={history.winner == false ? "Derrota" : "Vitória"}
                                    >
                                        {history.winner == false ? "Derrota" : "Vitória"}
                                    </p>
                                </div>
                            </>
                        ))}
                    </div>
                </div>
            </body>
        </div>
    );
}