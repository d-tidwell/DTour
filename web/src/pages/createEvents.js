import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class CreateEvent extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'redirectProfile','mount','confirmRedirect','dateAndTimeExtractor','submitFormData','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout','setPlaceholders'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const urlParamsId = urlParams.get("id");
        const identity = await this.client.getIdentity();
        this.dataStore.set('id', identity.email);
        const profile = await this.client.getProfile(identity.email);
        this.dataStore.set('profile', profile);
        if(profile == null) {
            document.getElementById("welcome").innerText = "Welcome! First Lets Make Your Profile!"
        }
        if(urlParamsId != null){
            const event = await this.client.getEventDetails(urlParamsId);
            this.dataStore.set('event', event);
            console.log(event);
            document.getElementById("loading").innerText = "Loading.....";
            document.getElementById("name").setAttribute('placeholder', event.eventModel.name);
            document.getElementById("address").setAttribute('placeholder', event.eventModel.eventAddress);
            const dateTime = await this.dateAndTimeExtractor(event.eventModel.dateTime);
            console.log(dateTime);
            document.getElementById("date").setAttribute('placeholder', dateTime.date);
            document.getElementById("time").setAttribute('placeholder', dateTime.time);
            document.getElementById("categories").setAttribute('placeholder', event.eventModel.category);
            console.log(event.eventModel.category);
            document.getElementById("description").setAttribute('placeholder', event.eventModel.description);
            document.getElementById("loading").innerText = "";
        }
   
        this.setPlaceholders();

    }

    mount() {
  
        document.getElementById('profilePic').addEventListener('click', this.redirectProfile);
        document.getElementById('allEvents').addEventListener('click', this.redirectAllEvents);
        document.getElementById('createEvents').addEventListener('click', this.redirectCreateEvents);
        document.getElementById('allFollowing').addEventListener('click', this.redirectAllFollowing);
        document.getElementById('logout').addEventListener('click', this.logout);
        document.getElementById('door').addEventListener('click', this.logout);
        document.getElementById('confirm').addEventListener('click', this.confirmRedirect);
        document.getElementById('submited').addEventListener('click', this.submitFormData);

      

        this.client = new dannaClient();
        this.clientLoaded();
    }
    async dateAndTimeExtractor(rawDate){
        console.log(rawDate);
        const inputStringDate = new Date(rawDate.split("[")[0]);
        if (isNaN(inputStringDate.getTime())) {
            throw new Error('Invalid date value');
        }
    
        const dateFormatter = new Intl.DateTimeFormat('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' });
        const timeFormatter = new Intl.DateTimeFormat('en-US', { hour: '2-digit', minute: '2-digit', hour12: true });
        const date = dateFormatter.format(inputStringDate);
        const time = timeFormatter.format(inputStringDate);
        return {date,time};
    }
    async convertToDateTime(dateString, timeString) {
        // Get the current timezone
        const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
      
        // Parse the date string
        const [month, day, year] = dateString.split('/').map(part => parseInt(part, 10));
      
        // Parse the time string and convert to 24-hour format
        const [timeValue, timePeriod] = timeString.split(' ');
        let [hours, minutes] = timeValue.split(':').map(part => parseInt(part, 10));
        if (timePeriod === 'PM' && hours !== 12) {
          hours += 12;
        } else if (timePeriod === 'AM' && hours === 12) {
          hours = 0;
        }
      
        // Create a Date object using the parsed values
        const date = new Date(year, month - 1, day, hours, minutes);
      
        // Convert date object to an ISO string
        const isoString = date.toISOString();
      
        // Get the offset in minutes and convert it to hours and minutes
        const offset = -date.getTimezoneOffset();
        const offsetSign = offset >= 0 ? "+" : "-";
        const offsetHours = Math.floor(Math.abs(offset) / 60);
        const offsetMinutes = Math.abs(offset) % 60;
        const formattedOffset = `${offsetSign}${String(offsetHours).padStart(2, "0")}:${String(offsetMinutes).padStart(2, "0")}`;
      
        // Get the date and time parts from the ISO string
        const [datePart, timePart] = isoString.split("T");
        const timeWithoutMillis = timePart.split(".")[0];
      
        // Assemble the final ZonedDateTime string
        const zonedDateTimeString = `${datePart}T${timeWithoutMillis}${formattedOffset}[${timezone}]`;
      
        return zonedDateTimeString;
    }
      

    async setPlaceholders(){
        const profile = this.dataStore.get("profile");
        if (profile == null) {
            return;
        }
        if (profile.profileModel.firstName && profile.profileModel.lastName) {
            document.getElementById("names").innerText =  profile.profileModel.firstName + " " +  profile.profileModel.lastName
        }
        document.getElementById("loading").remove();
    }

    async submitFormData(evt){
        evt.preventDefault();
        const oldEvent = this.dataStore.get("event");
        let event;
        if(typeof this.dataStore.get('event') === 'undefined'){
            console.log("here");
            const name = document.getElementById('name').value;
            const address = document.getElementById('address').value;
            const date = document.getElementById('date').value;
            const time = document.getElementById('time').value;
            const categoriesString = document.getElementById('categories').value;
            const categories = categoriesString.split(',').map(item => item.trim());
            const description = document.getElementById('description').value;
            const dateTime = await this.convertToDateTime(date, time);
            event = await this.client.createEvent(name, address, dateTime, categories, description, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
            });
        } else {
  
            const id = oldEvent.eventModel.eventId;
            const name = document.getElementById('name').value || document.getElementById("name").getAttribute('placeholder');
            const address = document.getElementById('address').value || document.getElementById("address").getAttribute('placeholder')
            const date = document.getElementById('date').value || document.getElementById("date").getAttribute('placeholder')
            const time = document.getElementById('time').value || document.getElementById("time").getAttribute('placeholder')
            const categoriesString = document.getElementById('categories').value|| document.getElementById("categories").getAttribute('placeholder')
            const categories = categoriesString.split(',').map(item => item.trim());
            const description = document.getElementById('description').value || document.getElementById("description").getAttribute('placeholder')
            const dateTime = await this.convertToDateTime(date, time);
            event = await this.client.updateEvent(id, name, address, dateTime, categories, description, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
            });
        }
        

        this.dataStore.set('event', event);
        document.getElementById('fname').innerText = event.event.name;
        document.getElementById('faddress').innerText = event.event.eventAddress;
        const newDateTime = await this.dateAndTimeExtractor(event.event.dateTime);
        document.getElementById('fdate').innerText = newDateTime.date;
        document.getElementById('ftime').innerText = newDateTime.time;
        document.getElementById('fcategories').innerText = event.event.category;
        document.getElementById('fdescription').innerText =event.event.description;
        document.getElementById('loading-modal').remove();
        
    }
    confirmRedirect() {
        window.location.href = '/profile.html';
    }
    redirectProfile(){
        window.location.href = '/profile.html';
    }
    redirectAllEvents(){
        window.location.href = '/viewAllEvents.html';
    }
    redirectCreateEvents(){
        window.location.href = '/createEvents.html';
    }
    redirectAllFollowing(){
        window.location.href = '/allFollowing.html';
    }
    async logout(){
        await this.client.logout(); 
        if(!this.client.isLoggedIn()){
            window.location.href ='/landingPage.html';
        }
        
    }
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createEvent = new CreateEvent();
    createEvent.mount();
};

window.addEventListener('DOMContentLoaded', main);