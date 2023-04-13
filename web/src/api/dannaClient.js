import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the DannaAPIService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class DannaClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getProfile', 'getAllEvents', 'getEventDetails','createEvent',
                                'createProfile','updateProfile','updateEvent','addEventToProfile','removeEventFromProfile','addToFollowing',
                                'removeFromFollowing'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the profile for the given ID.
     * @param id Unique identifier for a profile
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The profile's metadata.
     */
    async getProfile(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`profiles/${id}`);
            return response.data.profile;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

   /**
    * @param  errorCallback (Optional) a funciton to execute on a failed call
    * @returns all the events
    */
    async getAllEvents(errorCallback) {
        try {
            const response = await this.axiosClient.get(`events/all/`);
            return response.data.events;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    
    /**
    * 
    * @param  id Unique identifier for a event
    * @param  errorCallback (Optional) a funciton to execute on a failed call
    * @returns all the event details for id
    */
    async getEventDetails(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`events/${id}`);
            return response.data.event;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * 
     * @param  id Unique identifier for profile
     * @param  fname first name
     * @param  lname last name
     * @param  location location
     * @param  gender gender
     * @param  dob date of birth as a string
     * @param  errorCallback 
     * @returns profile metadata
     */
    async createProfile(id, fname, lname, location, gender, dob, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create a profile.");
            const response = await this.axiosClient.post(`profiles/createProfile`, {
                firstName: firstName,
                lastName: lastName,
                location: location,
                gender: gender,
                dateOfBirth: dateOfBirth
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data.profile;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * 
     * @param  id Unique identifier for profile
     * @param  fname first name
     * @param  lname last name
     * @param  location location
     * @param  gender gender
     * @param  dob date of birth as a string
     * @param  errorCallback 
     * @returns profile metadata
     */

    async updateProfile(id, fname, lname, location, gender,dob, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create a profile.");
            const response = await this.axiosClient.put(`profiles/${id}`, {
                firstName: firstName,
                lastName: lastName,
                location: location,
                gender: gender,
                dateOfBirth: dateOfBirth
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data.profile;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * 
     * @param  name name of the event
     * @param  date date of the event
     * @param  time time of the event
     * @param  address address of the event 
     * @param  category category of the event
     * @param  description description of the event
     * @param  errorCallback 
     * @returns the event
     */
    async createEvent(name, date, time, address, category, description, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create an event.");
            const response = await this.axiosClient.post(`events/create`, {
                name: name,
                date: date,
                time: time,
                address: address,
                category: category,
                description: description
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'

                }
            });
            return response.data.event;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

      /**
     * 
     * @param  name name of the event
     * @param  date date of the event
     * @param  time time of the event
     * @param  address address of the event 
     * @param  category category of the event
     * @param  description description of the event
     * @param  errorCallback 
     * @returns the event
     */
    async updateEvent(name, date, time, address, category, description, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update events.");
            const response = await this.axiosClient.put(`events/${id}`, {
                name: name,
                date: date,
                time: time,
                address: address,
                category: category,
                description: description,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data.event;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * 
     * @param {*} id Unique identifyer of the profile
     * @param {*} eventId Unique identifyer of the event
     * @param {*} errorCallback 
     * @returns 
     */
    async addEventToProfile(eventId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create a profile.");
            const response = await this.axiosClient.put(`profiles/addEvent`, {
                eventId: eventId
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data.events;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * 
     * @param {*} id Unique identifyer of the profile
     * @param {*} eventId Unique identifyer of the event
     * @param {*} errorCallback 
     * @returns 
     */
    async removeEventFromProfile(eventId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create a profile.");
            const response = await this.axiosClient.put(`profiles/removeEvent`, {
                eventId: eventId
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data.profile.events;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * 
     * @param {*} id Unique identifyer of the profile
     * @param {*} profileId Unique identifyer of the profile to add to the following list
     * @param {*} errorCallback 
     * @returns 
     */
    async addToFollowing(profileId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create a profile.");
            const response = await this.axiosClient.put(`profiles/addFollowing`, {
                profileId: profileId
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data.profile.following;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
     /**
     * 
     * @param {*} id Unique identifyer of the profile
     * @param {*} profileId Unique identifyer of the profile to remove from the following list
     * @param {*} errorCallback 
     * @returns 
     */
    async removeFromFollowing(profileId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create a profile.");
            const response = await this.axiosClient.put(`profiles/removeFollowing`, {
                profileId: profileId
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data.profile.following;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
