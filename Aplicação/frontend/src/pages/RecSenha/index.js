import React, { useState } from 'react';
import './styles.css'
import Logo from "../../assets/testeLogo3.svg";
import { FiArrowLeft } from 'react-icons/fi'
import { Link, useHistory } from 'react-router-dom';
import apiEmail from '../../services/apiEmail';
import { waitForElementToBeRemoved } from '@testing-library/react';

import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

export default function Register() {

  const [openAlerta, setOpenAlerta] = React.useState(false);
  const [dadosAlerta, setDadosAlerta] = useState('');
  
  function handleOpenAlert (resposta) {
    setDadosAlerta(resposta);
    setOpenAlerta(true);
  };

  const handleCloseAlert = () => {
    setOpenAlerta(false);
  };

    const [email, setEmail] = useState('');

    const history = useHistory();

    function handleVoltar(){
        history.push('/')
    }

    async function handlePassRecover(e) {
        e.preventDefault();

    if (!email) {
      handleOpenAlert('Por favor, digite seu email.');
    } else {
      try {
        
          const response = await apiEmail.get(`/email/${email}/`);
          console.log(response.status);
          if (response.status === 200){
            handleOpenAlert('As instruções de recuperação de senha enviadas para seu email!');
            history.push("/");
          }else{
            handleOpenAlert('Email inválido');
          }
      } catch (err) {
        handleOpenAlert('Email inválido');
        
      }
    }
  };

    return (
        <div className="rec-senha-container">
            <header>
                <img src={Logo} alt="HunterProject" onClick = {handleVoltar}></img>
            </header>
            <div >
                <form onSubmit = {handlePassRecover}>
                    <h1>Digite seu email:</h1>
                    <input type="email" value={email} onChange={e => setEmail(e.target.value)} />
                    <div>
                        <button className="btn env" type="submit">Enviar</button>
                    </div>
                </form>
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