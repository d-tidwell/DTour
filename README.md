# NSS Midstone Group Project

## Tech Stack 
Backend: Java - AWS Cloudfront, Cognito, Cloudformation, Lambda, S3, Amplify, DynamoDB
Frontend: JavaScript, WebPack, BootStrap5, HTML5, Animated CSS, Node Package Manager

## Project Infrastructure

![Image 1: The architecture of your starter project](resources/images/architecture_diagram.png)

*Image 1: Architecture diagram for the @DTour project*

This project was a culmination of a two week intensive group back & front end build with no prior instruction in Webpack, JavaScript, HTML or CSS. Our team was given three days of concept and endpoint design time followed by 10 days of build time, and one day of presentation preparation to accomplish the deliverables and have a functional cloud API deployed to a front end web application.

A powerpoint outlining the project mission can be found here [@DTour PowerPoint](https://docs.google.com/presentation/d/1UVmT7e1QBgVxmuxp4RD5vVlZVpYPvCqlumgTPxhtTDg/edit?usp=sharing)

# Project Organization and Execution

Our concept was to deliver an event driven social media platform. Users would make profiles, create events, RSVP to events, follow friends and be able to update or delete events as needed. It was important that all events created were for future times and birthdays were caught for appropriate age related events and future data tasks.


## Planning
I created front end wire frames in [Moqup](https://moqups.com/) to outline the design aspect and page interactivity 
![Image: Profile View Wire Frame](https://github.com/d-tidwell/DTour/blob/main/resources/images/profileView.png)
![Image: All Events View Wire Frame](https://github.com/d-tidwell/DTour/blob/main/resources/images/allEventsView.png)
Additional Wire Frames and planning resources can been found [here](https://github.com/d-tidwell/DTour/blob/main/resources/images/allEventsView.png)

I defined the endpoint call structures and data shapes of inbound and outbound JSON of the API [Endpoint Definitions](https://github.com/d-tidwell/DTour/edit/main/resources/endpoints_needed.md)

I created a team job board in the GitHub Projects ticketing style.

We defined user stories together to further drill down from our conceptual expectations discussion.

From these 'user stories' . The tasks were delegated and outlined by segregating tickets by endpoint to prevent overlap in development to reduce merge conflicts in two person teams of our group of 5. Each members ticket expressly noted the class naming convention, special considerations of each task, where to find more information and how a ticket would be considered completed (i.e. cUrl the endpoint to test its functionality) before moving on to the next task. 

Each person developed there individual components and were to test them locally on their device using [SAM](https://aws.amazon.com/serverless/sam/) in conjunction with an AWS individual account deployment. Once a ticket or loop of an endpoint was completed the code was submitted via pull request and reviewed to be merged and deployed via the GitHub Pipeline to deployment on the remote AWS services.

## Results

As you can see below all User stories were completed and the front end enhanced with hover animations, background animations, site wide styling and realtime page hydration. Furthermore, I was able to expand beyond the sprint objectives to incorporate header based sorting mechanisms for each table.

Backend API tied to FrontEnd interactivity includes:
-User can add person to following list and dynamically the button and its event listener on the foriegn profile will turn to remove
-User can RSVP to events or cancel RSVP from both profile and all events views
-User can create - edit there own events but do not have the ability to access the edit screen of events they are not the creator of
-User can see the event details of events as well as those other users who have RSVP'd
-User can delete an event they created and it will be removed from their events schedule as well as those of who had RSVP's previously
-User can update their profile information
-User can circumvent front end UI and make API calls direct with token as header

### Landing Page
![Image: Deployed LandingPage](https://github.com/d-tidwell/DTour/blob/main/resources/images/LandingPage.png)
### Custom Cognito Login Page
![Image: Deployed Cognito Login](https://github.com/d-tidwell/DTour/blob/main/resources/images/CognitoIntegration.png)
### Profile View
![Image: Deployed Profile View](https://github.com/d-tidwell/DTour/blob/main/resources/images/ProfileView.png)
### All Events View
![Image: Deployed All Events](https://github.com/d-tidwell/DTour/blob/main/resources/images/AllEvents.png)


### Create / Edit Profile / Modal Confirmation
![Image: Deployed Edit Profile](https://github.com/d-tidwell/DTour/blob/main/resources/images/CreateProfile.png)
### Create / Edit Event / Modal Confirmation
![Image: Deployed Create Event](https://github.com/d-tidwell/DTour/blob/main/resources/images/createEvent.png)
### Foriegn Profile View
![Image: Deployed ForiegnProfile ](https://github.com/d-tidwell/DTour/blob/main/resources/images/FollowingProfileView.png)
### Event Details
![Image: Deployed Event Details ](https://github.com/d-tidwell/DTour/blob/main/resources/images/eventDetails.png)
