import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import { getClub } from '../modules/clubs'
import { allPlayersOfClub } from '../modules/playerSnapshots'

const ClubItem = (props) => {

    const { clubId } = props.match.params
    const dispatch = useDispatch()
    const { clubs, playerSnapshots } = useSelector((state) => {
        return state
    })

    useEffect(() => {
        dispatch(getClub(clubId))
        dispatch(allPlayersOfClub(clubId))
    }, [clubId, dispatch])


    if (!clubs || !clubs.length || !playerSnapshots) {
        return <div />
    }

    return (
        <div className="container mt-3">
            <div className="row mb-3 col-12 px-2">
                <h1>{clubs[0].name}</h1>
            </div>
            <div className="row">
                <div className="list-group col-12 px-2">
                    {playerSnapshots.map((snapshot) => (
                        <div key={snapshot.id} className="list-group-item list-group-item-action flex-column align-items-start">
                            <Link to={`/players/${snapshot.player.id}`} style={{ color: 'inherit', textDecoration: 'none' }}>
                                <div className="d-flex flex-fill justify-content-between">
                                    <h5 className="mb-3 mt-3">{snapshot.player.name}</h5>
                                    <div className="d-flex justify-content-end">
                                        <small>{snapshot.points_during_current_season} points in season</small>
                                        <small className="ml-3">{snapshot.position}</small>
                                    </div>
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


export default ClubItem