import React, { useState } from 'react';
import "./styles.css";
import logo from '../../assets/testeLogo3.svg';
import '../../routes.js';
import apiGamer from '../../services/apiGamer';
import apiHunter from '../../services/apiHunter';
import { login } from "../../auth";
import { Link, useHistory } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle'; 

export default function Login(){ 

  const [openAlerta, setOpenAlerta] = React.useState(false);
  const [dadosAlerta, setDadosAlerta] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const history = useHistory('');

  function handleOpenAlert (resposta) {
    setDadosAlerta(resposta);
    setOpenAlerta(true);
  };

  const handleCloseAlert = () => {
    setOpenAlerta(false);
  };

  function chamaCadastro(){
  
    history.push('/cadastro');
  }


  async function handleSignIn(e){
    e.preventDefault();

    
    if (!email || !password) {
      handleOpenAlert("Preencha seu e-mail e sua senha para continuar!");
    } else {
      try {
        
          const response = await apiHunter.get(`/gamer/${email}/${password}/`);
          console.log(response.status);
          if (response.status === 200){
          
          login("@hunter-token");
          
          localStorage.setItem('email', email);
          
          history.push("/home");
          
          }else{
            handleOpenAlert("Email ou senha inválidos");
          }
      } catch (err) {
        handleOpenAlert("Email ou senha inválidos");
        
      }
    }
  };

  return (

      <div className="login-container">
        <form onSubmit={handleSignIn}>
          <img src={logo} alt="Hunter"></img>
          <p className = "campo" >Email:</p>
          <br></br>
          <input onChange={e => setEmail( e.target.value)} />
          <p className = "campo">Senha:</p>
          <br></br>
          <input type="password" onChange={e => setPassword(e.target.value)} />
          <Link to='/recuperar-senha'><a href=""> Esqueceu sua senha?</a></Link>
          <div className='login-container formBtn'>
          
           <div>
          <button className="login-container btn Cad"  type="button" onClick={chamaCadastro}>Cadastrar</button>
            </div>
          <button className="login-container btn Log" type="submit" >Enviar</button>
            

          </div>
        </form>

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

    );

  }