import { combineReducers } from "redux";
import clubs from "./clubs";
import players from "./players";
import playerSnapshots from "./playerSnapshots";

export default combineReducers({
    clubs,
    players,
    playerSnapshots
});