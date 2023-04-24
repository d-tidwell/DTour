import dannaClient from '../api/dannaClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/dannaHeader';
import DataStore from "../util/DataStore";

class CreateProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'redirectProfile','mount','convertToDateTime','confirmRedirect','submitFormData','redirectAllEvents','redirectCreateEvents','redirectAllFollowing','logout','setPlaceholders'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        // console.log("viewprofile constructor");
    }

    async clientLoaded() {
        // const urlParams = new URLSearchParams(window.location.search);
        const identity = await this.client.getIdentity();
        this.dataStore.set('id', identity.email);
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
  
        document.getElementById('profilePic').addEventListener('click', this.redirectProfile);
        document.getElementById('allEvents').addEventListener('click', this.redirectAllEvents);
        document.getElementById('createEvents').addEventListener('click', this.redirectCreateEvents);
        document.getElementById('allFollowing').addEventListener('click', this.redirectAllFollowing);
        document.getElementById('logout').addEventListener('click', this.logout);
        document.getElementById('door').addEventListener('click', this.logout);
        document.getElementById('confirm').addEventListener('click', this.confirmRedirect);
        document.getElementById('submited').addEventListener('click', this.submitFormData);

      

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

    async convertToDateTime(dateString, timeString) {
        // Get the current timezone
        const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
      
        // Parse the date string
        const [month, day, year] = dateString.split('/').map(part => parseInt(part, 10));
      
        // Parse the time string and convert to 24-hour format
        const [timeValue, timePeriod] = timeString.split(' ');
        let [hours, minutes] = timeValue.split(':').map(part => parseInt(part, 10));
        if (timePeriod === 'PM' && hours !== 12) {
          hours += 12;
        } else if (timePeriod === 'AM' && hours === 12) {
          hours = 0;
        }
      
        // Create a Date object using the parsed values
        const date = new Date(year, month - 1, day, hours, minutes);
      
        // Convert date object to an ISO string
        const isoString = date.toISOString();
      
        // Get the offset in minutes and convert it to hours and minutes
        const offset = -date.getTimezoneOffset();
        const offsetSign = offset >= 0 ? "+" : "-";
        const offsetHours = Math.floor(Math.abs(offset) / 60);
        const offsetMinutes = Math.abs(offset) % 60;
        const formattedOffset = `${offsetSign}${String(offsetHours).padStart(2, "0")}:${String(offsetMinutes).padStart(2, "0")}`;
      
        // Get the date and time parts from the ISO string
        const [datePart, timePart] = isoString.split("T");
        const timeWithoutMillis = timePart.split(".")[0];
      
        // Assemble the final ZonedDateTime string
        const zonedDateTimeString = `${datePart}T${timeWithoutMillis}${formattedOffset}[${timezone}]`;
      
        return zonedDateTimeString;
    }

    async submitFormData(evt){
        evt.preventDefault();
        const firstName = document.getElementById('fname').value || document.getElementById('fname').getAttribute('placeholder');
        const lastName = document.getElementById('lname').value ||  document.getElementById('lname').getAttribute('placeholder');
        const dobraw = document.getElementById('dob').value ||  document.getElementById('dob').getAttribute('placeholder');
        const dob = await this.convertToDateTime(dobraw, '12:01 AM')
        const location = document.getElementById('location').value ||  document.getElementById('location').getAttribute('placeholder');
        const gender = document.getElementById('gender').value || document.getElementById('gender').getAttribute('placeholder');
        console.log(firstName, lastName, dob, location, gender);
        let profile;
        if(document.getElementById('welcome').innerText == "Welcome! First Lets Make Your Profile!"){
            profile = await this.client.createProfile(firstName, lastName, location, gender, dob, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
            });
        } else {
            profile = await this.client.updateProfile(this.dataStore.get('id'),firstName, lastName, location, gender, dob, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
            });
        }
        

        this.dataStore.set('profile', profile);
        document.getElementById('fnameC').innerText = firstName || profile.profileModel.firstName;
        document.getElementById('lnameC').innerText = lastName || profile.profileModel.lastName;
        document.getElementById('dobC').innerText = dob || profile.profileModel.dateOfBirth;
        document.getElementById('loC').innerText = location || profile.profileModel.location;
        document.getElementById('genderC').innerText = gender ||profile.profileModel.gender;
        document.getElementById('loading-modal').remove();
        
    }
    confirmRedirect() {
        window.location.href = '/profile.html';
    }
    redirectProfile(){
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
    const createProfile = new CreateProfile();
    createProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);