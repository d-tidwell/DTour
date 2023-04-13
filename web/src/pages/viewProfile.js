import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'redirectEditProfile','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("viewprofile constructor");
    }

    /**
     * Once the client is loaded, get the profile metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const profileId = urlParams.get('id');
        const profile = await this.client.getProfile(profileId);
        this.dataStore.set('profile', profile);
        document.getElementById('names').innerText = "Loading Profile ...";
        document.getElementById('eventResults').innerText = "Loading Events ...";
        document.getElementById('personalEventResults').innerText = "Loading Personal Events...)";
        document.getElementById("followingList").innerText = "Loading People You Follow..."

    }
        /**
     * Add the header to the page and load the dannaClient.
     */
    mount() {
        const profile = this.dataStore.get("profile");
        if (profile == null) {
            return;
        }
        document.getElementById("names").innerText = profile.name;
        document.getElementById("eventResults").innerText = profile.eventResults;
        document.getElementById("personalEventResults").innerText = profile.eventResults;
        document.getElementById("allFollowingList").innerText = profile.eventResults;
        document.getElementById('profilePic').addEventListener('click', this.redirectEditProfile);
        document.getElementById('allEvents').addEventListener('click', this.redirectAllEvents);
        document.getElementById('createEvents').addEventListener('click', this.redirectCreateEvents);
        document.getElementById('allFollowing').addEventListener('click', this.redirectAllFollowing);
        document.getElementById('logout').addEventListener('click', this.logout);

        // this.header.addHeaderToPage();

        this.client = new dannaClient();
        this.clientLoaded();
    }

    redirectEditProfile(){
        window.location.href = '/createProfile.html';
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
