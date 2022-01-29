import React from 'react'


const SearchBar = (props) => {

    const setQuery = props.setQuery

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
        </div>
    )
}

export default SearchBar