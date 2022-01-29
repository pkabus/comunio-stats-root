import React from 'react'
import { Link } from 'react-router-dom'


const SearchResults = (props) => {

    const { clubs, players } = props

    return (
        <div className="container mt-3">
            <div className="row mb-3 col-12 px-2">
                <h3>Search results</h3>
            </div>
            <div className="row">
                <div className="list-group col-12 px-2">
                    {players && players.length ?
                        players.map((player) => (
                            <div key={player.id} className="list-group-item list-group-item-action flex-column align-items-start">
                                <Link to={`/players/${player.id}`} style={{ color: 'inherit', textDecoration: 'none' }}>
                                    <div className="d-flex w-100 justify-content-between">
                                        <h5 className="mb-3 mt-3">{player.name}</h5>
                                        <small>Player</small>
                                    </div>
                                </Link>
                            </div>
                        ))
                        : <div></div>

                    }

                    {clubs && clubs.length ?
                        clubs.map((club) => (
                            <div key={club.id} className="list-group-item list-group-item-action flex-column align-items-start">
                                <Link to={`/clubs/${club.id}`} style={{ color: 'inherit', textDecoration: 'none' }}>
                                    <div className="d-flex w-100 justify-content-between">
                                        <h5 className="mb-3 mt-3">{club.name}</h5>
                                        <small>Club</small>
                                    </div>
                                </Link>
                            </div>
                        ))
                        : <div></div>
                    }
                </div>
            </div>
        </div>
    )

}

export default SearchResults