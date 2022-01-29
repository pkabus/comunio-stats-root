import { SUCCESS_SUFFIX } from "redux-axios-middleware";


export const GET_PLAYER = 'GET_PLAYER';
export const SEARCH_PLAYERS = 'SEARCH_PLAYERS';


const playersReducer = (state = [], action) => {
    switch (action.type) {
        case SEARCH_PLAYERS + SUCCESS_SUFFIX:
            return action.payload.data._embedded ? action.payload.data._embedded.player_dto_list : [];
        case GET_PLAYER + SUCCESS_SUFFIX:
            return action.payload.data ? [action.payload.data] : [];
        default:
            return state;
    }
};

export default playersReducer;


export const getPlayer = (playerId) => ({
    type: GET_PLAYER,
    payload: {
        request: {
            url: `/players?id=${playerId}`,
        }
    }
});

export const searchPlayers = (query) => ({
    type: SEARCH_PLAYERS,
    payload: {
        request: {
            url: `/players/q?name=${query}`,
        }
    }
});