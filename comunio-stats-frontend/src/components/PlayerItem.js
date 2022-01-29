import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getPlayerSnapshots } from '../modules/playerSnapshots'
import { getPlayer } from '../modules/players'
import { ComposedChart, Line, Bar, XAxis, Label, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'
import moment from 'moment'


const PlayerItem = (props) => {


    const { playerId } = props.match.params
    const dispatch = useDispatch()
    const start = moment().subtract(28, "days").format("YYYY-MM-DD")
    const end = moment().format("YYYY-MM-DD")
    const { players, playerSnapshots } = useSelector((state) => {
        return state
    })


    useEffect(() => {
        dispatch(getPlayer(playerId))
        dispatch(getPlayerSnapshots(playerId, start, end))
    }, [dispatch, playerId, start, end])


    if (!players || !players.length || !playerSnapshots) {
        return <div />
    }

    return (
        <div className="container mt-3">
            <div className="row">
                <h1>{players[0].name}</h1>
                <ResponsiveContainer width="100%" height={400}>
                    <ComposedChart
                        width={500}
                        height={300}
                        data={playerSnapshots}
                        margin={{ left: 20 }}>

                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="date" />
                        <YAxis yAxisId="market_value" tickFormatter={monetaryFormatterSmall} />
                        <YAxis yAxisId="points" orientation="right" >
                            <Label
                                value='Points'
                                angle={-90}
                                position='outside'
                                fill='#676767'
                                fontSize={16}
                                dx={10}
                            />
                        </YAxis>
                        <Tooltip formatter={chartFormatter} />
                        <Legend />
                        <Line yAxisId="market_value"
                            type="monotone"
                            dataKey="market_value"
                            name="Market Value"
                            stroke="#8884d8"
                            strokeWidth={2} />
                        <Bar yAxisId="points"
                            type="monotone"
                            dataKey="points_per_day"
                            name="Points"
                            fill="#413ea0"
                            strokeWidth={2} />
                    </ComposedChart>
                </ResponsiveContainer>
            </div>
        </div>
    )
}

export default PlayerItem


function monetaryFormatterSmall(value) {
    if (value > 999999) {
        return "€" + value / 1000000 + " M"
    }

    return monetaryFormatter(value)
}

function monetaryFormatter(value) {
    return "€" + new Intl.NumberFormat('de').format(value)
}


function chartFormatter(value, name) {
    if (name && name === "Market Value") {
        return monetaryFormatter(value)
    }

    return value
}
