import React, { useEffect, useState } from "react";
import "./styles.css";
import Logo from "../../assets/testeLogo3.png";
import User from "../../assets/default-user.png";
import {
  FiArrowLeft,
  FiPower,
  FiTrash2,
  FiSearch,
  FiUser,
  FiPlusCircle,

} from "react-icons/fi";
import { Link, useHistory } from "react-router-dom";
import apiEquipe from "../../services/apiEquipe";
import apiGamer from "../../services/apiGamer";
import MenuItem from "@material-ui/core/MenuItem";
import MenuList from "@material-ui/core/MenuList";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";
import Grow from "@material-ui/core/Grow";
import Paper from "@material-ui/core/Paper";
import Popper from "@material-ui/core/Popper";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Modal from "@material-ui/core/Modal";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
  },
  paper: {
    marginRight: theme.spacing(2),
    width: "150px",
    height: "100px",
  },
  button: {
    height: "60px",
    width: "60px",
    borderRadius: "100%",
    border: "1px solid #dcdce6",
    background: "transparent",
    marginLeft: "300px",
    transition: "border-color 0.2s",
  },
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
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
}

export default function Busca({ dataResponse }) {
  console.log(dataResponse);
  const [matches, setMatches] = useState([]);
  const [nome, setNome] = useState("");
  const [idGamer, setIdGamer] = useState("");
  const [numUsers, setNumUsers] = useState([]);
  const [pesquisa, setPesquisa] = useState("");
  const [gamer, setGamer] = useState([]);
  const classes2 = useStyles2();
  const [modalStyle] = React.useState(getModalStyle);
  const [openModal, setOpenModal] = React.useState(false);
  const [equipes, setEquipes] = useState("");
  const [nomeEquipe, setNomeEquipe] = useState([]);
  const [openAlerta, setOpenAlerta] = React.useState(false);
  const [dadosAlerta, setDadosAlerta] = useState('');

  function handleOpenAlert (resposta) {
    setDadosAlerta(resposta);
    setOpenAlerta(true);
  };

  const handleCloseAlert = () => {
    setOpenAlerta(false);
  };

  function handleOpenModal(dados) {
    localStorage.setItem('idEquipe', dados);
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };



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
    if (event.key === "Tab") {
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


  const body = (
    <div style={modalStyle} className={classes2.paper}>
      <center><h2>Solicitação</h2></center>

      <div className={classes2.divAbove}>

        <p>Deseja se juntar a equipe?</p>

        {/* <p>Escolha sua imagem :</p>
            <div className={classes2.imagem}>
                <label id="thumbnail"
                    style={{ backgroundImage: `url(${preview})` }}
                    className={thumbnail ? 'has-thumbnail' : ''}
                >
                    <input type="file" onChange={event => setThumbnail(event.target.files[0])} />
                    <img src={camera} alt="Select your photo"></img>
                </label>
            </div> */}

        <p style={{ width: "300px" }}>
          <button className={classes2.buttonClose} onClick={handleCloseModal}>
            Não
          </button>
          <button className={classes2.buttonCreate} onClick={envioDadosEntrarEquipe}>
            Sim
          </button>
        </p>
      </div>
    </div>
  );


  async function envioDadosEntrarEquipe() {
    const data = {
      idEquipe: {
        idEquipe: localStorage.getItem('idEquipe')
      },
      idGamer: {
        idGamer: localStorage.getItem("idGamer")
      },
      idStatus: {
        idStatus: 3
      },
      capitao: false
    };

    try {
      const response = await apiEquipe.post(`/equipegamer/`, data); //{
      if (response.status === 201) {

        handleOpenAlert('Pedido enviado ao capitão da equipe');
        handleCloseModal();
        return;

      } else {
        handleOpenAlert("Erro ao enviar solicitação");
      }
    } catch (err) {
      handleOpenAlert("Erro ao enviar solicitação ou conectar-se ao servidor");
    }

  }




  const history = useHistory("");

  const email = localStorage.getItem("email");
  const id = localStorage.getItem("idGamer");

  React.useEffect(() => {
    async function dadosPerfil() {
      const response = await apiGamer.get(`/gamer/${email}/`); //{

      let dados = response.data;

      let temp = [];

      dados.forEach((item) => {
        temp.push(criaDados(item.idGamer, item.nome));
      });

      setNome(temp[0].nome);
      setIdGamer(temp[0].idGamer);
      localStorage.setItem("nome", temp[0].nome);
      localStorage.setItem("idGamer", temp[0].idGamer);
      //pesquisa();
      //partidaGamer();
    }
    dadosPerfil();
  }, [email]);


  useEffect(() => {
    const pesquisa = localStorage.getItem('pesquisa');
    apiEquipe.get(`/equipejogo/jogo/${pesquisa}/`).then((response) => {
      const { data = [] } = response || {};
      // verify response.data is an array
      const isArray = Array.isArray(data);
      isArray && setEquipes(data);

      data.forEach((item) =>
        getNumEquipe(item.idEquipe.nomeEquipe)
      );


      if (equipes == '' || equipes == null) {
        apiEquipe.get(`/equipegamer/equipe/${pesquisa}/`).then((response) => {
          const { data2 = [] } = response || {};
          // verify response.data is an array
          const isArray2 = Array.isArray(data2);
          isArray2 && setEquipes(data);

        });
      }
    });
  }, [dataResponse]);

  // useEffect(() => {
  //   dataResponse.forEach((values) => {
  //     api.get(`/jogo/${values.id}/`).then((response) => {
  //       const { data = [] } = response || {};
  //       const isArray = Array.isArray(data);
  //       isArray && setGames(data);
  //     });
  //   });
  // }, [dataResponse]);

  // useEffect(() => {
  //   data.forEach((values) => {
  //     api.get(`/equipe/${values.id}`).then((response) => {
  //       const { data = [] } = response || {};
  //       const isArray = Array.isArray(data);
  //       isArray && setEquipes(data);
  //     });
  //   });
  // }, [data, dataResponse]);

  // useEffect(() => {
  //   setNomeEquipe(localStorage.getItem("nomeEquipe"));
  //   api.get(`/equipegamer/equipe/${nomeEquipe}/`).then((response) => {
  //     setTeam(response.data);
  //   });
  // }, [nomeEquipe, team]);

  function criaDados(idGamer, nome) {
    return { idGamer, nome };
  }
  function criaDadosEquipe(fotoGamer) {
    return { fotoGamer };
  }

  let cont = 0;

  async function getNumEquipe(dados) {

    apiEquipe.get(`/equipegamer/equipe/qtd/${dados}/`).then((response) => {
      //const { data = [] } = response || {};
      //const isArray = Array.isArray(data);
      //isArray && setNumUsers(data);
      const data = response.data;
      if (response.status === 200) {
        //setNumUsers(data);
        numUsers.push(data);
        console.log(numUsers);
      }
      else{
        //setNumUsers(0);
        numUsers.push(0)
        console.log(numUsers);
      }

    });
  }

  function handleProfile() {
    history.push("/profile");
  }
  async function handleLogout() {
    try {
      const response = await apiGamer.post("/gamer/logoff");
      if (response.status === 200) {
        localStorage.clear();
        history.push("/");
      } else {
        handleOpenAlert("Estamos encontrando problemas na conexão com o servidor");
      }
    } catch (err) {
      handleOpenAlert("Estamos encontrando problemas na conexão com o servidor");
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

  async function handlePesquisa() {
    localStorage.setItem('pesquisa', pesquisa);
    window.location.reload(false);

  }

  return (
    <div className="home-container">
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

      <div className="div-principal">
        <div className="div-equipes">
          <p>Equipes</p>
        </div>

        <div className="div-equipes-busca">

          {equipes
            ? equipes.map((equipe) => (

              //() => getNumEquipe(equipe.idEquipe.nomeEquipe),

              <div className="div-organizacao-equipes">
                <img src={
                  equipe.idEquipe.fotoEquipe
                    ? require(`../../assets/${equipe.idEquipe.fotoEquipe}`)
                    : equipe.fotoEquipe
                }
                  alt=""
                  className="jogo-imagem"
                ></img>

                <p className="jogo-nome">{equipe.idEquipe.nomeEquipe}</p>

                <div>


                  <p className="div-integrantes" > Membros:<p style={{ color: '#00FF00' }}>{numUsers[cont], cont++}</p><p style={{ marginLeft: "-80px" }}>/  50</p> </p>
                </div>

                <div className="current-members-team">
                  {/* {gamer.map((player) => (
                    <div key={player.idGamer.idGamer} className="membros-imagem">
                      <img src={player.idGamer.fotoGamer} alt="User-Icon"></img>
                      
                    </div>
                  ))} */}
                </div>



                <div>
                  <button className="btnJuntar-se" onClick={() => handleOpenModal(equipe.idEquipe.idEquipe)} > Juntar-se</button>
                  <Modal open={openModal} onClose={handleCloseModal}>

                    {body}
                  </Modal>

                </div>
              </div>
            

            ))
            : equipes}
        </div>
      </div>
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
