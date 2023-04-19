import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class CreateProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount','modalPop','submitFormData', 'redirectEditProfile','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout','setPlaceholders'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        // console.log("viewprofile constructor");
    }

    async clientLoaded() {
        // const urlParams = new URLSearchParams(window.location.search);
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        this.dataStore.set('profile', profile);
        if(profile == null) {
            document.getElementById("welcome").innerText = "Welcome! First Lets Make Your Profile!"
        }
        document.getElementById("loading").innerText = "Loading.....";
        document.getElementById("fname").setAttribute('placeholder', 'First Name');
        document.getElementById("lname").setAttribute('placeholder', 'Last Name');
        document.getElementById("dob").setAttribute('placeholder', 'Date of Birth');
        document.getElementById("location").setAttribute('placeholder', 'Location');
        document.getElementById("gender").setAttribute('placeholder', 'Gender');
        this.setPlaceholders();

    }

    mount() {
  
        document.getElementById('profilePic').addEventListener('click', this.redirectEditProfile);
        document.getElementById('allEvents').addEventListener('click', this.redirectAllEvents);
        document.getElementById('createEvents').addEventListener('click', this.redirectCreateEvents);
        document.getElementById('allFollowing').addEventListener('click', this.redirectAllFollowing);
        document.getElementById('logout').addEventListener('click', this.logout);
        document.getElementById('door').addEventListener('click', this.logout);
        document.getElementById('confirm').addEventListener('click', this.submitFormData);
        document.getElementById('submited').addEventListener('click', this.modalPop());

        // this.header.addHeaderToPage();

        this.client = new dannaClient();
        this.clientLoaded();
    }

    async setPlaceholders(){
        const profile = this.dataStore.get("profile");
        console.log("this one",profile)
        if (profile == null) {
            return;
        }
        if (profile.profileModel.firstName && profile.profileModel.lastName) {
            document.getElementById("names").innerText =  profile.profileModel.firstName + " " +  profile.profileModel.lastName
            document.getElementById('fname').setAttribute('placeholder', profile.profileModel.firstName);
            document.getElementById('lname').setAttribute('placeholder', profile.profileModel.lastName);
        }
        if (profile.profileModel.dateOfBirth) {
            document.getElementById('dob').setAttribute('placeholder',profile.profileModel.dateOfBirth);
        }
        if (profile.profileModel.location) {
            document.getElementById('location').setAttribute('placeholder',profile.profileModel.location);
        }
        if (profile.profileModel.gender) {
            document.getElementById('gender').setAttribute('placeholder',profile.profileModel.gender);
        }
        document.getElementById("loading").remove();
    }

    async modalPop(){
        const firstName = document.getElementById('fname').value;
        console.log(firstName, "HEERRREE");
        const lastName = document.getElementById('lname').value;
        const dob = document.getElementById('dob').value;
        const location = document.getElementById('lo').value;
        const gender = document.getElementById('gender').value;
        document.getElementById('fnameC').innerText = firstName;
        document.getElementById('lnameC').innerText = lastName;
        document.getElementById('dobC').innerText = dob;
        document.getElementById('loC').innerText = location;
        document.getElementById('genderC').innerText = gender;

    }

    async submitFormData(evt){
        evt.preventDefault();
        const firstName = document.getElementById('fnameC').value;
        const lastName = document.getElementById('lnameC').value;
        const dob = document.getElementById('dobC').value;
        const location = document.getElementById('loC').value;
        const gender = document.getElementById('genderC').value;

        const profile = await this.client.createProfile(firstName, lastName, dob, location, gender, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
        });

        this.dataStore.set('profile', profile);
        window.location.href = '/viewProfile.html';
    }

    redirectEditProfile(){
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
    logout(){
        this.client.logout();
    }
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createProfile = new CreateProfile();
    createProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);