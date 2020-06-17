import React, { useEffect, useState } from "react";
import "./styles.css";
import Logo from "../../assets/testeLogo3.png";
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

export default function Home() {
  const [matches, setMatches] = useState([]);
  const [nome, setNome] = useState("");
  const [idGamer, setIdGamer] = useState("");
  const [nomeJogo, setJogo] = useState("");
  const [posicao, setPosicao] = useState("");
  const [data, setData] = useState("");
  const [hora, setHora] = useState("");
  const [pesquisa, setPesquisa] = useState("");
  const [resultado, setResultado] = useState([]);

  console.log(localStorage);

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
  const [page, setPage] = useState(0);
  const [metadata, setMetadata] = useState([]);

  const handleOpenModal = () => {
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };

  async function envioDadosPartida() {
    setDataPt(anoPt + "-" + mesPt + "-" + diaPt);
    setHorarioPt(horarioPt + ":" + minutoPt + ":00");

    setInfracao(false);

    if (!jogoPt || !idGamer || !posicaoPt || !horarioPt || !dataPt) {
      alert("Preencha os dados necessários");
    } else {
      const data = {
        idJogo: {
          idJogo: jogoPt,
        },
        idGamer: {
          idGamer: idGamer,
        },
        idPosicao: {
          idPosicao: posicaoPt,
        },
        data: dataPt,
        hora: horarioPt,
      };
      try {
        const response = await api.post(`/partida/`, data); //{
        if (response.status === 201) {
          alert("Partida Criada com Sucesso!");
          let dados = response.data;

          let temp = [];

          dados.forEach((item) => {
            temp.push(criaDadosPt(item.idPartida));
          });

          setIdPartida(temp[0].idPartida);

          console.log(idPartida);
          if (!nomeJogadorOpPt || !posicaoOpcionalPt) {
            return;
          } else {
            //   envioDadosPartidaAgregados(nomeJogadorOpPt, posicaoOpcionalPt);
            return;
          }
        } else {
          alert("Erro ao criar partida");
        }
      } catch (err) {
        alert("Erro ao criar partida ou conectar-se ao servidor");
      }
    }
  }
  function criaDadosPt(idPartida) {
    return { idPartida };
  }

  const body = (
    <div style={modalStyle} className={classes2.paper}>
      <h2>Organizar uma partida</h2>
      <div className={classes2.divAbove}>
        <p>Selecione um jogo :</p>

        <select
          className={classes2.select}
          onChange={(e) => setJogoPt(e.target.value)}
        >
          <option value="0">Selecione o jogo</option>
          <option value="1">Counter-Strike: Global Offensive</option>
          <option value="2">Valorant</option>
          <option value="3">League of Legends</option>
          <option value="4">Fortnite</option>
          <option value="5">DOTA 2</option>
          <option value="6">Call of Duty: Warzone</option>
          <option value="7">PlayerUnkown's Battlegrounds</option>
        </select>
        <p>Selecione uma posição :</p>
        <select
          className={classes2.select}
          onChange={(e) => setPosicaoPt(e.target.value)}
        >
          <option value="0">Selecione a sua posição</option>
          <option value="2">Atirador</option>
          <option value="3">Suporte</option>
          <option value="4">Jungle</option>
          <option value="5">Top</option>
          <option value="6">Mid</option>
          <option value="7">Entry Fragger</option>
          <option value="8">Lurker</option>
          <option value="9">Capitão</option>
          <option value="10">Sniper</option>
        </select>
        <p>Outros jogadores :</p>
        <div className={classes2.divOpcional}>
          <input
            placeholder="Nome do jogador(Opicional)"
            className={classes2.input}
            onChange={(e) => setNomeJogadorOpPt(e.target.value)}
          />
          <div className={classes2.comboOpcional}>
            <select
              className={classes2.select}
              onChange={(e) => setPosicaoOpcionalPt(e.target.value)}
            >
              <option value="0">Posição</option>
              <option value="2">Atirador</option>
              <option value="3">Suporte</option>
              <option value="4">Jungle</option>
              <option value="5">Top</option>
              <option value="6">Mid</option>
              <option value="7">Entry Fragger</option>
              <option value="8">Lurker</option>
              <option value="9">Capitão</option>
              <option value="10">Sniper</option>
            </select>
          </div>
        </div>
        <p>Selecione um horário :</p>
        <div className={classes2.hora}>
          <input
            placeholder="Hora (ex: 12)"
            className={classes2.horaStyle}
            onChange={(e) => setHorarioPt(e.target.value)}
          />

          <input
            placeholder="Min. (ex: 05)"
            className={classes2.horaStyle}
            onChange={(e) => setMinutoPt(e.target.value)}
          />
        </div>
        <p>Selecione uma data :</p>
        <div className={classes2.datas}>
          <input
            className={classes2.dataStyle}
            placeholder="Dia (ex: 01)"
            onChange={(e) => setDiaPt(e.target.value)}
          />
          <input
            className={classes2.dataStyle}
            placeholder="Mês (ex: 01)"
            onChange={(e) => setMesPt(e.target.value)}
          />
          <input
            className={classes2.dataStyle}
            placeholder="Ano(ex: 2020)"
            onChange={(e) => setAnoPt(e.target.value)}
          />
        </div>
        <p style={{ width: "300px" }}>
          <button className={classes2.buttonClose} onClick={handleCloseModal}>
            Fechar
          </button>
          <button className={classes2.buttonCreate} onClick={envioDadosPartida}>
            Criar
          </button>
        </p>
      </div>
    </div>
  );

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
    }
    dadosPerfil();
  }, [email]);

  useEffect(() => {
    api.get(`/partida/gamer/depois/${id}/`).then((response) => {
      const { data = [] } = response || {};
      // verify response.data is an array
      const isArray = Array.isArray(data);
      isArray && setMatches(data);
    });
  }, [id]);

  async function handlePesquisa () {
      localStorage.setItem('pesquisa', pesquisa);
      history.push("/busca", pesquisa);
    }
  

  function criaDados(idGamer, nome) {
    return { idGamer, nome };
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
    history.push("/equipes");
  }

  return (
    <div className="home-container">
      <header>
        <img src={Logo} alt="HunterProject"></img>

        <div className="input-pesquisa">
          <input
            type="text"
            placeholder="Busque por jogos, equipes..."
            onChange={(e) => setPesquisa(e.target.value)}
          ></input>
          <button className="btn-pesquisa" onClick={handlePesquisa}>
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

      <p className="bem-vindo">Bem vindo, {nome}.</p>

      <h1>Partidas Agendadas:</h1>

      <ul className="partidas">
        {matches.map((match) => (
          <li key={match.idPartida}>
            <strong>{match.idJogo.nomeJogo}</strong>
            <p>Posição : {match.idPosicao.posicao}</p>

            <strong>Data: </strong>
            <p>
              <b>{match.data}</b>
            </p>

            <strong>Horário: </strong>
            <p>
              <b>{match.hora}</b>
            </p>

            {/* <strong>Duração Estimada: </strong>
            <p>1 Hora</p> */}

            <button type="button">
              {" "}
              <FiTrash2
                size={20}
                color="#a8a8b3"
                onClick={() => handleDeleteMatch(match.idPartida)}
              />
            </button>
          </li>
        ))}

        <li>
          <strong>Adicionar Partida</strong>

          <button className="btn-adicionar" onClick={handleOpenModal}>
            {" "}
            <FiPlusCircle size={64} color="#000000" />
          </button>
          <Modal open={openModal} onClose={handleCloseModal}>
            {body}
          </Modal>
        </li>
      </ul>
    </div>
  );
}