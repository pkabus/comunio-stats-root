import { SUCCESS_SUFFIX } from "redux-axios-middleware";

export const LIST_PLAYER_SNAPSHOTS_OF_CLUB = 'LIST_PLAYER_SNAPSHOTS_OF_CLUB';
export const LIST_PLAYER_SNAPSHOTS = 'LIST_PLAYER_SNAPSHOTS';
export const SEARCH_PLAYERS = 'SEARCH_PLAYERS';

const clubsReducer = (state = [], action) => {
    switch (action.type) {
        case LIST_PLAYER_SNAPSHOTS_OF_CLUB + SUCCESS_SUFFIX:
        case SEARCH_PLAYERS + SUCCESS_SUFFIX:
            return action.payload.data._embedded ? action.payload.data._embedded.player_dto_list : [];

        default:
            return state;
    }
};

export default clubsReducer;

export const allPlayersOfClub = (clubId) => ({
    type: LIST_PLAYER_SNAPSHOTS_OF_CLUB,
    payload: {
        request: {
            url: `/players/snapshots?clubId=${clubId}&mostRecentOnly=true&size=50`,
        }
    }
});

export const getPlayerSnapshots = (playerId, start, end) => ({
    type: LIST_PLAYER_SNAPSHOTS,
    payload: {
        request: {
            url: `/players/snapshots?playerId=${playerId}&start=${start}&end=${end}&size=30`
        }
    }
})

export const searchPlayers = (query) => ({
    type: SEARCH_PLAYERS,
    payload: {
        request: {
            url: `/players/q?name=${query}`,
        }
    }
});