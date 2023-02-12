import axios from "axios";

class DayService{

    constructor(){
        this.url="http://localhost:8080/day";
    }

    getDay(date,nrDays){
        return  axios.get(this.url + "/" + date + "/" + nrDays);
    }    
}   

export default new DayService();