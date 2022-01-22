import { SUCCESS_SUFFIX } from "redux-axios-middleware";
import HttpService from "../services/HttpService";

export const LIST_CLUBS = 'LIST_CLUBS';
export const GET_CLUB = 'GET_CLUB';
export const SEARCH_CLUBS = 'SEARCH_CLUBS';

const clubsReducer = (state = [], action) => {
    switch (action.type) {
        case LIST_CLUBS + SUCCESS_SUFFIX:
            return action.payload.data;

        default:
            return state;
    }
};

export default clubsReducer;

export const allClubs = () => ({
    type: LIST_CLUBS,
    payload: {
        request: {
            url: '/clubs',
        }
    }
});

export const getClub = (clubId) => ({
    type: GET_CLUB,
    payload: {
        request: {
            url: `/clubs?id=${clubId}`
        }
    }
});

export const searchClubs = (query) => ({
    type: SEARCH_CLUBS,
    payload: {
        request: {
            url: `/clubs/q?name=${query}`,
        }
    }
});