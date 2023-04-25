import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class FViewProfile extends BindingClass {
    constructor() {+
        super();
        this.bindClassMethods(['clientLoaded', 'mount','thisPageRemoveFrom','redirectEditProfile','redirectAllEvents','delay',
        'redirectCreateEvents','redirectAllFollowing','logout','addEvents','addPersonalEvents','addName','addFollowing','addToFollowing','getEventWithRetry'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        // console.log("viewprofile constructor");
    }

    /**
     * Once the client is loaded, get the profile metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        const foriegnId = urlParams.get("id");
        if (foriegnId) {
          const theirProfile = await this.getProfileWithRetry(foriegnId);
          this.dataStore.set("foriegn", theirProfile);
          this.dataStore.set('events', theirProfile.profileModel.events);
          this.dataStore.set('TlastName', theirProfile.profileModel.lastName);
          this.dataStore.set('following', theirProfile.profileModel.following);
          this.dataStore.set('TfirstName', theirProfile.profileModel.firstName);
        } else {
          console.error('id not found in the URL');
        }
        const fols = profile.profileModel.following;
        await this.checkFollowing(fols, foriegnId);
        this.dataStore.set("email", identity.email);
        this.dataStore.set('profile', profile);
        this.dataStore.set('firstName', profile.profileModel.firstName);
        this.dataStore.set('lastName', profile.profileModel.lastName);

        await this.addEvents();
        await this.addPersonalEvents();
        await this.addName();
        await this.addFollowing();
        

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
        document.getElementById('follow-btn').addEventListener('click', this.addToFollowing);
        document.getElementById('names').innerText = "Loading Profile ...";

        this.client = new dannaClient();
        this.clientLoaded();
    }
    async checkFollowing(fols, foriegnId){
        if(fols.includes(foriegnId)){
            document.getElementById('follow-btn').innerText = "remove";
        }
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
    async getEventWithRetry(result, maxRetries = 3, delayMs = 1000) {
        let retries = 0;
        let getEvent;
    
        while (retries < maxRetries) {
            try {
                getEvent = await this.client.getEventDetails(result);
                if (getEvent && getEvent.eventModel) {
                    return getEvent;
                }
            } catch (error) {
                console.error(`Error while fetching profile for ID ${result}:`, error);
            }
    
            retries++;
            await this.delay(delayMs);
        }
    
        throw new Error(`Failed to get profile for ID ${result} after ${maxRetries} retries.`);
    }
    async addToFollowing(){
        const profAdd = this.dataStore.get("foriegn");
        if(document.getElementById("follow-btn").innerText === 'remove'){
            await this.client.removeFromFollowing(profAdd.profileModel.profileId);
        } else {
            await this.client.addToFollowing(profAdd.profileModel.profileId);
            
        }
        window.location.href = "/profile.html";
     
        
    }
    async addEvents(){
        const events = await this.dataStore.get("events");
        const fprof = await this.dataStore.get('foriegn');
        const email = fprof.profileModel.profileId;
        if (events == null) {
            document.getElementById("event-list").innerText = "No Events added in your Profile";
        } else {
            let eventResult;
            let counter = 0;
            for (eventResult of events) {
                const resulting =  await this.getEventWithRetry(eventResult);
                if((resulting.eventModel.eventCreator !== email )){

                    counter += 1
                    const anchor = document.createElement('tr');
                    const th = document.createElement('th');
                    th.setAttribute("scope", "row");
                    th.innerText = counter;
                    const eventId = document.createElement('td');
                    const idlink = document.createElement('a');
                    idlink.setAttribute('href', 'eventDetails.html?id='+resulting.eventModel.eventId); 
                    idlink.style.color ="#212524";
                    idlink.innerText = resulting.eventModel.eventId;
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
                        const eventLocation = document.createElement('td');
                        eventLocation.innerText = resulting.eventModel.eventAddress;
                        const eventOrg = document.createElement('td');
                        const foriegnProfile = resulting.eventModel.eventCreator;
                        const realName = await this.client.getProfile(foriegnProfile);
                        eventOrg.innerText = realName.profileModel.firstName + " "+ realName.profileModel.lastName;
                        eventId.appendChild(idlink);
                        anchor.appendChild(th);
                        anchor.appendChild(eventId);
                        anchor.appendChild(eventName);
                        anchor.appendChild(eventDate);
                        anchor.appendChild(eventTime);
                        anchor.appendChild(eventLocation);
                        anchor.appendChild(eventOrg);
                        document.getElementById("event-list").appendChild(anchor);  
                        
                    } catch (error) {
                        console.error("Error adding events");
                    }
                }
                
                
            }
        }
    }

    async thisPageRemoveFrom(result){
        await this.client.removeEventFromProfile(result);
        window.location.href = "/profile.html";
    }
    
    async addPersonalEvents(){
        let checkArray = [];
        const events = await this.dataStore.get("events");
        const fprof = await this.dataStore.get('foriegn');
        const email = fprof.profileModel.profileId;
        if (events == null) {
            document.getElementById("created-event-list").innerText = "No Events created by you in your Profile";
        } else {
            let eventResult;
            let counter = 0;
            for (eventResult of events) {
                const resulting = await this.getEventWithRetry(eventResult);
                if(resulting){
                    if(( resulting.eventModel.eventCreator === email)){
                        counter += 1
                        checkArray.push(eventResult);
                        const anchor = document.createElement('tr');
                        const th = document.createElement('th');
                        th.setAttribute("scope", "row");
                        th.innerText = counter;
                        const eventId = document.createElement('td');
                        const idlink = document.createElement('a');
                        idlink.setAttribute('href', 'eventDetails.html?id='+resulting.eventModel.eventId); 
                        idlink.style.color ="#212524";
                        idlink.innerText = resulting.eventModel.eventId;
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
                            const eventLocation = document.createElement('td');
                            eventLocation.innerText = resulting.eventModel.eventAddress;
                            eventId.appendChild(idlink);
                            anchor.appendChild(th);
                            anchor.appendChild(eventId);
                            anchor.appendChild(eventName);
                            anchor.appendChild(eventDate);
                            anchor.appendChild(eventTime);
                            anchor.appendChild(eventLocation);
                            document.getElementById("created-event-list").appendChild(anchor);  
                            
                            } catch (error) {
                                console.error("Error adding events");
                            }
                        }
                    
               
                }
                document.addEventListener("DOMContentLoaded", function() {
                    document.getElementById("personalEventResults").remove();
                  });
                    }
            if(checkArray.length == 0){
                document.getElementById("personalEventResults").innerText = "You Should Try Creating an Event!!";
            }      
        }
    }

    async addName(){
        const fname = await this.dataStore.get("firstName");
        const lname = await this.dataStore.get("lastName");
        const Tfname = await this.dataStore.get("TfirstName");
        const Tlname = await this.dataStore.get("TlastName");
        document.getElementById("names").innerText = fname + " " + lname;
        document.getElementById("theirNames").innerText = Tfname + " " + Tlname;
    }

    async addFollowing(){
        const following = await this.dataStore.get("following");
        if (following == null) {
            document.getElementById("allFollowingListText").remove()
            document.getElementById("allFollowingList").innerText = "You are not following anyone";
        } else {
        let profileFollowing;
        for (profileFollowing of following) {
            const getName = await this.client.getProfile(profileFollowing);
            // Create an anchor element
            const anchor = document.createElement('a');
            anchor.setAttribute('href', 'foriegnView.html?id='+getName.profileModel.profileId);
            anchor.className = 'nav-link px-4 d-flex flex-column align-items-center smprofile';
            anchor.id = 'foreignPic' + getName.profileModel.profileId;
    
            // Create an icon element
            const icon = document.createElement('i');
            icon.className = 'bi bi-person-circle nav-profile-icon-sm';

            // Create an H3 element
            const name = document.createElement('H3');
            name.className = 'names text-following';
            name.id = 'names';
            name.textContent = getName.profileModel.firstName + " " + getName.profileModel.lastName;
            
            //Center the profilepic
            anchor.style.position = 'relative';
            anchor.style.textAlign = 'center';
            icon.style.position = 'absolute';
            icon.style.top = '-40px';

            // Append elements
            anchor.appendChild(name);
            anchor.appendChild(icon);
            document.getElementById("allFollowingList").appendChild(anchor);
        }
        document.getElementById("allFollowingListText").remove();

        }
    
        
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
    const fviewProfile = new FViewProfile();
    fviewProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);
