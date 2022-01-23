import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { ListGroup } from 'react-bootstrap'
import { useDispatch, useSelector } from 'react-redux'
import { searchClubs } from '../modules/Clubs'
import { searchPlayers } from '../modules/Players'


const Search = () => {

    const [query, setQuery] = useState('')
    const dispatch = useDispatch()
    const { Clubs, Players } = useSelector((state) => {
        return state
    })


    useEffect(() => {
        if (query.length < 2) {
            return
        }

        console.log("query = " + query)
        dispatch(searchClubs(query))
        dispatch(searchPlayers(query))
    }, [query, dispatch])



    return (
        <div>

            <div className="input-group mb-3">
                <div className="input-group-prepend">
                    <label className="input-group-text" htmlFor="searchQuery">Deine Moam</label>
                </div>
                <input type="text" className="form-control" placeholder="Search for club or player" aria-label="Username" aria-describedby="addon-wrapping" onChange={(queryInput) => setQuery(queryInput.target.value)} />
            </div>




            <h3>Players</h3>
            <ListGroup varian="flush">
                {
                    query.length > 2 && Players && Players.length ?
                        Players.map(player =>
                            <ListGroup.Item key={player.id}>
                                <Link to={`/players/${player.id}`}>{player.name}</Link>
                            </ListGroup.Item>)
                        : null
                }
            </ListGroup>

            <h3>Clubs</h3>
            <ListGroup varian="flush">
                {
                    query.length > 2 && Clubs && Clubs.length ?
                        Clubs.map(club =>
                            <ListGroup.Item key={club.id}>
                                <Link to={`/clubs/${club.id}`}>{club.name}</Link>
                            </ListGroup.Item>)
                        : null
                }
            </ListGroup>
        </div>
    )

}

export default Search













// export class Search extends Component {

//     constructor(props) {
//         super(props)

//         this.state = {
//             players: [],
//             clubs: [],
//             errorMsg: ''
//         }

//         this.searchInputOnChange = this.searchInputOnChange.bind(this)
//     }

//     componentDidMount() {


//     }

//     searchInputOnChange(input) {
//         const { url } = backend_properties

//         if (input.target.value.length < 2) {
//             this.setState({
//                 players: [],
//                 clubs: []
//             })
//             return
//         }

//         axios.get(`http://${url}/players/q?name=${input.target.value}`)
//             .then(response => {
//                 console.log(`New Players for ${input.target.value}: ` + JSON.stringify(response.data._embedded.player_dto_list))
//                 this.setState({
//                     players: response.data._embedded.player_dto_list.length > 0 ? response.data._embedded.player_dto_list : ''
//                 })
//             })
//             .catch(error => {
//                 this.setState({
//                     errorMsg: error.message,
//                     players: []
//                 })
//             })

//         axios.get(`http://${url}/clubs/q?name=${input.target.value}`)
//             .then(response => {
//                 console.log(`New Clubs for ${input.target.value}: ` + JSON.stringify(response.data._embedded.club_dto_list))
//                 this.setState({
//                     clubs: response.data._embedded.club_dto_list.length > 0 ? response.data._embedded.club_dto_list : ''
//                 })
//             })
//             .catch(error => {
//                 this.setState({
//                     errorMsg: error.message,
//                     clubs: []
//                 })
//             })
//     }


//     render() {
//         const { players, clubs } = this.state
//         return (
//             <div>

//                 <div className="input-group mb-3">
//                     <div className="input-group-prepend">
//                         <label className="input-group-text" htmlFor="searchQuery">Deine Moam</label>
//                     </div>
//                     <input type="text" className="form-control" placeholder="Search for club or player" aria-label="Username" aria-describedby="addon-wrapping" onChange={this.searchInputOnChange} />
//                 </div>




//                 <h3>Players</h3>
//                 <ListGroup varian="flush">
//                     {
//                         players.length ?
//                             players.map(player =>
//                                 <ListGroup.Item key={player.id}>
//                                     <Link to={`/players/${player.id}`}>{player.name}</Link>
//                                 </ListGroup.Item>)
//                             : null
//                     }
//                 </ListGroup>

//                 <h3>Clubs</h3>
//                 <ListGroup varian="flush">
//                     {
//                         clubs.length ?
//                             clubs.map(club =>
//                                 <ListGroup.Item key={club.id}>
//                                     <Link to={`/clubs/${club.id}`}>{club.name}</Link>
//                                 </ListGroup.Item>)
//                             : null
//                     }
//                 </ListGroup>
//             </div>
//         )
//     }
// }


