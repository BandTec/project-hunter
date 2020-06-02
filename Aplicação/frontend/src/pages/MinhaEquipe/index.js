import React, { useEffect, useState } from 'react';
import './styles.css'
import Logo from "../../assets/testeLogo3.png";

import Lol from "../../assets/lol-icon.svg";
import Overwatch from "../../assets/overwatch-icon.svg";
import User from "../../assets/user1.jpg"

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
    const [idEquipe, setIdEquipe] = useState('');
    
    localStorage.setItem('nomeEquipe', "Keyd");
    useEffect(() => {
        setNomeEquipe(localStorage.getItem('nomeEquipe'));
        api.get(`/equipegamer/equipe/${nomeEquipe}/`
    
        ).then(response => {
          setTeam(response.data);
          console.log(team);
          
          let dados = response.data;

          let temp = [];
    
          dados.forEach(item => {
            temp.push(
              criaDados(
                item.idEquipeGamer
                
              )
            );
          });
    
          setIdEquipe(temp[0].idEquipeGamer);
          console.log(temp[0].idEquipeGamer);


          
        })

        api.get(`/equipejogo/equipe/${idEquipe}/`

        ).then(response => {
            setTeamGames(response.data);
            console.log(teamGames);
        });

        // api.get(`/equipejogo/equipe/${idEquipe}/`
    
        // ).then(response => {
        //   setTeamGames(response.data);
        // })

      }, [nomeEquipe]);

      function criaDados(idEquipeGamer) {
        return { idEquipeGamer }
      }

    //   useEffect(() => {
        
    //     api.get(`/equipejogo/nome/${team.IdEquipeGamer}/`
    
    //     ).then(response => {
    //       setTeamGames(response.data);
    //     })
    //   }, [team]);

    //   useEffect(() => {
        
    //     api.get(`/equipejogo/nome/${team.IdEquipeGamer}/`
    
    //     ).then(response => {
    //       setTeamGames(response.data);
    //     })
    //   }, [team]);

    


    const [team, setTeam] = useState([]);
    const [teamGames, setTeamGames] = useState([]);
    const [teamGamers, setTeamGamers] = useState([]);
    const [teamHistory, setTeamHistory] = useState([]);

    const history = useHistory('');
    // const userId = localStorage.getItem('userId');
    const userName = localStorage.getItem('nome');
    // useEffect(() => {
    //     api.get('profile', {
    //         headers : {
    //             Authorization: userId,
    //         }
    //     }).then(response =>{
    //         setMatches(response.data);
    //     })
    // }, [userId]);



    function handleLogout() {
        localStorage.clear();
        history.push('/');
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
                                        <MenuItem onClick={handleClose}>Perfil</MenuItem>
                                        <MenuItem onClick={handleClose}>Equipes</MenuItem>
                                        <MenuItem onClick={handleClose}>Agendamentos</MenuItem>
                                        <MenuItem onClick={handleLogout}>Logout</MenuItem>
                                    </MenuList>
                                </ClickAwayListener>
                            </Paper>
                        </Grow>
                    )}
                </Popper>



            </header>

            <div className="div-profile">
                <img className="profile-pic" src={TeamPicture} alt="Foto de Perfil"></img>
                    <h1 className="profile-nic">{nomeEquipe}</h1>
                <h1 className="profile-rate"> <FiStar size={48} color="#F1DA07" />  4.96</h1>
            </div>


            <body>
                <div>

                    <h2>Jogadores:</h2>
                    <div className="current-members">
                     {team.map(team => (
                        <div key={team.idGamer.idGamer}>
                            <img src={User} alt="User-Icon" ></img>
                            <p>{team.idGamer.nome}</p>
                        </div>
                       ))}
                        
                    </div>

                    <h2>Jogos Atuais:</h2>
                    <div className="current-games">

                    {teamGames.map(team => (
                        <div key={team.idEquipe.idEquipe} >
                            <img src={Lol} alt="League Of Legends" ></img>
                            <p>{teamGames.idJogo.nomeJogo} </p>
                        </div>
                         ))}
                        <div>
                            <img src={Overwatch} alt="Overwatch" ></img>
                            <p>Overwhatch</p>
                        </div>
                    </div>

                </div>

                <div>
                    <h3>Ultimas Partidas:</h3>
                    <div className="last-games">


                        <p>Jogo</p>
                        <p>Resultado</p>

                        <div><p> <img src={Lol} alt="League Of Legends" style={{ width: '20px', height: '20px' }} ></img> League of Legends</p></div>
                        <div><p className="derrota">Derrota</p></div>
                        <div><p> <img src={Lol} alt="League Of Legends" style={{ width: '20px', height: '20px' }} ></img> League of Legends</p></div>
                        <div><p className="vitoria">Vitória</p></div>


                    </div>
                </div>
            </body>
        </div>
    );
}