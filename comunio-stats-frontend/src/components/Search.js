import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
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

        dispatch(searchClubs(query))
        dispatch(searchPlayers(query))
    }, [query, dispatch])



    return (
        <div className="container mt-3">
            <div className="row">
                <div className="input-group mb-3 col-12 px-2">
                    <div className="input-group-prepend">
                        <label className="input-group-text" htmlFor="searchQuery">Search</label>
                    </div>
                    <input type="text" className="form-control" placeholder="for clubs and players" aria-label="Username" aria-describedby="addon-wrapping" onChange={(queryInput) => setQuery(queryInput.target.value)} />
                </div>
            </div>


            <div className="row">
                <div className="list-group col-12 px-2">
                    {query.length > 2 && Players && Players.length ?
                        Players.map((player) => (
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

                    {query.length > 2 && Clubs && Clubs.length ?
                        Clubs.map((club) => (
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

export default Search
