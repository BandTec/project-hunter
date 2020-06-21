import React, { useEffect, useState } from 'react';

import "./styles.css"
import Logo from "../../assets/testeLogo3.png";

import Lol from "../../assets/lol-icon.svg";
import CSGO from "../../assets/csgo-icon.svg";
import Overwatch from "../../assets/overwatch-icon.svg";
import User from "../../assets/default-user.png"


import TeamPicture from "../../assets/team-icon.svg";
import { FiArrowLeft, FiStar, FiTrash2, FiSearch, FiUser, FiPlusCircle, FiMessageCircle, FiPrinter } from 'react-icons/fi'
import { Link, useHistory } from 'react-router-dom';
import apiEquipe from '../../services/apiEquipe';
import apiGamer from '../../services/apiGamer';
import apiArquivo from '../../services/apiArquivo';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Grow from '@material-ui/core/Grow';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Modal from "@material-ui/core/Modal";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import apiPartida from '../../services/apiPartida';

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
        position: "absolute",

        backgroundColor: "#000",
        font: "Roboto, Arial, Helvetica, sans-serif",
        color: "#fff",
        border: "2px solid #000",
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
        width: "700px",
    },

    divAbove: {

        alignItems: "center",
        justifyContent: "center",
    },



    nome: {
        display: "grid",
        gridTemplateColumns: "repeat(3, 1fr)",
        justifyContent: "space-between",
        marginBottom: "20px"
    },

    nomeStyle: {
        width: "340px",
        height: "48px",
        marginTop: "10px",
        marginBottom: "10px",
        marginRight: "5px",
        border: "1px solid #ddd",
        borderRadius: "4px",

        padding: "0 20px",
        fontSize: "16px",
        color: "#666",
    },

    imagem: {
        gridTemplateColumns: "repeat(2, 1fr)",
        justifyContent: "right",
        alignContent: "right",
    },

    imagemStyle: {
        width: "220px",
        height: "48px",
        marginTop: "10px",
        marginBottom: "10px",

        border: "1px solid #ddd",
        borderRadius: "4px",

        padding: "0 20px",
        fontSize: "16px",
        color: "#666",
    },


    buttonClose: {
        backgroundColor: "#000",
        color: "#00FF00",
        marginTop: "15px",
        border: "1px solid #00FF00",
        borderRadius: "4px",
        height: "48px",
        width: "112px",
        padding: "0 20px",
        fontSize: "16px",
        fontStyle: "bold",
        cursor: "pointer",
    },
    buttonCreate: {
        backgroundColor: "#00FF00",
        color: "#000",
        marginTop: "15px",
        marginLeft: "30px",
        border: "1px solid #000",
        borderRadius: "4px",
        height: "48px",
        width: "112px",
        padding: "0 20px",
        fontSize: "16px",
        fontStyle: "bold",
        cursor: "pointer",
    },

    input: {
        width: "280px",
        marginTop: "10px",
        marginBottom: "10px",
        border: "1px solid #ddd",
        borderRadius: "4px",
        height: "48px",
        padding: "0 20px",
        fontSize: "16px",
        color: "#666",
    },
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

    function rand() {
        return Math.round(Math.random() * 20) - 10;
    }
    function getModalStyle() {
        const top = 50 ;
        const left = 50 ;
        return {
            top: `${top}%`,
            left: `${left}%`,
            transform: `translate(-${top}%, -${left}%)`,
        };
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


    const [modalStyle] = React.useState(getModalStyle);
    const [openModal, setOpenModal] = React.useState(false);
    const classes2 = useStyles2();
    const [nomeEquipe, setNomeEquipe] = useState('');
    const [fotoEquipe, setFotoEquipe] = useState([]);
    const idEquipe = localStorage.getItem('idEquipe');
    const [pesquisa, setPesquisa] = useState("");
    const [solicitacoes, setSolicitacoes] = useState('');
    const idGamerLogado = localStorage.getItem('idGamer');
    const [idEquipeGamer, setIdEquipeGamer] = useState('');
    const [capitao, setCapitao] = useState([]);
    //localStorage.setItem('nomeEquipe', "keyd");
    const [openAlerta, setOpenAlerta] = React.useState(false);
    const [dadosAlerta, setDadosAlerta] = useState('');
    
    function handleOpenAlert (resposta) {
      setDadosAlerta(resposta);
      setOpenAlerta(true);
    };
  
    const handleCloseAlert = () => {
      setOpenAlerta(false);
    };

    const handleOpenModal = () => {
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
    };

    const body = (
        <div style={modalStyle} className={classes2.paper}>
            <center><h2>Solicitações</h2></center>
            <center>
                <div className={classes2.divAbove}>
                    <div className="solicitantes">
                        {solicitacoes ? solicitacoes.map(sol => (
                            <div className="div-above-solicitacoes">
                                <div key={sol.idGamer.idGamer} >
                                    <img className="profile-gamer-solicitacao" src={require(`../../assets/${sol.idGamer.fotoGamer}`)} alt="Foto de Perfil"></img>
                                    <p>{sol.idGamer.usuario}</p>
                                </div>
                                
                                <div className="botoes-div-above-solicitacoes">
                                    <button className="btnAprovar" onClick={() => aceitarJogador(sol.idEquipeGamer)}> Aprovar</button>

                                    <button className="btnRecusar" onClick={() => recusarJogador(sol.idEquipeGamer)}> Recusar</button>
                                </div>
                            </div>

                        )) : solicitacoes}
                    </div>

                </div>
                <center className="btn-modal">
                    <p style={{ width: "300px" }}>
                        <button className={classes2.buttonClose} onClick={handleCloseModal}>
                            Fechar
              </button>
                    </p>
                </center>
            </center>
        </div>
    );

    async function aceitarJogador(id) {
        
        try {
            const response = await apiEquipe.put(`/equipegamer/aceitar/${id}/`); //{
            if (response.status === 200) {
                handleOpenAlert("Jogador aceito com sucesso!");
                let dados = response.data.idEquipe;

                console.log(dados);

                //   adicionaJogador(dados);
                return;

            } else {
                handleOpenAlert("Erro ao aceitar jogador!");
            }
        } catch (err) {
            handleOpenAlert("Erro ao aceitar jogador ou conectar-se ao servidor!");
        }finally{
            window.location.reload(false);
        }

    }

    async function recusarJogador(id) {
       
        try {
            const response = await apiEquipe.put(`/equipegamer/recusar/${id}/`); //{
            if (response.status === 200) {
                handleOpenAlert("Jogador recusado com sucesso!");
                let dados = response.data.idEquipe;

                console.log(dados);

                //   recusaJogador(dados);
                return;

            } else {
                handleOpenAlert("Erro ao recusar jogador!");
            }
        } catch (err) {
            handleOpenAlert("Erro ao recusar jogador ou conectar-se ao servidor!");
        }finally{
            window.location.reload(false);
        }

    }

    async function handlePesquisa() {
        localStorage.setItem('pesquisa', pesquisa);
        history.push("/busca", pesquisa);
    }

    useEffect(() => {
        setNomeEquipe(localStorage.getItem('nomeEquipe'));
        apiEquipe.get(`/equipegamer/equipe/${nomeEquipe}/`

        ).then(response => {
            setTeam(response.data);
            console.log(team);

        })

    }, [nomeEquipe]);

    useEffect(() => {
        apiEquipe.get(`/equipe/nome/${nomeEquipe}/`

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

            //setIdEquipe(temp[0].idEquipe);

            //getPlayerPendente(idEquipe);
            console.log(fotoEquipe);
        });
    }, [nomeEquipe]);

    useEffect(() => {
        apiEquipe.get(`/equipejogo/equipe/${idEquipe}/`

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
        apiPartida.get(`/partida/equipe/antes/${idEquipe}/`

        ).then(response => {
            //setTeamGames(response.data);
            const { data = [] } = response || {};
            // verify response.data is an array
            const isArray = Array.isArray(data)
            isArray && setTeamHistory(data);

            console.log(teamHistory);
        });
    }, [idEquipe]);

    // useEffect(() => {
    //     api.get(`/equipegamer/pendente/${idEquipe}/`

    //     ).then(response => {
    //         //setTeamGames(response.data);
    //         const { data = [] } = response || {};
    //         // verify response.data is an array
    //         const isArray = Array.isArray(data)
    //         isArray && setSolicitacoes(data);

    //         console.log(solicitacoes);
    //     });
    // }, [idEquipe]);

    useEffect(() => {
        apiEquipe.get(`/equipegamer/pendente/${idEquipe}/`

        ).then(response => {
            //setTeamGames(response.data);
            const { data = [] } = response || {};
            // verify response.data is an array
            const isArray = Array.isArray(data)
            isArray && setSolicitacoes(data);

            console.log(solicitacoes);
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
            const response = await apiGamer.post('/gamer/logoff');
            if (response.status === 200) {
                localStorage.clear();
                history.push('/');
            } else {
                handleOpenAlert('Estamos encontrando problemas na conexão com o servidor');
            }
        } catch (err) {
            handleOpenAlert('Estamos encontrando problemas na conexão com o servidor');
        }

    }

    async function handleExportacao() {
        try {
            const response = await apiArquivo.post(`/arquivo/${1}`);
            if (response.status === 200) {
                handleOpenAlert('Exportação realizada com sucesso!');
            } else {
                handleOpenAlert('Estamos encontrando problemas para exportar seu arquivo!');
            }
        } catch (err) {
            handleOpenAlert('Estamos encontrando problemas para exportar seu arquivo!');
        }
    }

    useEffect(() => {
        try {

            apiEquipe.get(`/equipegamer/capitao/${idGamerLogado}/${idEquipe}/`).then(response => {
                console.log(response.status);
                if (response.status === 200) {

                    setCapitao(1);
                    console.log(capitao);

                } else {
                    setCapitao(0);
                    console.log(capitao);
                }
            });
        } catch (err) {
            handleOpenAlert("ID do jogador e/ou da equipe inválido(s)");

        }
    }, [capitao]);

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
                <div className="notificacoes">
                    {capitao === 1 ? (
                        <>
                        <button className="btn-notif" onClick={handleOpenModal}>
                            <FiMessageCircle size={23} color="#FFFFFF"></FiMessageCircle>
                        </button>
                        <button className="btn-exportacao" onClick={handleExportacao}>
                            <FiPrinter size={23} color="#FFFFFF"></FiPrinter>
                        </button>
                        </>
                    ) : null}
                </div>
                <Modal open={openModal} onClose={handleCloseModal}>
                    {body}
                </Modal>
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
