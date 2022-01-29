import { SUCCESS_SUFFIX } from "redux-axios-middleware";


export const GET_PLAYER = 'GET_PLAYER';
export const SEARCH_PLAYERS = 'SEARCH_PLAYERS';
var runningSearchRequest = null

const playersReducer = (state = [], action) => {
    switch (action.type) {
        case SEARCH_PLAYERS:
            runningSearchRequest = action.actionId
            return state;
        case SEARCH_PLAYERS + SUCCESS_SUFFIX:
            if (runningSearchRequest === action.meta.previousAction.actionId) {
                // only proceed if the search request is the expected one
                return action.payload.data._embedded ? action.payload.data._embedded.player_dto_list : [];
            } else {
                return state;
            }
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
    actionId: Math.floor(Math.random() * 10000000),
    payload: {
        request: {
            url: `/players/q?name=${query}`,
        }
    }
});