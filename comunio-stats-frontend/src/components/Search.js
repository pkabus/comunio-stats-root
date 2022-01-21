import React, { Component } from 'react'
import axios from 'axios'
import { backend_properties } from '../backend_properties.js'
import { Link } from 'react-router-dom'
import { ListGroup } from 'react-bootstrap'

export class Search extends Component {

    constructor(props) {
        super(props)

        this.state = {
            players: [],
            clubs: [],
            errorMsg: ''
        }

        this.searchInputOnChange = this.searchInputOnChange.bind(this)
    }

    componentDidMount() {
        

    }

    searchInputOnChange(input) {
        const { backend_host, backend_port } = backend_properties

        if (input.target.value.length < 3) {
            return
        }

        axios.get(`http://${backend_host}:${backend_port}/players/q?name=${input.target.value}`)
            .then(response => {
                console.log(`New Players for ${input.target.value}: ` + JSON.stringify(response.data._embedded.player_dto_list))
                this.setState({
                    players: response.data._embedded.player_dto_list.length > 0 ? response.data._embedded.player_dto_list : ''
                })
            })
            .catch(error => {
                this.setState({
                    errorMsg: error.message
                })
            })

        axios.get(`http://${backend_host}:${backend_port}/clubs/q?name=${input.target.value}`)
            .then(response => {
                console.log(`New Clubs for ${input.target.value}: ` + JSON.stringify(response.data._embedded.club_dto_list))
                this.setState({
                    clubs: response.data._embedded.club_dto_list.length > 0 ? response.data._embedded.club_dto_list : ''
                })
            })
            .catch(error => {
                this.setState({
                    errorMsg: error.message
                })
            })
    }


    render() {
        const { players, clubs } = this.state
        return (
            <div>

                <div className="input-group mb-3">
                    <div className="input-group-prepend">
                        <label className="input-group-text" htmlFor="searchQuery">Deine Moam</label>
                    </div>
                    <input type="text" className="form-control" placeholder="Search" aria-label="Username" aria-describedby="addon-wrapping" onChange={this.searchInputOnChange} />
                </div>




                <h3>Players</h3>
                <ListGroup varian="flush">
                    {
                        players.length ?
                            players.map(player =>
                                <ListGroup.Item key={player.id}>
                                    <Link to={`/players/${player.id}`}>{player.name}</Link>
                                </ListGroup.Item>)
                            : null
                    }
                </ListGroup>

                <h3>Clubs</h3>
                <ListGroup varian="flush">
                    {
                        clubs.length ?
                            clubs.map(club =>
                                <ListGroup.Item key={club.id}>
                                    <Link to={`/clubs/${club.id}`}>{club.name}</Link>
                                </ListGroup.Item>)
                            : null
                    }
                </ListGroup>
            </div>
        )
    }
}

export default Search
