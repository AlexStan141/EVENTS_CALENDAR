import axios from "axios"

class EventService{

    constructor(){
        this.url = "http://localhost:8080/event";
    }

    getAllEvents(){
        return axios.get(this.url);
    }

    getEventById(id){
        return axios.get(this.url + "/id/" + id);
    }

    getEvent(date){
        return axios.get(this.url + "/" + date);
    }

    eventTable(middleDay){
        return axios.get(this.url + "/table/" + middleDay);
    }

    insertEvent(name,startDate,endDate){
        return axios.post(this.url,{name,startDate,endDate});
    }

    updateEvent(id,name,startDate,endDate){
        return axios.put(this.url,{id,name,startDate,endDate});
    }

    deleteEvent(id){
        return axios.delete(this.url + "/" + id)
    }

    getNames(){
        return axios.get(this.url + "/names");
    }

    getIdFromName(name){
        console.log(this.url + "/idFromName/" + name);
        return axios.get(this.url + "/idFromName/" + name);
    }

    getOrderedEvents(){
        return axios.get(this.url + "/ordered");
    }
}

export default new EventService();