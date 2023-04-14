import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class ViewProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'redirectEditProfile','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addEvents);
        this.dataStore.addChangeListener(this.addPersonalEvents);
        this.dataStore.addChangeListener(this.addName);
        this.dataStore.addChangeListener(this.addFollowing);
        this.header = new Header(this.dataStore);
        // console.log("viewprofile constructor");
    }

    /**
     * Once the client is loaded, get the profile metadata and song list.
     */
    async clientLoaded() {
        // const urlParams = new URLSearchParams(window.location.search);
        const identity = this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        console.log("getting..." + identity.email);
        this.dataStore.set('profile', profile);
        document.getElementById('names').innerText = "Loading Profile ...";
        document.getElementById('eventResults').innerText = "Loading Events ...";
        document.getElementById('personalEventResults').innerText = "Loading Personal Events...)";
        document.getElementById("followingList").innerText = "Loading People You Follow...";

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

        //this.header.addHeaderToPage();

        this.client = new dannaClient();
        this.clientLoaded();
    }

    async addEvents(){
        const profile = this.dataStore.get("profile");
        if (profile == null) {
            return;
        }
        document.getElementById("eventResults").innerText = profile.events;
    }

    async addPersonalEvents(){
        const profile = this.dataStore.get("profile");
        if (profile == null) {
            return;
        }
        document.getElementById("personalEventResults").innerText = profile.events;
    }

    async addName(){
        const profile = this.dataStore.get("profile");
        if (profile == null) {
            return;
        }
        document.getElementById("names").innerText = profile.name;
    }

    async addFollowing(){
        const profile = this.dataStore.get("profile");
        if (profile == null) {
            return;
        }
        document.getElementById("allFollowingList").innerText = profile.following;
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
    logout(){
        this.client.logout;
        window.location.href ='/landingPage.html';
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
