import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { searchClubs } from '../modules/clubs'
import { searchPlayers } from '../modules/players'
import ClubList from './ClubList'
import SearchResults from './SearchResults'
import SearchBar from './SearchBar'

const LandingPage = () => {

    const [query, setQuery] = useState('')
    const dispatch = useDispatch()
    const { clubs, players } = useSelector((state) => {
        return state
    })


    useEffect(() => {
        if (query.length < 2) {
            return
        }

        dispatch(searchClubs(query))
        dispatch(searchPlayers(query))
    }, [query, dispatch])


    if (query.length < 2) {
        return (
            <div>
                <SearchBar setQuery={setQuery} />
                <ClubList />
            </div>
        )


    }

    return (
        <div>
            <SearchBar setQuery={setQuery} />
            <SearchResults players={players} clubs={clubs} />
        </div>
    )

}

export default LandingPage