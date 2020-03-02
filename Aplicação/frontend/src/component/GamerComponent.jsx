import React, { Component } from 'react'
import HunterDataService from '../service/HunterDataService';

const HOME = 'home'

class ListHunterComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            matches: [],
            message: null
        }
        this.deleteMatchClicked = this.deleteMatchClicked.bind(this)
        this.updateMatchClicked = this.updateMatchClicked.bind(this)
        this.addMatchClicked = this.addMatchClicked.bind(this)
        this.refreshMatches = this.refreshMatch.bind(this)
    }

    componentDidMount() {
        this.refreshMatches();
    }

    refreshMatches() {
        CourseDataService.retrieveHome()//HARDCODED
            .then(
                response => {
                    //console.log(response);
                    this.setState({ matches: response.data })
                }
            )
    }

    deleteMatchClicked(id) {
        HunterDataService.deleteMatch(HOME, id)
            .then(
                response => {
                    this.setState({ message: `Delete of match ${id} Successful` })
                    this.refreshMatches()
                }
            )

    }

    addMatchClicked() {
        this.props.history.push(`/matches/-1`)
    }

    updateMatchClicked(id) {
        console.log('update ' + id)
        this.props.history.push(`/matches/${id}`)
    }

    render() {
        console.log('render')
        return (
            <div className="container">
                <h3>All Courses</h3>
                {this.state.message && <div class="alert alert-success">{this.state.message}</div>}
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Description</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.matches.map(
                                    course =>
                                        <tr key={matches.id}>
                                            <td>{matches.id}</td>
                                            <td>{matches.description}</td>
                                            <td><button className="btn btn-success" onClick={() => this.updateMatchClicked(match.id)}>Update</button></td>
                                            <td><button className="btn btn-warning" onClick={() => this.deleteMatchClicked(match.id)}>Delete</button></td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addMatchClicked}>Add</button>
                    </div>
                </div>
            </div>
        )
    }
}

export default ListCoursesComponent