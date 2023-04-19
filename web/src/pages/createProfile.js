import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class CreateProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount','showConfirmationModal','submitFormData', 'redirectEditProfile','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout','setPlaceholders'], this);
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
        document.getElementById('submit-btn').addEventListener('click', this.showConfirmationModal);
        // document.getElementById('cancel-Btn').addEventListener('click', this.closeModal);
        // document.getElementById('confirm-Btn').addEventListener('click', this.submitFormData);

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

    }

    async  showConfirmationModal() {
        console.log('called');
        const firstName = document.getElementById('fname').value;
        const lastName = document.getElementById('lname').value;
        const dob = document.getElementById('dob').value;
        const location = document.getElementById('location').value;
        const gender = document.getElementById('gender').value;
      
        // create the confirmation message with the form data
        const confirmationMessage = `Please confirm the following details: \n\nFirst Name: ${firstName}\nLast Name: ${lastName}\nDate of Birth: ${dob}\nLocation: ${location}\nGender: ${gender}`;
        const message = document.getElementById('confirmation-message');
        message.textContent = confirmationMessage;
        const modalShow = document.getElementById('confirmation-modal');
        // modalShow.setAttribute()

    }

    async submitFormData(evt){
        evt.preventDefault();
        const firstName = document.getElementById('fname').value;
        const lastName = document.getElementById('lname').value;
        const dob = document.getElementById('dob').value;
        const location = document.getElementById('location').value;
        const gender = document.getElementById('gender').value;

        const profile = await this.client.createProfile(firstName, lastName, dob, location, gender, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
        });

        this.dataStore.set('profile', profile);
        window.location.href = '/viewProfile.html';
    }

    async closeModal() {
        const modal = document.querySelector('.modal.is-active'); // get the active modal element
        if (modal) {
          modal.classList.remove('is-active'); // remove the "is-active" class to close the modal
        }
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
        window.location.href ='/landingPage.html';
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