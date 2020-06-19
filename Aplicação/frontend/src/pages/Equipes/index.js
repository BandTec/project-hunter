import React, { useEffect, useState, useMemo } from 'react';
import './styles.css'
import Logo from "../../assets/testeLogo3.png";


import camera from '../../assets/camera.svg';
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

import Modal from "@material-ui/core/Modal";


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
    },

    divAbove: {
        display: "grid",
        gridTemplateColumns: "repeat(2, 1fr)",
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

export default function Teams() {

    const [modalStyle] = React.useState(getModalStyle);
    const [openModal, setOpenModal] = React.useState(false);
    const [page, setPage] = useState(0);
    const [metadata, setMetadata] = useState([]);
    const [thumbnail, setThumbnail] = useState(null);
    const classes2 = useStyles2();
    const [pesquisa, setPesquisa] = useState("");


    const preview = useMemo(() => { return thumbnail ? URL.createObjectURL(thumbnail) : null },
        thumbnail);
    function onChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    const handleOpenModal = () => {
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
    };

    const body = (
        <center>
        <div style={modalStyle} className={classes2.paper}>
            <h2>Crie sua equipe</h2>
            
            <div className={classes2.divAbove}>
            <p>Escolha sua imagem :</p>

                <div className={classes2.imagem}>
                    <label id="thumbnail"
                        style={{ backgroundImage: `url(${preview})` }}
                        className={thumbnail ? 'has-thumbnail' : ''}
                    >
                        <input type="file" onChange={event => setThumbnail(event.target.files[0])} />
                        <img src={camera} alt="Select your photo"></img>
                    </label>
                </div>

                <p>Digite o nome da equipe:</p>
                <div className={classes2.nome}>
                    <input
                        placeholder="Ex: Fusion Gaming"
                        className={classes2.nomeStyle}
                        onChange={(e) => setNomeEquipeCriada(e.target.value)}
                    />
                </div>

                <p>Selecione o jogo principal:</p>
                <div className={classes2.nome}>
                    <select className={classes2.nomeStyle} onChange={e => setIdJogo(e.target.value)}>
                        <option value='0'>Selecione o jogo</option>
                        <option value='1'>Counter-Strike: Global Offensive</option>
                        <option value='2'>Valorant</option>
                        <option value='3'>League of Legends</option>
                        <option value='4'>Fortnite</option>
                        <option value='5'>DOTA 2</option>
                        <option value='6'>Call of Duty: Warzone</option>
                        <option value='7'>PlayerUnkown's Battlegrounds</option>
                    </select>
                </div>
                
                <center className="btn-modal-criarEquipe">
                <p style={{ width: "300px"}}>
                    <button className={classes2.buttonClose} onClick={handleCloseModal}>
                        Fechar
              </button>
                    <button className={classes2.buttonCreate} onClick={envioDadosEquipe}>
                        Criar
              </button>
                </p>
                </center>
            </div>
        </div>
        </center>
    );



    async function envioDadosEquipe() {
        if (!nomeEquipeCriada || !thumbnail) {
            alert("Preencha os dados necessários");
          } else {
            
            const fotoEquipeCriada = nomeEquipeCriada.toLowerCase() + "-icon.png"; 

            const data = {
              nomeEquipe : nomeEquipeCriada,
              fotoEquipe: fotoEquipeCriada
            };

            try {
                const response = await api.post(`/equipe/`, data); //{
                    if (response.status === 201){
                        envioDadosJogo(response.data.idEquipe);
                        }else{
                            alert('Erro no cadastro da sua equipe, tente novamente!');
                        }
                    } catch (err) {
                        alert('Erro no cadastro da sua equipe, tente novamente!');
              }
            }

    }

    async function envioDadosJogo(id) {
        if (idJogo === "" ) {
            alert('Por favor, preencha o jogo que sua equipe mais joga!');
        } else {

            const data2 = {
                'idEquipe': {
                    'idEquipe': id
                  },
                  'idJogo': {
                    'idJogo': idJogo
                  }
                };

            try {
                const response = await api.post(`/equipejogo/`, data2);
                //alert(`Seu ID de Acesso ${response.data.id}`);
                if (response.status === 201){
                    alert('Equipe criada com sucesso!');
                }else{
                    alert('Erro no cadastro do jogo, tente novamente!');
                }
            } catch (err) {
                alert('Erro no cadastro do jogo, tente novamente!');
            }
        }
    }

    async function adicionaJogador(dados){

       

        const data = {
            idEquipe: {
               idEquipe: dados
            },
            idGamer: {
                idGamer: localStorage.getItem("idGamer")
            },
            idStatus:{
                idStatus: 1 
            },
            capitao: true
          };

          try {
            const response = await api.post(`/equipegamer/`, data); //{
            if (response.status === 201) {
              
              alert('Usuário adicionado');
              handleCloseModal();
              return;
               
            } else {
              alert("Erro ao adicionar jogador");
            }
          } catch (err) {
            alert("Erro ao adicionar jogador ou conectar-se ao servidor");
          }

    }

    function criaDadosEquipe(idEquipe) {
        return { idEquipe };
      }




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
    const [idJogo, setIdJogo] = useState('');
    const [nomeEquipeCriada, setNomeEquipeCriada] = useState('');
    const [IdEquipeCriada, setIdEquipeCriada] = useState('');

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

    async function handlePesquisa () {
        localStorage.setItem('pesquisa', pesquisa);
        history.push("/busca", pesquisa);
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

    function handleScreenFind(){
        history.push('/busca');
    }

    function handleTeamProfile(name, id) {
        localStorage.setItem('nomeEquipe', name);
        localStorage.setItem('idEquipe', id);
        history.push('/perfil-equipe');
    }

    return (
        <div className="equipes-container">
            <header>
                <img src={Logo} alt="HunterProject" onClick={handleHome}></img>
                {/* <span>Bem vindo, {userName}</span> */}

                <div className="input-pesquisa">
                    <input type="text" placeholder="Busque por jogos, equipes..."></input>
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
            <div className="div-principal-btn">
                <div className="div-equipes-btn">
                    <button onClick={handleScreenFind}> <FiSearch size={64} color="#FFF" /> </button>
                    <p onClick={handleScreenFind}>Entrar em equipe</p>
                </div>
                <div className="div-equipes-btn">
                    <button onClick={handleOpenModal}> <FiPlusCircle size={64} color="#FFF" /> </button>
                    <p onClick={handleOpenModal}>Criar equipe</p>
                </div>
                <Modal open={openModal} onClose={handleCloseModal}>
                    {body}
                </Modal>
            </div>
            
            <div className="div-principal">
                {equipes.map(team => (
                    <div key={team.idEquipe.idEquipe} className="div-equipes">
                        <img src={require(`../../assets/${team.idEquipe.fotoEquipe}`)} onClick={() => handleTeamProfile(team.idEquipe.nomeEquipe, team.idEquipe.idEquipe )} alt="Icone Equipe"></img>
                        <p onClick={() => handleTeamProfile(team.idEquipe.nomeEquipe, team.idEquipe.idEquipe)}>{team.idEquipe.nomeEquipe}</p>
                    </div>
                ))}
            </div>



        </div>
    );
}


