import axios from 'axios'

const HOME = 'home'
const API_URL = 'http://localhost:8080'
const HOME_API_URL = `${API_URL}/${HOME}`

class HunterDataService {

    retrieveLogin() {
        //console.log('executed service')
        return axios.get(`${API_URL}/Login`);
    }
    retrieveCadastro() {
        //console.log('executed service')
        return axios.get(`${API_URL}/Cadastro`);
    }

    //  retrieveCourse(name, id) {
    //      //console.log('executed service')
    //      return axios.get(`${HOME_API_URL}/matches/${id}`);
    //  }

    //  deleteCourse(name, id) {
    //      //console.log('executed service')
    //      return axios.delete(`${HOME_API_URL}/matches/${id}`);
    //  }

    //  updateCourse(name, id, match) {
    //      //console.log('executed service')
    //      return axios.put(`${HOME_API_URL}/matches/${id}`);
    //  }

    //  createCourse(name, match) {
    //      //console.log('executed service')
    //      return axios.post(`${HOME_API_URL}/matches/`);
    //  }
}

export default new HunterDataService()