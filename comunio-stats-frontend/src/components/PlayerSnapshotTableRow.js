import React, { Component } from 'react'
import * as moment from 'moment'
import NumberFormat from 'react-number-format'

class PlayerSnapshot extends Component {

    constructor(props) {
        super(props)

        this.state = {
            formattedCreated: ''
        }
    }

    componentDidMount() {
        // const date = new Date(created[0], created[1] - 1, created[2])
        const { created } = this.props
        this.setState({
            formattedCreated: moment({
                year: created[0],
                month: created[1] - 1,
                day: created[2]
            }).format('DD.MM.YYYY')
        })
    }


    render() {
        const { created, position, market_value, points_during_current_season } = this.props

        return (
            <tr>
                <td>
                    {
                        moment({
                            year: created[0], month: created[1] - 1, day: created[2]
                        }).format('DD.MM.YYYY')
                    }
                </td>
                <td>{position}</td>
                <td><NumberFormat value={market_value} displayType="text" thousandSeparator="." decimalSeparator={false} /></td>
                <td>{points_during_current_season}</td>
            </tr >
        )
    }
}

export default PlayerSnapshot
