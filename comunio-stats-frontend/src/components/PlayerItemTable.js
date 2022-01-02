import React, { Component } from 'react'

class PlayerItemTable extends Component {
    render() {
        return (
            <div>
                <h1>{playerName}</h1>
                <Table striped bordered hover>
                    <thead>
                        {
                            snapshots.length ?
                                <tr>
                                    <th>Date</th>
                                    <th>Position</th>
                                    <th>Market Value</th>
                                    <th>Points</th>
                                </tr>
                                : null
                        }
                    </thead>

                    <tbody>
                        {
                            snapshots.map(snapshot =>
                                <PlayerSnapshotTableRow
                                    key={snapshot.id}
                                    created={snapshot.created}
                                    position={snapshot.position}
                                    market_value={snapshot.market_value}
                                    points_during_current_season={snapshot.points_during_current_season}
                                />)
                        }
                    </tbody>
                </Table>
            </div>

        )
    }
}

export default PlayerItemTable
