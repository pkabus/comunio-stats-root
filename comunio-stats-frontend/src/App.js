import './App.css';
import { Provider } from "react-redux";
import Search from './components/Search'
import ClubItem from './components/ClubItem'
import PlayerItem from './components/PlayerItem'
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom"
import 'bootstrap/dist/css/bootstrap.min.css';
import StoreService from './services/StoreService'

const store = StoreService.setup();

const App = () => {
  return (
    <div className="App">
      <Provider store={store}>
        <Router>
          <Switch>
            <Route exact path="/">
              <Search />
            </Route>
            <Route path="/clubs/:clubId" component={ClubItem} />
            <Route path="/players/:playerId" component={PlayerItem} />
          </Switch>
        </Router>
      </Provider>
    </div>
  );
}

export default App;
