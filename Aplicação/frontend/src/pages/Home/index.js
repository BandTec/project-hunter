import React, { useEffect, useState } from 'react';
import './styles.css'
import Logo from "../../assets/testeLogo3.png";
import { FiArrowLeft, FiPower, FiTrash2, FiSearch, FiUser, FiPlusCircle } from 'react-icons/fi'
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
    button:{
        height: "60px",
        width: "60px",
        borderRadius: "100%",
        border: "1px solid #dcdce6",
        background: "transparent",
        marginLeft: "300px",
        transition : "border-color 0.2s",
    }
  }));

export default function Home(){

    const [nome, setNome] = useState('');
    const [idGamer, setIdGamer] = useState('');
    const [nomeJogo, setJogo] = useState('');
    const [posicao, setPosicao] = useState('');
    const [data, setData] = useState('');
    const [hora, setHora] = useState('');

    console.log(localStorage);    

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
    
    const email = localStorage.getItem('email');
    const id = localStorage.getItem('idGamer');
    
 
     React.useEffect(() => {
      async function dadosPerfil() {
        const response = await api.get(`/gamer/${email}/`); //{
 
      
     let dados = response.data;

     let temp = [];

     dados.forEach( item => {
       temp.push(
         criaDados(
           item.idGamer,
           item.nome
         )
       );
     });
     
     setNome(temp[0].nome);
     setIdGamer(temp[0].idGamer);
     localStorage.setItem('nome', temp[0].nome);
     localStorage.setItem('idGamer', temp[0].idGamer);
   }
   dadosPerfil();
 }, []);




  useEffect(() => {
    api.get(`/partida/gamer/${id}/`
    
    ).then(response =>{
        setMatches(response.data);
    })
}, [id]);


    React.useEffect(() => {
      async function dadosPartida() {
        const responsePartida = await api.get(`/partida/gamer/${id}/`);    

     let dadosPartida = responsePartida.data;

     let tempPartida = [];

     dadosPartida.forEach( item => {
       tempPartida.push(
         criaDadosPartida(
           item.idJogo.nomeJogo,
           item.idPosicao.posicao,
           item.data,
           item.hora
         )
       );
      });
console.log(tempPartida[0]);
     

     setData(tempPartida[0].data);
     setHora(tempPartida[0].hora);
     setJogo(tempPartida[0].nomeJogo);
     setPosicao(tempPartida[0].posicao);
     

     localStorage.setItem('data', tempPartida[0].data);
     localStorage.setItem('hora', tempPartida[0].hora);
     localStorage.setItem('nomeJogo', tempPartida[0].nomeJogo);
     localStorage.setItem('posicao', tempPartida[0].posicao);
   }

   dadosPartida();
 }, []);
 
 function criaDados(idGamer, nome){
   return {idGamer, nome}
 }

 function criaDadosPartida(nomeJogo, posicao, data, hora){
  return {data, hora,nomeJogo, posicao}
}

    // async function handleDeleteMatch(id){
    //     try{
    //         await api.delete(`matches/${id}`, {
    //             headers: {
    //                 Authorization: userId, 
    //             }
    //         });

    //         setMatches(matches.filter(matches => matches.id !== id));
    //     }catch(err){
    //         alert('Erro ao deletar o partida, tente novamente');
    //     }
    // }

    function handleProfile(){
      
      history.push('/profile');
  }
    async function handleLogout(){
        try{
          const response = await api.post('/usuario/logoff');
         if (response.status === 200){
          localStorage.clear();
          history.push('/');
         }else{
          alert('Estamos encontrando problemas na conexão com o servidor'); 
         }
        }catch(err){
          alert('Estamos encontrando problemas na conexão com o servidor');
        }
        
    }

    async function handleConfig(){
      history.push('/config');
    }


    return(
        <div className= "home-container">
            
            <header>
            <img src = {Logo} alt="HunterProject"></img>
            {/* <span>Bem vindo, {userName}</span> */}
            
           <div className="input-pesquisa">
             <input type="text" placeholder="Busque por jogos, equipes..."></input>
             <button className="btn-pesquisa"><FiSearch size={18} color="#000000"/></button>
           </div>
           
            
        <Button
          ref={anchorRef}
          aria-controls={open ? 'menu-list-grow' : undefined}
          aria-haspopup="true"
          onClick={handleToggle}
          className={classes.button}
          
        >
          <FiUser size={36} color="#FFFFFF"/>
        </Button>
        <Popper open={open} anchorEl={anchorRef.current} role={undefined} transition disablePortal >
          {({ TransitionProps, placement }) => (
            <Grow
              {...TransitionProps}
              style={{ transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom'}}
            >
              <Paper className={classes.root} >
                <ClickAwayListener onClickAway={handleClose}>
                  <MenuList autoFocusItem={open} id="menu-list-grow" onKeyDown={handleListKeyDown}>
                    <MenuItem onClick={handleProfile}>Perfil</MenuItem>
                    <MenuItem onClick={handleClose}>Equipes</MenuItem>
                    <MenuItem onClick={handleClose}>Agendamentos</MenuItem>
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

            <h1>Hoje:</h1>

            <ul className="matches">

                 {matches.map(match => ( 
                    <li key={match.idPartida}>
                    <strong>{match.idJogo.nomeJogo}</strong>
                    <p>Posição : {match.idPosicao.posicao}</p>

                    <strong>Horário: </strong>
                    <p><b>{match.hora}</b></p>

                    <strong>Duração Estimada: </strong>
                    <p>1 Hora</p>

                    <button type="button"> <FiTrash2 size={20} color="#a8a8b3"/></button>
                </li> 
                 ))}
            </ul>
      
        </div>   
    );
}