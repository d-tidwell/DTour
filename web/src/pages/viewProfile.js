import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class ViewProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'redirectEditProfile','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout','addEvents','addPersonalEvents','addName','addFollowing'], this);
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
        console.log("Identity", identity);
        const profile = await this.client.getProfile(identity.email);
        console.log("getting..." + identity.email);
        this.dataStore.set('profile', profile);
        this.dataStore.set('events', profile.profileModel.events);
        this.dataStore.set('firstName', profile.profileModel.firstName);
        this.dataStore.set('lastName', profile.profileModel.lastName);
        this.dataStore.set('following', profile.profileModel.following);
        console.log("checking after client load profile", this.dataStore.get("profile"));
        console.log("checking after client load profile events", this.dataStore.get("events"));
        console.log("checking after client load firstname", this.dataStore.get("firstName"));
        console.log("checking after client load following", this.dataStore.get("following"));
        this.addEvents();
        this.addPersonalEvents();
        this.addName();
        this.addFollowing();
        console.log("client loaded - methods called");
        

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
                console.log(resulting);
                counter += 1
                const anchor = document.createElement('tr');
                const th = document.createElement('th');
                th.setAttribute("scope", "row");
                th.innerText = counter;
                const eventName = document.createElement('td');
                eventName.innerText = eventResult;
                const eventDate = document.createElement('td');
                eventDate.innerText = resulting.dateTime;
                const eventTime = document.createElement('td');
                eventTime.innerText = resulting.dateTime;
                const eventLocation = document.createElement('td');
                eventLocation.innerText = resulting.address;
                const eventOrg = document.createElement('td');
                eventOrg.innerText = resulting.createdBy;
                const eventCancel = document.createElement('td');
                // eventCancel.innerText = "NEED button to cancel here";
                const removeBtn = document.createElement('button');
                removeBtn.innerText = "Cancel";
                removeBtn.className("btn btn-light-custom");
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
            }
        }
    }

    async addPersonalEvents(){
        const events = this.dataStore.get("events");
        if (events == null) {
            document.getElementById("personalEventResults").innerText = "No Events created by you in your Profile";
        }
        document.getElementById("personalEventResults").innerText = events;
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
            document.getElementById("allFollowingList").innerText = "You are not following anyone";
        }
    
        let profileFollowing;
        for (profileFollowing of following) {
            const getName = await this.client.getProfile(profileFollowing);
            // Create an anchor element
            const anchor = document.createElement('a');
            anchor.setAttribute('href', '#');
            anchor.className = 'nav-link align-middle px-0';
            anchor.id = 'foreignPic';
    
            // Create an icon element
            const icon = document.createElement('i');
            icon.className = 'bi bi-person-circle nav-profile-icon-sm';
    
            // Create a span element
            const span = document.createElement('span');
            span.className = 'ms-1 d-none d-sm-inline';
    
            // Create an H3 element
            const name = document.createElement('H3');
            name.className = 'names';
            name.id = 'names';
            name.textContent = getName.profileModel.getName;
    
            // Append elements
            span.appendChild(name);
            anchor.appendChild(icon);
            anchor.appendChild(span);
            document.getElementById("allFollowingList").appendChild(anchor);
        }
        document.getElementById("allFollowingListText").remove();
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
