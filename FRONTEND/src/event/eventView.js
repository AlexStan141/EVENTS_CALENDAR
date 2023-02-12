import { useNavigate, useParams } from "react-router-dom";
import {useState,useEffect} from "react";
import eventService from "../service/eventService";
import "./eventView.css"

function EventView(){

        const {date} = useParams();
        const [dates,setDates] = useState([]);
        const [names, setNames] = useState([]);
        const navigate = useNavigate();

        useEffect(() => {
            eventService.eventTable(date).then((response)=> {
                setDates(response.data);
            })
        },[date]);

        useEffect(() => {
            eventService.getNames().then((response) => {
                setNames(response.data);
            })
        },[]);

        return( 
        <>
           <img src="https://cdn-icons-png.flaticon.com/512/892/892692.png"  alt="yesterday" width="100"
                    height="100" onClick={() => {navigate(`/event/view/${dates[3][0]}`)}}></img>
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Eo_circle_red_arrow-down.svg/2048px-Eo_circle_red_arrow-down.svg.png"  alt="tomorrow" width="100"
                    height="100" onClick={() => {navigate(`/event/view/${dates[5][0]}`)}}></img> 
            <button onClick={() => {navigate("/event/insert")}}>Create Event</button> 
            <table>
                {dates.map((date) => {return <tr>
                    {date.map((event) => {return(<td onClick={() => {
                        if(names.includes(event,0)){
                            eventService.getIdFromName(event).then((response)=> {
                                navigate(`/event/update/${response.data}`);
                            })
                        }
                    }}>{event}</td>)})}
                </tr>})}
            </table>
        </>)
}

export default EventView;