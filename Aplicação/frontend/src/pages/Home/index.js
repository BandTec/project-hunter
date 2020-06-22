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
import apiGamer from "../../services/apiGamer";
import apiPartida from "../../services/apiPartida";
import MenuItem from "@material-ui/core/MenuItem";
import MenuList from "@material-ui/core/MenuList";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";
import Grow from "@material-ui/core/Grow";
import Paper from "@material-ui/core/Paper";
import Popper from "@material-ui/core/Popper";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
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

function getModalStyle() {
  const top = 50;
  const left = 50;

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
  const [openAlerta, setOpenAlerta] = React.useState(false);
  const [dadosAlerta, setDadosAlerta] = useState('');

  console.log(localStorage);

  const [jogoPt, setJogoPt] = useState("");
  const [posicaoPt, setPosicaoPt] = useState("");
  const [jogadorOpPt1, setJogadorOpPt1] = useState("");
  const [posicaoOpcionalPt1, setPosicaoOpcionalPt1] = useState("");
  const [jogadorOpPt2, setJogadorOpPt2] = useState("");
  const [posicaoOpcionalPt2, setPosicaoOpcionalPt2] = useState("");
  const [jogadorOpPt3, setJogadorOpPt3] = useState("");
  const [posicaoOpcionalPt3, setPosicaoOpcionalPt3] = useState("");
  const [jogadorOpPt4, setJogadorOpPt4] = useState("");
  const [posicaoOpcionalPt4, setPosicaoOpcionalPt4] = useState("");
  const [idsOutrosGamers, setIdsOutrosGamers] = useState([]);
  const [idsPosicaoOutros, setIdsPosicaoOutros] = useState([]);
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

  async function envioDadosPartida() {
    

    setInfracao(false);
    console.log("jogoPt", jogoPt);
    console.log("idGamer", idGamer);
    console.log("posicaoPt", posicaoPt);
    console.log("hoararioPt", horarioPt);
    console.log("dataPt", dataPt);

    if (!jogoPt || !posicaoPt || !horarioPt || !minutoPt || !anoPt || !mesPt || !diaPt) {
      handleOpenAlert("Preencha os dados necessários");
    } else {
      if (jogadorOpPt1) {
        const response = await apiGamer.get(`/gamer/usuario/${jogadorOpPt1}`);

        let dados = response.data;
        console.log("jogador1", dados);

        if (!dados) {
          handleOpenAlert("Jogador opcional 1 não encontrado");
        }
      }
      if (jogadorOpPt2) {
        const response = await apiGamer.get(`/gamer/usuario/${jogadorOpPt2}`);

        let dados = response.data;

        if (!dados) {
          handleOpenAlert("Jogador opcional 2 não encontrado");
        }
      }
      if (jogadorOpPt3) {
        const response = await apiGamer.get(`/gamer/usuario/${jogadorOpPt3}`);

        let dados = response.data;

        if (!dados) {
          handleOpenAlert("Jogador opcional 3 não encontrado");
        }
      }
      if (jogadorOpPt4) {
        const response = await apiGamer.get(`/gamer/usuario/${jogadorOpPt4}`);

        let dados = response.data;

        if (!dados) {
          handleOpenAlert("Jogador opcional 4 não encontrado");
        }
      }
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
        data: anoPt + "-" + mesPt + "-" + diaPt,
        hora: horarioPt + ":" + minutoPt + ":00",
      };
      console.log("data", data);
      try {
        const response = await apiPartida.post(`/partida`, data); //{
        if (response.status === 201) {
          let dados = response.data;

          let temp = [];

          temp.push(criaDadosPt(dados.idPartida));

          setIdPartida(temp[0].idPartida);
          if (jogadorOpPt1) {
            const response = await apiPartida.post(
              `/partida/${jogadorOpPt1}/${posicaoOpcionalPt1}/${dados.idPartida}`,
              data
            );
            console.log("dados1", response);
            if (!response) {
              handleOpenAlert("Falha ao adicionar jogador opcional 1");
            }
          }
          if (jogadorOpPt2) {
            const response = await apiPartida.post(
              `/partida/${jogadorOpPt2}/${posicaoOpcionalPt2}/${dados.idPartida}`,
              data
            );
            if (!response) {
              handleOpenAlert("Falha ao adicionar jogador opcional 2");
            }
          }
          if (jogadorOpPt3) {
            const response = await apiPartida.post(
              `/partida/${jogadorOpPt3}/${posicaoOpcionalPt3}/${dados.idPartida}`,
              data
            );
            if (!response) {
              handleOpenAlert("Falha ao adicionar jogador opcional 3");
            }
          }
          if (jogadorOpPt4) {
            const response = await apiPartida.post(
              `/partida/${jogadorOpPt4}/${posicaoOpcionalPt4}/${dados.idPartida}`,
              data
            );
            if (!response) {
              return handleOpenAlert("Falha ao adicionar jogador opcional 4");
            }
          }
          handleOpenAlert("Partida Criada com Sucesso!");

          console.log("dados", dados.idPartida);

          handleCloseModal();

          console.log(idPartida);
        } else {
          handleOpenAlert("Erro ao criar partida");
        }
      } catch (err) {
        handleOpenAlert("Erro ao criar partida ou conectar-se ao servidor");
      } finally {
        window.location.reload(false);
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
        <p>Jogador opcional 1 :</p>
        <div className={classes2.divOpcional}>
          <input
            placeholder="Nome de usuário"
            className={classes2.input}
            onChange={(e) => setJogadorOpPt1(e.target.value)}
          />
          <div className={classes2.comboOpcional}>
            <select
              className={classes2.select}
              onChange={(e) => setPosicaoOpcionalPt1(e.target.value)}
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

        <p>Jogador opcional 2 :</p>
        <div className={classes2.divOpcional}>
          <input
            placeholder="Nome de usuário"
            className={classes2.input}
            onChange={(e) => setJogadorOpPt2(e.target.value)}
          />
          <div className={classes2.comboOpcional}>
            <select
              className={classes2.select}
              onChange={(e) => setPosicaoOpcionalPt2(e.target.value)}
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

        <p>Jogador opcional 3 :</p>
        <div className={classes2.divOpcional}>
          <input
            placeholder="Nome de usuário"
            className={classes2.input}
            onChange={(e) => setJogadorOpPt3(e.target.value)}
          />
          <div className={classes2.comboOpcional}>
            <select
              className={classes2.select}
              onChange={(e) => setPosicaoOpcionalPt3(e.target.value)}
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

        <p>Jogador opcional 4 :</p>
        <div className={classes2.divOpcional}>
          <input
            placeholder="Nome de usuário"
            className={classes2.input}
            onChange={(e) => setJogadorOpPt4(e.target.value)}
          />
          <div className={classes2.comboOpcional}>
            <select
              className={classes2.select}
              onChange={(e) => setPosicaoOpcionalPt4(e.target.value)}
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
            placeholder="Hora"
            className={classes2.horaStyle}
            onChange={(e) => setHorarioPt(e.target.value)}
          />

          <input
            placeholder="Min."
            className={classes2.horaStyle}
            onChange={(e) => setMinutoPt(e.target.value)}
          />
        </div>
        <p>Selecione uma data :</p>
        <div className={classes2.datas}>
          <input
            className={classes2.dataStyle}
            placeholder="Dia"
            onChange={(e) => setDiaPt(e.target.value)}
          />
          <input
            className={classes2.dataStyle}
            placeholder="Mês"
            onChange={(e) => setMesPt(e.target.value)}
          />
          <input
            className={classes2.dataStyle}
            placeholder="Ano"
            onChange={(e) => setAnoPt(e.target.value)}
          />
        </div>
        <center style={{ marginRight: "-160%" }}>
          <p className="btn-modal" style={{ width: "300px" }}>
            <button className={classes2.buttonClose} onClick={handleCloseModal}>
              Fechar
            </button>
            <button
              className={classes2.buttonCreate}
              onClick={envioDadosPartida}
            >
              Criar
            </button>
          </p>
        </center>
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
    }
    dadosPerfil();
  }, [email]);

  useEffect(() => {
    apiPartida.get(`/partida/gamer/depois/${id}/`).then((response) => {
      const { data = [] } = response || {};
      // verify response.data is an array
      const isArray = Array.isArray(data);
      isArray && setMatches(data);
    });
  }, [id]);

  async function handlePesquisa() {
    localStorage.setItem("pesquisa", pesquisa);
    history.push("/busca", pesquisa);
  }

  function criaDados(idGamer, nome) {
    return { idGamer, nome };
  }

  async function handleDeleteMatch(id) {
    try {
      await apiPartida.delete(`/partida/partida/${id}`);

      setMatches(matches.filter((matches) => matches.id !== id));
      handleOpenAlert("Partida deletada com sucesso!");
    } catch (err) {
      handleOpenAlert("Erro ao deletar o partida, tente novamente");
    } finally {
      window.location.reload(false);
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
            placeholder="Busque por jogos ou equipes..."
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
              <b>{match.data.split("-").reverse().join("/")}</b>
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
