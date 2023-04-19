import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class ViewProfile extends BindingClass {
    constructor() {+
        super();
        this.bindClassMethods(['clientLoaded', 'mount','thisPageRemoveFrom','redirectEditProfile','redirectAllEvents',
        'redirectCreateEvents','redirectAllFollowing','logout','addEvents','addPersonalEvents','addName','addFollowing'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        // console.log("viewprofile constructor");
    }

    /**
     * Once the client is loaded, get the profile metadata.
     */
    async clientLoaded() {
        // const urlParams = new URLSearchParams(window.location.search);
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        this.dataStore.set('profile', profile);

        this.dataStore.set('events', profile.profileModel.events);
        this.dataStore.set('firstName', profile.profileModel.firstName);
        this.dataStore.set('lastName', profile.profileModel.lastName);
        this.dataStore.set('following', profile.profileModel.following);
        this.addEvents();
        this.addPersonalEvents();
        this.addName();
        this.addFollowing();
        

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
        document.getElementById('names').innerText = "Loading Profile ...";
        document.getElementById('personalEventResults').innerText = "Loading Personal Events...";
        document.getElementById("allFollowingListText").innerText = "Loading People You Follow...";
        //this.header.addHeaderToPage();

        this.client = new dannaClient();
        this.clientLoaded();
    }

    async addEvents(){
        const events = this.dataStore.get("events");
        if (events == null) {
            document.getElementById("event-list").innerText = "No Events added in your Profile";
        } else {
            let eventResult;
            let counter = 0;
            for (eventResult of events) {
                const resulting = await this.client.getEventDetails(eventResult);
                counter += 1
                const anchor = document.createElement('tr');
                const th = document.createElement('th');
                th.setAttribute("scope", "row");
                th.innerText = counter;
                const eventName = document.createElement('td');
                eventName.innerText = eventResult;
                const rawDate = resulting.eventModel.dateTime;
                try {
                    const inputStringDate = new Date(rawDate.split("[")[0]);

                    if (isNaN(inputStringDate.getTime())) {
                        throw new Error('Invalid date value');
                    }
                
                    const dateFormatter = new Intl.DateTimeFormat('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' });
                    const timeFormatter = new Intl.DateTimeFormat('en-US', { hour: '2-digit', minute: '2-digit', hour12: false });
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
                    const eventCancel = document.createElement('td');
                    // eventCancel.innerText = "NEED button to cancel here";
                    const removeBtn = document.createElement('button');
                    removeBtn.innerText = "Cancel";
                    removeBtn.className= "btn btn-dark";
                    removeBtn.id = eventResult + "btn";
                    removeBtn.addEventListener('click', (function(result) {
                        return function() {
                          this.thisPageRemoveFrom(result);
                        };
                      })(eventResult).bind(this));
                      removeBtn.id = eventResult + "btn";
                    eventCancel.appendChild(removeBtn);
                    anchor.appendChild(th);
                    anchor.appendChild(eventName);
                    anchor.appendChild(eventDate);
                    anchor.appendChild(eventTime);
                    anchor.appendChild(eventLocation);
                    anchor.appendChild(eventOrg);
                    anchor.appendChild(eventCancel);
                    document.getElementById("event-list").appendChild(anchor);
                    
                } catch (error) {
                    console.error("Error adding events");
                }
                
                
            }
        }
    }


    async thisPageRemoveFrom(result){
        this.client.removeEventFromProfile(result);
    }
    
    async addPersonalEvents(){
        const events = this.dataStore.get("events");
        if (events == null) {
            document.getElementById("created-event-list").innerText = "No Events created by you in your Profile";
        }
        document.getElementById("created-event-list").innerText = events;
    }

    async addName(){
        const fname = this.dataStore.get("firstName");
        const lname = this.dataStore.get("lastName");
        if (fname == null) {
            document.getElementById("names").innerText = "John Doh";
        }
        document.getElementById("names").innerText = fname + " " + lname;
    }

    async addFollowing(){
        const following = this.dataStore.get("following");
        if (following == null) {
            document.getElementById("allFollowingListText").remove()
            document.getElementById("allFollowingList").innerText = "You are not following anyone";
        } else {
            let profileFollowing;
        for (profileFollowing of following) {
            const getName = await this.client.getProfile(profileFollowing);
            // Create an anchor element
            const anchor = document.createElement('a');
            anchor.setAttribute('href', '#');
            anchor.className = 'nav-link px-4 d-flex flex-column align-items-center';
            anchor.id = 'foreignPic' + getName.profileModel.getName;
    
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
        window.location.href = '/createProfile.html';
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
    const viewProfile = new ViewProfile();
    viewProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);
