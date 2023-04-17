import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class ViewProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'redirectEditProfile','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout','addEvents','addPersonalEvents','addName','addFollowing'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addEvents);
        this.dataStore.addChangeListener(this.addPersonalEvents);
        this.dataStore.addChangeListener(this.addName);
        this.dataStore.addChangeListener(this.addFollowing);
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
        console.log(profile);
        

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
        document.getElementById('eventResults').innerText = "Loading Events ...";
        document.getElementById('personalEventResults').innerText = "Loading Personal Events...";
        document.getElementById("allFollowingList").innerText = "Loading People You Follow...";

        //this.header.addHeaderToPage();

        this.client = new dannaClient();
        this.clientLoaded();
    }

    async addEvents(){
        const events = this.dataStore.get("events");
        if (events == null) {
            document.getElementById("eventResults").innerText = "No Events added in your Profile";
        }
        document.getElementById("eventResults").innerText = events;
    }

    async addPersonalEvents(){
        const events = this.dataStore.get("events");
        if (events == null) {
            document.getElementById("eventResults").innerText = "No Events created by you in your Profile";
        }
        document.getElementById("eventResults").innerText = events;
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

        let profileHtml = '';
        let profileFollowing;
        for (profileFollowing of following) {
            document.getElementById("allFollowingList").appendChild('<a href="#" class="nav-link align-middle px-0" id="foreignPic">'+' <i class="bi bi-person-circle"></i>'+'<span class="ms-1 d-none d-sm-inline"><H3 class="names" id="names">'+profileFollowing + '</H3></span></a>');   
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
