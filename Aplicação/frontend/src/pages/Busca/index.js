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
import api from "../../services/api";
import MenuItem from "@material-ui/core/MenuItem";
import MenuList from "@material-ui/core/MenuList";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";
import Grow from "@material-ui/core/Grow";
import Paper from "@material-ui/core/Paper";
import Popper from "@material-ui/core/Popper";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";

import Modal from "@material-ui/core/Modal";

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

  divOpcional: {
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
  },

  datas: {
    display: "grid",
    gridTemplateColumns: "repeat(3, 1fr)",
    justifyContent: "space-between",
  },

  dataStyle: {
    width: "140px",
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

  hora: {
    gridTemplateColumns: "repeat(2, 1fr)",
    justifyContent: "right",
    alignContent: "right",
  },

  horaStyle: {
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

  comboOpcional: {
    marginLeft: "5px",
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
  select: {
    marginTop: "10px",
    marginBottom: "10px",
    border: "1px solid #ddd",
    borderRadius: "4px",
    height: "48px",
    padding: "0 20px",
    fontSize: "16px",
    color: "#666",
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
  const top = 50 + rand();
  const left = 50 + rand();

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

  const[gamer, setGamer] = useState([]);
  const [nomeJogo, setJogo] = useState("");
  const [posicao, setPosicao] = useState("");
  const [data, setData] = useState("");
  const [hora, setHora] = useState("");

  const [jogoPt, setJogoPt] = useState("");
  const [posicaoPt, setPosicaoPt] = useState("");

  const [nomeJogadorOpPt, setNomeJogadorOpPt] = useState("");
  const [posicaoOpcionalPt, setPosicaoOpcionalPt] = useState("");
  const [horarioPt, setHorarioPt] = useState("");
  const [minutoPt, setMinutoPt] = useState("");

  const [dataPt, setDataPt] = useState("");

  const [anoPt, setAnoPt] = useState("");
  const [mesPt, setMesPt] = useState("");
  const [diaPt, setDiaPt] = useState("");

  const [infracao, setInfracao] = useState("");

  const [idPartida, setIdPartida] = useState("");

  const classes2 = useStyles2();
  const [modalStyle] = React.useState(getModalStyle);
  const [openModal, setOpenModal] = React.useState(false);
  const [games, setGames] = useState("");
  const [equipes, setEquipes] = useState("");
  const [nomeEquipe, setNomeEquipe] = useState([]);
  const [team, setTeam] = useState([]);

  const handleOpenModal = () => {
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

  const history = useHistory("");

  const email = localStorage.getItem("email");
  const id = localStorage.getItem("idGamer");

  React.useEffect(() => {
    async function dadosPerfil() {
      const response = await api.get(`/gamer/${email}/`); //{

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

  async function partidaGamer() {
    api.get(`/partida/gamer/${id}/`).then((response) => {
      const { data = [] } = response || {};
      // verify response.data is an array
      const isArray = Array.isArray(data);
      isArray && setMatches(data);
    })
  }

  useEffect(() => {
    const pesquisa = localStorage.getItem('pesquisa');
    api.get(`/equipejogo/jogo/${pesquisa}/`).then((response) => {
      const { data = [] } = response || {};
      // verify response.data is an array
      const isArray = Array.isArray(data);
      isArray && setEquipes(data);
      
      let dados = response.data;

      let temp = [];

      dados.forEach((item) => {
        getGamer(item.idEquipe.nomeEquipe)
      });
    });
  }, [dataResponse]);

async function getGamer(nomeEquipe){
  api.get(`/equipegamer/equipe/${nomeEquipe}/`).then((response) => {
    const { data = [] } = response || {};
    // verify response.data is an array
    const isArray = Array.isArray(data);
    isArray && setGamer(data);
  });
}
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
  function criaDadosEquipe(fotoGamer){
    return {fotoGamer};
  }

  async function handleDeleteMatch(id) {
    try {
      await api.delete(`/partida/partida/${id}`);

      setMatches(matches.filter((matches) => matches.id !== id));
    } catch (err) {
      alert("Erro ao deletar o partida, tente novamente");
    }
  }

  function handleAgendamento() {
    history.push("/agendamento");
  }

  function handleProfile() {
    history.push("/profile");
  }
  async function handleLogout() {
    try {
      const response = await api.post("/gamer/logoff");
      if (response.status === 200) {
        localStorage.clear();
        history.push("/");
      } else {
        alert("Estamos encontrando problemas na conexão com o servidor");
      }
    } catch (err) {
      alert("Estamos encontrando problemas na conexão com o servidor");
    }
  }

  async function handleConfig() {
    history.push("/config");
  }

  async function handleEquipe() {
    history.push("/equipe");
  }

  return (
    <div className="home-container">
      <header>
        <img src={Logo} alt="HunterProject"></img>

        <div className="input-pesquisa">
          <input type="text" placeholder="Busque por jogos, equipes..."></input>
          <button className="btn-pesquisa">
            <FiSearch size={18} color="#000000" />
          </button>
        </div>

        <Button
          ref={anchorRef}
          aria-controls={open ? "menu-list-grow" : undefined}
          aria-haspopup="true"
          onClick={handleToggle}
          className={classes.button}
        >
          <FiUser size={36} color="#FFFFFF" />
        </Button>
        <Popper
          open={open}
          anchorEl={anchorRef.current}
          role={undefined}
          transition
          disablePortal
        >
          {({ TransitionProps, placement }) => (
            <Grow
              {...TransitionProps}
              style={{
                transformOrigin:
                  placement === "bottom" ? "center top" : "center bottom",
              }}
            >
              <Paper className={classes.root}>
                <ClickAwayListener onClickAway={handleClose}>
                  <MenuList
                    autoFocusItem={open}
                    id="menu-list-grow"
                    onKeyDown={handleListKeyDown}
                  >
                    <MenuItem onClick={handleProfile}>Perfil</MenuItem>
                    <MenuItem onClick={handleEquipe}>Minha Equipe</MenuItem>
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
                
                <div className="current-members-team">
                  {team.map((team) => (
                    <div key={team.idGamer.idGamer} className="membros-imagem">
                      <img src={User} alt="User-Icon"></img>
                      <p>{team.idGamer.nome}</p>
                    </div>
                  ))}
                </div>

                <div>
                  <p className="div-integrantes"> 5/50 </p>
                </div>

                <div>
                  <button className="btnJuntar-se"> Juntar-se</button>
                </div>
              </div>
            ))
            : equipes}
        </div>
      </div>

    </div>
  );
}
