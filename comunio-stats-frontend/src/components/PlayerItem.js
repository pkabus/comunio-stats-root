import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getPlayerSnapshots } from '../modules/playerSnapshots'
import { getPlayer } from '../modules/players'
import { ComposedChart, Line, XAxis, Label, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'
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
                        <XAxis dataKey={(v) => moment({
                            year: v.created[0], month: v.created[1] - 1, day: v.created[2]
                        }).format('DD.MM.YYYY')} />
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
                        <Line yAxisId="points"
                            type="monotone"
                            dataKey="points_during_current_season"
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

