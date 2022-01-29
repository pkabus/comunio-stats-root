import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import { allClubs } from '../modules/clubs'

const ClubList = () => {

    const dispatch = useDispatch()
    const { clubs } = useSelector((state) => {
        return state
    })

    useEffect(() => {
        dispatch(allClubs())
    }, [dispatch])


    return (
        <div className="container mt-3">
            <div className="row mb-3 col-12 px-2">
                <h1>Bundesliga clubs</h1>
            </div>
            <div className="row">
                <div className="list-group col-12 px-2">
                    {clubs.map((club) => (
                        <div key={club.id} className="list-group-item list-group-item-action flex-column align-items-start">
                            <Link to={`/clubs/${club.id}`} style={{ color: 'inherit', textDecoration: 'none' }}>
                                <div className="d-flex flex-fill justify-content-between">
                                    <h5 className="mb-3 mt-3">{club.name}</h5>
                                </div>
                            </Link>
                        </div>
                    ))
                    }
                </div>
            </div>
        </div>
    )
}

export default ClubList

