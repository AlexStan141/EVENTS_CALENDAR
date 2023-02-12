import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes,Route, useParams } from 'react-router-dom';
import EventView from "./event/eventView";
import EventInsert from './event/eventInsert';
import EventUpdate from './event/eventUpdate';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/event/view/:date/" element={<EventView/>}/>
        <Route path="/event/insert" element={<EventInsert/>}/>
        <Route path="/event/update/:id" element={<EventUpdate/>}/>
      </Routes>
    </Router>
  );
}

export default App;
