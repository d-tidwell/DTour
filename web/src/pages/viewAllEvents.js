import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class ViewAllEvents extends BindingClass {
    constructor() {+
        super();
        this.bindClassMethods(['clientLoaded', 'mount','redirectProfile','thisPageAddFrom','thisPageRemoveFrom','redirectAllEvents',
        'redirectCreateEvents','redirectAllFollowing','logout','addEvents','addName','createTableRow','getProfileWithRetry'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
   
    }

    /**
     * Once the client is loaded, get the profile metadata.
     */
    async clientLoaded() {
        // const urlParams = new URLSearchParams(window.location.search);
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        const events = await this.client.getAllEvents();
        this.dataStore.set("email", identity.email);
        this.dataStore.set('profile', profile);
        this.dataStore.set('events', events.allEventList);
        this.dataStore.set('firstName', profile.profileModel.firstName);
        this.dataStore.set('lastName', profile.profileModel.lastName);
        this.dataStore.set('following', profile.profileModel.following);
        this.addEvents();
        this.addName();
    
    }
    /**
     * Add the header to the page and load the dannaClient.
     */
    mount() {
        document.getElementById('profilePic').addEventListener('click', this.redirectProfile);
        document.getElementById('allEvents').addEventListener('click', this.redirectAllEvents);
        document.getElementById('createEvents').addEventListener('click', this.redirectCreateEvents);
        document.getElementById('allFollowing').addEventListener('click', this.redirectAllFollowing);
        document.getElementById('logout').addEventListener('click', this.logout);
        document.getElementById('door').addEventListener('click', this.logout);
        document.getElementById('names').innerText = "Loading Profile ...";
        //this.header.addHeaderToPage();

        this.client = new dannaClient();
        this.clientLoaded();
    }

    async delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    async getProfileWithRetry(result, maxRetries = 3, delayMs = 1000) {
        let retries = 0;
        let getName;
    
        while (retries < maxRetries) {
            try {
                getName = await this.client.getProfile(result);
    
                if (getName && getName.profileModel) {
                    return getName;
                }
            } catch (error) {
                console.error(`Error while fetching profile for ID ${result}:`, error);
            }
    
            retries++;
            await this.delay(delayMs);
        }
    
        throw new Error(`Failed to get profile for ID ${result} after ${maxRetries} retries.`);
    }

    async addEvents(){
        const events = await this.dataStore.get("events");
        const profile = await this.dataStore.get("profile")
        if (events == null) {
            document.getElementById("event-list").innerText = "No Events added in your Profile";
        } else {
            let eventResult;
            let counter = 0;
            for (eventResult of events) {
                const resulting =  await this.client.getEventDetails(eventResult.eventId);
                counter += 1
                await this.createTableRow(eventResult, counter, profile);
                   
            }
        }
    }

    async createTableRow(eventResult, counter){
        const resulting = await this.client.getEventDetails(eventResult.eventId);
        const profId = await this.dataStore.get("profile");
        const anchor = document.createElement('tr');
        // ... (all the other table cell creation code)
        const th = document.createElement('th');
        th.setAttribute("scope", "row");
        th.innerText = counter;
        th.setAttribute("scope", "row");
        th.innerText = counter;
        const eventId = document.createElement('td');
        const idlink = document.createElement('a');
        idlink.setAttribute('href', 'eventDetails.html?id='+eventResult.eventId); 
        idlink.style.color ="#212524";
        idlink.innerText = eventResult.eventId;
        const eventName = document.createElement('td');
        eventName.innerText = resulting.eventModel.name;
        const rawDate = resulting.eventModel.dateTime;
        try {
            const inputStringDate = new Date(rawDate.split("[")[0]);

            if (isNaN(inputStringDate.getTime())) {
                throw new Error('Invalid date value');
            }
        
            const dateFormatter = new Intl.DateTimeFormat('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' });
            const timeFormatter = new Intl.DateTimeFormat('en-US', { hour: '2-digit', minute: '2-digit', hour12: true });
            const date = dateFormatter.format(inputStringDate);
            const time = timeFormatter.format(inputStringDate);
            const eventDate = document.createElement('td');
            eventDate.innerText = date;
            const eventTime = document.createElement('td');
            eventTime.innerText = time;
            const eventType = document.createElement('td');
            eventType.innerText = resulting.eventModel.category;
            const eventOrg = document.createElement('td');
            const foriegnProfile = resulting.eventModel.eventCreator;
            const orgNameReal = await this.getProfileWithRetry(foriegnProfile);
            const realName = await this.getProfileWithRetry(profId.profileModel.profileId);
            eventOrg.innerText = orgNameReal.profileModel.firstName+ " "+ orgNameReal.profileModel.lastName;

                const eventCancel = document.createElement('td');
                const removeBtn = document.createElement('button');
            
                if (realName.profileModel.events.includes(eventResult.eventId)) {
                    console.log(realName.profileModel.events,eventResult.eventId,"CANCEL");
                    removeBtn.innerText = "Cancel";
                    removeBtn.className = "btn btn-dark";
                    removeBtn.addEventListener('click', (e) => {
                        e.preventDefault();
                        console.log("clickedRemove", eventResult.eventId);
                        this.thisPageRemoveFrom.call(this, eventResult.eventId, eventOrg.innerText);
                    });
                } else {
                    console.log(realName.profileModel.events,eventResult.eventId,"RSVP");
                    removeBtn.innerText = "RSVP";
                    removeBtn.className = "btn btn-light-custom-table";
                    removeBtn.addEventListener('click', (e) => {
                        e.preventDefault();
                        console.log("clickedAdd", eventResult.eventId);
                        this.thisPageAddFrom.call(this, eventResult.eventId);
                    });
                }
                eventId.appendChild(idlink);
                eventCancel.appendChild(removeBtn);
                anchor.appendChild(th);
                anchor.appendChild(eventId);
                anchor.appendChild(eventName);
                anchor.appendChild(eventDate);
                anchor.appendChild(eventTime);
                anchor.appendChild(eventType);
                anchor.appendChild(eventOrg);
                anchor.appendChild(eventCancel);
                document.getElementById("event-list").appendChild(anchor);

            } catch (error) {
                console.error(error);
            }
    }

    async thisPageRemoveFrom(result, innerId){
        const calling = this.dataStore.get('firstName') + " "+this.dataStore.get('lastName');
        if(calling === innerId){
            console.log(calling === innerId, "is it?????????");
            const deleting = await this.client.deleteEventFromProfile(result);
        } else {
            const remaining = await this.client.removeEventFromProfile(result);
        }
        
        window.location.href = "/viewAllEvents.html";
    }
    async thisPageAddFrom(result){
        const newList = await this.client.addEventToProfile(result);
        window.location.href = "/viewAllEvents.html";
    }
    
   

    async addName(){
        const fname = await this.dataStore.get("firstName");
        const lname = await this.dataStore.get("lastName");
        if (fname == null) {
            document.getElementById("names").innerText = "John Doh";
        }
        document.getElementById("names").innerText = fname + " " + lname;
    }

    redirectProfile(){
        window.location.href = '/profile.html';
        console.log("createEvent button clicked");
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
    const viewAllEvents = new ViewAllEvents();
    viewAllEvents.mount();
};

window.addEventListener('DOMContentLoaded', main);
