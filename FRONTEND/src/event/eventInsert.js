import {useNavigate, useParams } from "react-router-dom";
import {useState,useEffect} from "react";
import { Form, Button } from "react-bootstrap";
import eventService from "../service/eventService";
import "./eventInsert.css";

function EventInsert(){

    const [name, setName] = useState("");
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [errors,setErrors] = useState([]);
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();
        var target = event.target;
        var name = target.elements.name.value;
        var startDate = target.elements.startDate.value;
        var endDate = target.elements.endDate.value;

        eventService.insertEvent(name,startDate,endDate).then((response) => {
            if(response.data.info[0] =="Event inserted successfully"){
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
                <Form.Group controlId = "insertEventName">
                    <Form.Label>Name:</Form.Label>
                    <Form.Control
                        type="text"
                        name="name"
                        value={name}
                        onChange = {(event) =>{setName(event.target.value)}}>
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId = "insertEventStartDate">
                    <Form.Label>Start date:</Form.Label>
                    <Form.Control
                        type="text"
                        name="startDate"
                        value={startDate}
                        onChange = {(event) =>{setStartDate(event.target.value)}}>
                    </Form.Control>
                </Form.Group>
                <Form.Group controlId = "insertEventEndDate">
                    <Form.Label>End date:</Form.Label>
                    <Form.Control
                        type="text"
                        name="endDate"
                        value={endDate}
                        onChange = {(event) =>{setEndDate(event.target.value)}}>
                    </Form.Control>
                </Form.Group>
                <Button variant="primary" type="submit">Add event</Button>
            </Form>
        </>
    )
}

export default EventInsert;

