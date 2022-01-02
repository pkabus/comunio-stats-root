import React, { Component } from 'react'
import axios from 'axios'
import { backend_properties } from '../backend_properties.js'
import { ComposedChart, LineChart, Line, Bar, XAxis, Label, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'
import moment from 'moment'
import { Container } from 'react-bootstrap'

class PlayerItem extends Component {
    constructor(props) {
        super(props)

        this.state = {
            playerName: '',
            snapshots: [],
            errorMsg: ''
        }

        this.chartFormatter = this.chartFormatter.bind(this)
        this.monetaryFormatterSmall = this.monetaryFormatterSmall.bind(this)
    }

    monetaryFormatterSmall(value) {
        if (value > 999999) {
            return "€" + value / 1000000 + " M"
        }

        return this.monetaryFormatter(value)
    }

    monetaryFormatter(value) {
        return "€" + new Intl.NumberFormat('de').format(value)
    }


    chartFormatter(value, name) {
        if (name && name === "Market Value") {
            return this.monetaryFormatter(value)
        }

        return value
    }

    componentDidMount() {
        const { backend_host, backend_port } = backend_properties
        const { playerId } = this.props.match.params
        axios.get(`http://${backend_host}:${backend_port}/players?id=${playerId}`)
            .then(response => {
                this.setState({
                    playerName: response.data.name
                })
            })
            .catch(error => {
                this.setState({
                    errorMsg: error.message
                })
            })

        const start = moment().subtract(28, "days").format("YYYY-MM-DD")
        const end = moment().format("YYYY-MM-DD")
        axios.get(`http://${backend_host}:${backend_port}/players/snapshots?playerId=${playerId}&start=${start}&end=${end}&size=30`)
            .then(response => {
                // this.setState({
                //     snapshots: response.data._embedded.player_snapshot_dto_list
                //         .map(entry => ({
                //             date: moment({
                //                 year: entry.created[0], month: entry.created[1] - 1, day: entry.created[2]
                //             }).format('DD.MM.YYYY'),
                //             points: entry.points_during_current_season,
                //             market_value: entry.market_value,
                //             pointsPerDay: 0
                //         }))

                // })

                var data = response.data._embedded.player_snapshot_dto_list
                var newSnapshots = []
                for (var i = 1; i < data.length; i++) {
                    var current = data[i]
                    var last = data[i - 1]
                    var newSnapshotValue = {
                        date: moment({
                            year: current.created[0], month: current.created[1] - 1, day: current.created[2]
                        }).format('DD.MM.YYYY'),
                        points: current.points_during_current_season,
                        market_value: current.market_value,
                        points_per_day: current.points_during_current_season - last.points_during_current_season
                    }
                    newSnapshots.push(newSnapshotValue)
                }

                this.setState({
                    snapshots: newSnapshots
                })


            })


            .catch(error => {
                this.setState({
                    errorMsg: error.message
                })
            })
    }



    render() {
        const { playerName, snapshots, errorMsg } = this.state
        if (errorMsg) {
            console.error(errorMsg)
        }

        return (
            <Container>
                <h1>{playerName}</h1>
                <ResponsiveContainer width="100%" height={400}>
                    <ComposedChart
                        width={500}
                        height={300}
                        data={snapshots}
                        margin={{ left: 20 }}>

                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="date" />
                        <YAxis yAxisId="market_value" tickFormatter={this.monetaryFormatterSmall} />
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
                        <Tooltip formatter={this.chartFormatter} />
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
                            // stroke="#82ca9d"
                            strokeWidth={2} />
                    </ComposedChart>
                </ResponsiveContainer>
            </Container>
        )
    }
}

export default PlayerItem
