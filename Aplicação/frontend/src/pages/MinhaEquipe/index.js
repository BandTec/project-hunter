import React, { useEffect, useState } from 'react';

import "./styles.css"
import Logo from "../../assets/testeLogo3.png";

import Lol from "../../assets/lol-icon.svg";
import CSGO from "../../assets/csgo-icon.svg";
import Overwatch from "../../assets/overwatch-icon.svg";
import User from "../../assets/default-user.png"


import TeamPicture from "../../assets/team-icon.svg";
import { FiArrowLeft, FiStar, FiTrash2, FiSearch, FiUser, FiPlusCircle, FiMessageCircle } from 'react-icons/fi'
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
    const [pesquisa, setPesquisa] = useState("");
    const idGamerLogado = localStorage.getItem('idGamer');

    //localStorage.setItem('nomeEquipe', "keyd");

    async function handlePesquisa () {
        localStorage.setItem('pesquisa', pesquisa);
        history.push("/busca", pesquisa);
      }

    useEffect(() => {
        setNomeEquipe(localStorage.getItem('nomeEquipe'));
        api.get(`/equipegamer/equipe/${nomeEquipe}/`

        ).then(response => {
            setTeam(response.data);
            console.log(team);

        })

    }, [nomeEquipe]);

    useEffect(() => {
        api.get(`/equipe/nome/${nomeEquipe}/`

        ).then(response => {
            //setTeamGames(response.data);
            const { data = [] } = response || {};
            // verify response.data is an array
            const isArray = Array.isArray(data)
            isArray && setFotoEquipe(data);


            let dados = response.data;

            let temp = [];

            dados.forEach(item => {
                temp.push(
                    criaDados(
                        item.idEquipe
                    )
                );
            });

            setIdEquipe(temp[0].idEquipe);

            console.log(fotoEquipe);
        });
    }, [nomeEquipe]);

    useEffect(() => {
        api.get(`/equipejogo/equipe/${idEquipe}/`

        ).then(response => {
            //setTeamGames(response.data);
            const { data = [] } = response || {};
            // verify response.data is an array
            const isArray = Array.isArray(data)
            isArray && setTeamGames(data);

            console.log(teamGames);
        });
    }, [idEquipe]);

    useEffect(() => {
        api.get(`/partida/equipe/antes/${idEquipe}/`

        ).then(response => {
            //setTeamGames(response.data);
            const { data = [] } = response || {};
            // verify response.data is an array
            const isArray = Array.isArray(data)
            isArray && setTeamHistory(data);

            console.log(teamHistory);
        });
    }, [idEquipe]);




    function criaDados(idEquipe) {
        return { idEquipe }
    }


    const [team, setTeam] = useState([]);
    const [teamGames, setTeamGames] = useState([]);
    const [teamGamers, setTeamGamers] = useState([]);
    const [teamHistory, setTeamHistory] = useState([]);

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

    async function handleCapitao(e){
        e.preventDefault();
    
          try {
            
              const response = await api.get(`/equipegamer/capitao/${idGamerLogado}/${idEquipe}/`);
              console.log(response.status);
              if (response.status === 200){
              
              const capitao = 1;
              
              }else{
              const  capitao = 0;
              }
          } catch (err) {
            alert("ID do jogador e/ou da equipe inválido(s)");
            
          }
        }
      };

    async function handleConfig() {
        history.push('/config');
    }

    async function handleEquipe() {
        history.push('/equipes');
    }


    function handleHome() {

        history.push('/home');
    }
    return (
        <div className="profile-container">

            <header>
                <img src={Logo} alt="HunterProject" onClick={handleHome}></img>
                {/* <span>Bem vindo, {userName}</span> */}

                <div className="input-pesquisa">
                <input
                     type="text"
                    placeholder="Busque por jogos ou equipes..."
                    onChange={(e) => setPesquisa(e.target.value)}
          ></input>
          <button className="btn-pesquisa" onClick={handlePesquisa}>
            <FiSearch size={18} color="#000000" />
          </button>
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
                {fotoEquipe.map(team => (
                    <img className="profile-pic" src={require(`../../assets/${team.fotoEquipe}`)} alt="Foto de Perfil"></img>
                ))}
                <h1 className="profile-nic">{nomeEquipe}</h1>
                {capitao == 1 ? return ( <button> Notificações </button>); : null }
                {/* <h1 className="profile-rate"> <FiStar size={48} color="#F1DA07" />  4.96</h1> */}
            </div>


            <body>
                <div>

                    <h3>Jogadores:</h3>
                    <div className="current-members">
                        {team.map(team => (
                            <div key={team.idGamer.idGamer}>
                            
                                {/* <img src={require(`../../assets/${team.idGamer.fotoGamer}`)} alt="User-Icon" ></img> */}
                                <img src={User} alt="User-Icon" ></img>
                                <p>{team.idGamer.nome}</p>
                            </div>
                        ))}

                    </div>

                    <h3>Jogos Atuais:</h3>
                    <div className="current-gamesE">

                        {teamGames.map(team => (
                            <div key={team.idJogo.idJogo} >
                                <img src={require(`../../assets/${team.idJogo.fotoJogo}`)} alt="Icone Jogo" ></img>
                                <p>{team.idJogo.nomeJogo}</p>

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



                        {/* <div><p> <img src={Lol} alt="League Of Legends" style={{ width: '20px', height: '20px' }} ></img> League of Legends</p></div>
                        <div><p className="vitoria">Vitória</p></div> */}


                    </div>
                </div>
            </body>
        </div>
    );
}