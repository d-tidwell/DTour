import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class EventDetails extends BindingClass {
    constructor() {+
        super();
        this.bindClassMethods(['clientLoaded', 'mount','addName','orgName','attending','delay','redirectEditEvent','getProfileWithRetry','createAttendingIcons','convertDateTimeToBrowserTimeZone','redirectEditProfile','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

    }

    /**
     * Once the client is loaded, get the profile metadata.
     */
    async clientLoaded() {
        const identity = await this.client.getIdentity();
        const urlParams = new URLSearchParams(window.location.search);
        const eventId = urlParams.get("id");
        if (eventId) {
          const event = await this.client.getEventDetails(eventId);
          this.dataStore.set("event", event);
          this.dataStore.set("eventId", event.eventModel.eventId);
          if(identity.email !== event.eventModel.eventCreator){
            document.getElementById('edit-btn').remove()
            }
        } else {
          console.error('id not found in the URL');
        }
        const profile = await this.client.getProfile(identity.email);
        this.dataStore.set("email", identity.email);
        this.dataStore.set('profile', profile);
        this.dataStore.set('firstName', profile.profileModel.firstName);
        this.dataStore.set('lastName', profile.profileModel.lastName);
        this.addName();
        this.populateDetails();
        this.attending();
    }

    /**
     * Add the header to the page and load the dannaClient.
     */
    mount() {
        document.getElementById('profilePic').addEventListener('click', this.redirectEditProfile);
        document.getElementById('allEvents').addEventListener('click', this.redirectAllEvents);
        document.getElementById('createEvents').addEventListener('click', this.redirectCreateEvents);
        document.getElementById('allFollowing').addEventListener('click', this.redirectAllFollowing);
        document.getElementById('logout').addEventListener('click', this.logout);
        document.getElementById('door').addEventListener('click', this.logout);
        document.getElementById('edit-btn').addEventListener('click', this.redirectEditEvent);

        this.client = new dannaClient();
        this.clientLoaded();
    }
     
    async addName(){
        const fname = await this.dataStore.get("firstName");
        const lname = await this.dataStore.get("lastName");
        if (fname == null) {
            document.getElementById("names").innerText = "John Doh";
        }
        document.getElementById("names").innerText = fname + " " + lname;
    }
    async convertDateTimeToBrowserTimeZone(dateTimeString) {
        const rawDate = dateTimeString;

        const inputStringDate = new Date(rawDate.split("[")[0]);

        if (isNaN(inputStringDate.getTime())) {
            throw new Error('Invalid date value');
        }
    
        const dateFormatter = new Intl.DateTimeFormat('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' });
        const timeFormatter = new Intl.DateTimeFormat('en-US', { hour: '2-digit', minute: '2-digit', hour12: true });
        const date = dateFormatter.format(inputStringDate);
        const time = timeFormatter.format(inputStringDate);
        return {date, time}
                
    }
    async orgName(find){
        const orgProf = await this.getProfileWithRetry(find);
        const first = orgProf.profileModel.firstName;
        const last = orgProf.profileModel.lastName
        return { first,last }
    }
    async populateDetails(){
        const details = await this.dataStore.get("event");
        document.getElementById("name").textContent = details.eventModel.name +"             ";
        const dateTime = await this.convertDateTimeToBrowserTimeZone(details.eventModel.dateTime);
        document.getElementById("date").textContent = dateTime.date;
        document.getElementById("time").textContent = dateTime.time;
        const org = await this.orgName(details.eventModel.eventCreator);
        document.getElementById("eventCreator").textContent = org.first +" "+ org.last;
        document.getElementById("location-text").textContent = details.eventModel.eventAddress;
        document.getElementById("category").textContent = details.eventModel.category;
        document.getElementById("description-text").textContent = details.eventModel.description;
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
    async attending(){
        const attendees = await this.dataStore.get("event")
        console.log(attendees.eventModel.attendees);
        let id;
        for(id of attendees.eventModel.attendees){
            console.log(id);
            await this.createAttendingIcons(id);
            await this.delay(1000); 
        }
    }

    async createAttendingIcons(result){
    
        try {
            const getName = await this.getProfileWithRetry(result);
            const profileId = getName.profileModel.profileId;
            const profileFName = getName.profileModel.firstName;
            const profileLName = getName.profileModel.lastName;
            // Create an anchor element
            const anchor = document.createElement('a');
            anchor.setAttribute('href', 'foriegnView.html?id='+profileId);
            anchor.className = 'nav-link px-4 d-flex flex-column align-items-center smprofile pt-6';
            anchor.id = 'foreignPic' +profileId;

            // Create an icon element
            const icon = document.createElement('i');
            icon.className = 'bi bi-person-circle nav-profile-icon-sm pt-6';

            // Create an H3 element
            const name = document.createElement('H3');
            name.className = 'names text-following pt-6';
            name.id = 'names';
            name.textContent = profileFName + " " + profileLName;
            
            //Center the profilepic
            anchor.style.position = 'relative';
            anchor.style.textAlign = 'center';
            icon.style.position = 'absolute';
            icon.style.top = '-40px';
            // Set a fixed height for the anchor element
            anchor.style.height = '95px';

            // Append elements
            anchor.appendChild(name);
            anchor.appendChild(icon);
            document.getElementById("allFollowingList").appendChild(anchor);
        } catch (error) {
            console.error(`Error while fetching profile for ID ${result}:`, error);
        }
        

    }
    async redirectEditEvent(){
        const eventId = await this.dataStore.get("eventId");
        window.location.href ='/createEvents.html?id=' + eventId;
      

    }
    redirectEditProfile(){
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
    const eventDetails = new EventDetails();
    eventDetails.mount();
};

window.addEventListener('DOMContentLoaded', main);
