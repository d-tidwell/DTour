## Profile
POST Create a new profile accepts all profile information needed to create a Profile object 
GET endpoint uses email (id) and returns all of the profile information to include:
    User Name for profile
    User Age
    User Gender
    User Location
    List of all events RSVP'd to (the ones in the ProfileModel list of events they RSVP's)
    List of all the events that were created by user (this will be events createdBy parameter)
    List of all followers from Profile Model
PUT  all profile fields to edit the Profile information of age Gender location



## Event
-Make sure there is a string for event description in the Event/ Event model
- Make sure there is a string for event createdBy in the Event/Event model
POST accepts all event information needed to create a new event by user (make sure to add createdBy for a parameter)
GET ALL EVENTS with the eventId, name, date/time, location, createdBy
GET Event Details accepts eventid for a single page view all event info all model parameters
POST to add event to Profile needs to accept the eventId and add to current users email
PUT Edit event details accepts all event fields to edit the Event information
DELETE remove event from Profile

## MISC
POST accepts an email of the person you want to add to the current session users email list of follower
DELETE remove by email of the person you want to remove from the current sessions email list of following