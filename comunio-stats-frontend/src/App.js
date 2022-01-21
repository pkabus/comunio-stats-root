import './App.css';
import ClubList from './components/ClubList'
import Search from './components/Search'
import ClubItem from './components/ClubItem'
import PlayerItem from './components/PlayerItem'
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom"
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route exact path="/">
            {/* <ClubList /> */}
            <Search />
          </Route>
          <Route path="/clubs/:clubId" component={ClubItem} />
          <Route path="/players/:playerId" component={PlayerItem} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
