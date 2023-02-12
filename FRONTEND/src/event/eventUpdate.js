import {useNavigate, useParams } from "react-router-dom";
import {useState,useEffect} from "react";
import { Form, Button } from "react-bootstrap";
import eventService from "../service/eventService";
import "./eventUpdate.css";

function EventUpdate(){

    const [name, setName] = useState("");
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [errors,setErrors] = useState([]);
    const navigate = useNavigate();
    const {eventName} = useParams();
    const {id} = useParams();


    useEffect(() => {
        eventService.getEventById(id).then((response) => {
            console.log(response.data);
            setName(response.data.object.name);
            setStartDate(response.data.object.startDate);
            setEndDate(response.data.object.endDate);
        })
    },[]);

    const handleSubmit = (event) => {
        event.preventDefault();
        var target = event.target;
        var name = target.elements.name.value;
        var startDate = target.elements.startDate.value;
        var endDate = target.elements.endDate.value;
        eventService.updateEvent(id,name,startDate,endDate).then((response) => {
            if(response.data.info[0] =="Event updated successfully"){
                navigate(`/event/view/${startDate.substring(0,10)}`);
            }
            else{
                setErrors(response.data.info);
            }
        })
    }

    return(
        <>
            {errors.map((error) => {
                return(<p class="error">{error}</p>)
            })}
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId = "updateEventName">
                    <Form.Label>Name:</Form.Label>
                    <Form.Control
                        type="text"
                        name="name"
                        value={name}
                        onChange = {(event) =>{setName(event.target.value)}}>
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId = "updateEventStartDate">
                    <Form.Label>Start date:</Form.Label>
                    <Form.Control
                        type="text"
                        name="startDate"
                        value={startDate}
                        onChange = {(event) =>{setStartDate(event.target.value)}}>
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId = "updateEventEndDate">
                    <Form.Label>End date:</Form.Label>
                    <Form.Control
                        type="text"
                        name="endDate"
                        value={endDate}
                        onChange = {(event) =>{setEndDate(event.target.value)}}>
                    </Form.Control>
                </Form.Group>
                <Button variant="primary" type="submit">Update event</Button>
            </Form>
            <Button onClick={() => {
                eventService.deleteEvent(id);
                navigate(`/event/view/${startDate.substring(0,10)}`);
            }}>Delete event</Button>
        </>
    )
}

export default EventUpdate;