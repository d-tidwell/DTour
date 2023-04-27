# NSS Midstone Group Project

This project was a culmination of a two week intensive group back & front end build with no prior instruction in Webpack, JavaScript, HTML or CSS. Our team was given three days of concept and endpoint design time followed by 10 days of build time, and one day of presentation preparation to accomplish the deliverables outlined below and have a functional cloud API deployed to a front end web application.

A powerpoint outlining the project mission can be found here [@DTour PowerPoint](https://docs.google.com/presentation/d/1UVmT7e1QBgVxmuxp4RD5vVlZVpYPvCqlumgTPxhtTDg/edit?usp=sharing)

### Team Deliverables

* **Design Document: Problem Statement** Your team’s technical design specification. Complete [this template](resources/design-document.md). This should explain the product you'll be creating and what problem it solves.
* **Design Document: Peer Team Review** Another team will review your technical design to help improve it. Each team will review at least one other teams design.
* **Design Document: Instructor Review** One of the instructors will review your technical design after you've create it and reviewed it with another team.
* **Team Charter:** Statements of how your team will work to ensure constructive collaboration, fairness, that everyone is learning and contributing. Complete [this template](resources/team-charter.md).
* **Working Product:** Your project, as a working website deployed to AWS.
* **Team Reflection:** A final retrospective with a few questions to answer as a team to reflect on what you have accomplished and learned throughout the project. Complete [this template](resources/team-reflection.md).


## Technical Learning Objectives

This project is very open-ended and you/your team will be doing a lot of creative thinking to decide what you want to build. That said, there are several technical learning objectives that each team must meet. You should review the [Technical Learning Objectives](./resources/technical-objectives.md) as you familiarize yourself with the project. As part of the team reflection/retrospective you'll fill out this document with your team to document how you met the different objectives.

## Project Starter Infrastructure

Your team has been provided with a website, hosted by [CloudFront](https://aws.amazon.com/cloudfront/), that talks to an Amazon API Gateway endpoint. (A web front-end is likely new for several of you, as your unit projects so far have been all service development.) The Amazon API Gateway connects to a Lambda service, which stores its data in DynamoDB, an architecture that we’ve used several times on unit projects so far. The starter code provided contains a website that uses the playlist service you created in Unit 3. Below, Image 1 describes the architecture of the starter project.

![Image 1: The architecture of your starter project](resources/images/architecture_diagram.png)

*Image 1: Architecture diagram for the starter project*

### Introduction to the Starter Code

Unlike our unit projects in the past, this project will have two code bases.

[One code base](./MusicPlaylistServiceLambda) will be for your Lambda service code, similar to what we’ve seen previously. This will contain code that designs and runs your service APIs, as well as packages to interact with and test your service.

The [second code base](./web) will contain code for your website:

* HTML: The content of your web pages
* CSS: The styling and formatting of your web pages
* JavaScript: The code that runs when a user interacts with the web pages, some of which triggers the browser to send requests to the service

Often times these are separated into different repositories, but for simplicity’s sake we've kept them in the same repository for this project.

_Both of these should be considered starting points/example code. You will end up removing/replacing all of it as you build your own project._

### Deploying

We'll be using GitHub Actions to deploy the code to AWS. One of the first tasks that you'll do as a team is to configure the repository for this. Once that's completed your code should build and deploy as described in the [next README](./README-SETUP.md).

### AWS Resources

Each team has one AWS account they will share to deploy their website and service. This is where all of your changes will be merged together and visible. Your team's account will be named something like `SE_Unit_5_Group_TEAMNAME` and will be available for you to configure like you've done in previous units.

### Your website!

To access your website, you'll need to build it first. The [next README](./README-SETUP.md) has instructions on the different ways we'll be doing this.

## How Your Website Works

Creating each page of your website requires a surprising amount of interactions between different JavaScript files and between our frontend and our backend. Our view playlist page makes 3 separate calls to our MusicPlaylistService.

Let’s look at one piece - adding the header to our page. This requires the full end to end flow of HTML to JS to backend service all the way back to updating our HTML.

![Image 2: The end to end flow of HTML to JS to the backend service, and the return to HTML.](resources/images/sequence_diagram.png)

*Image 2: The end to end flow of HTML to JS to the backend service, and the return to HTML*

Two important things to call out:

1. To call our backend we are always going through our `MusicPlaylistClient`.
2. The `DataStore` uses a different pattern than we’ve seen previously. It has two member variables - the state, which is a JavaScript object that should hold all of the data being used across a webpage, and a list of listeners. The listeners are methods in other objects. Anytime `set` or `setState` is called it loops through all of those methods and executes each one. So in each of those listeners the first thing we do is check to see if the data that they rely on is null. Then, if it’s not null, we update the html to reflect the new data. You can take a look at the `addPlaylistToPage()` method in `viewPlaylist.js` for an example of this.

## Project Setup

This document has provided an overview of the project as a whole. For more specifics on how to configure the project and your GitHub repo/AWS account, continue by reading [README-SETUP.md](./README-SETUP.md).
