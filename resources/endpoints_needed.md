## Profile

Profile & ProfileModel
-id (String (email from authentication))
-firstName (String)
-lastName (String)
-location (String)
-gender (String)
-dateOfBirth (DateTime)
-events (set of strings)
-following (set of strings)

Event & EventModel
-id (String needs to be hash generated for uniqueness)
-eventCreator (String)
-name (String)
-address (String)
-dateTime (DateTime)
-category (set of strings)
-description (string)
-attendees (set of string)

POST Create a new profile accepts all profile information needed to create a Profile object 
    endpoint: profile/create
    data: id, fname, lname, location, gender, dob
    response: all of the new created profile object data
    (use for returning data from createProfile.html,createProfile.js)
GET endpoint uses email (id) and returns all of the profile information to include:
    endpoint: profile/${id}
    data: none
    response: all of the associated data
    (use for viewing profile.html & .js, foreignProfile.html & .js)
PUT  all profile fields to edit the Profile information of age Gender location etc
    endpoint: profile/${id}
    data: fname, lname, location, gender,dob
    response: the updated profile data
    (use for updateProfile.html, updateProfile.js)

## Event
-Make sure there is a string for event description in the Event/ Event model
- Make sure there is a string for event createdBy in the Event/Event model
POST accepts all event information needed to create a new event by user (make sure to add createdBy for a parameter)
    endpoint:event/create
    data: name, date, time, address, category, description
    response:the new event data
GET ALL EVENTS with the eventId, name, date/time, location, createdBy
    endpoint:events/all/
    data: no data
    response: all results from dynamo
GET Event Details accepts eventid for a single page view all event info all model parameters
    endpoint: events/${id}
    data: no data
    response: all the event objects parameters
PUT to add event to Profile needs to accept the eventId and add to current users email
    endpoint: profile/addEvent
    data: event id
    response: updated list of the profile events
PUT Edit event details accepts all event fields to edit the Event information
    endpoint: event/${id}
    data: id, name, date, time, address, category, description
    response: updated event object all parameters
PUT remove event from Profile
    endpoint: profile/removeEvent
    data: event id to remove
    response: updated list of all events from profile
## MISC
PUT accepts an email of the person you want to add to the current session users email list of follower
    endpoint: profile/addFollowing
    data: profileId of the person to add to this profiles list
    response: updated profiles following list
PUT remove by email of the person you want to remove from the current sessions email list of following
    endpoint: profile/removeFollowing
    data: profileId of the person to remove from this profiles list
    response: updated profiles following list
