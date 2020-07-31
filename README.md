# PocketCare S - Server

<img src="https://github.com/PocketCareS/PocketCareS-Android/blob/development/assets/logo.png" width="300">

**Call for code submission for COVID-19 track.**

PocketCare S is a comprehensive smartphone-based solution for monitoring close encounters. It is a bluetooth low energy (BLE) based solution which enables smartphones to send and receive anonymous beacon signals. It checks the distance between a smartphone and another beacon (or smartphone running PocketCare S) to see if they are close to each other (less than 2m). If so, the smartphone records the duration of such a close encounter with another beacon. 

PocketCare S is designed to report social distance information without collecting or revealing any personally identifiable information about any specific individual.


## Contents 
1. [Demo Video](#demo-video) 
2. [The Architecture](#the-architecture)
3. [Getting Started](#getting-started)
4. [How does PocketCare S Work?](#how-does-pocketcare-s-work)
5. [Built With](#built-with)
6. [Project RoadMap](#project-roadmap)
7. [Further Readings](#further-readings)
8. [License](#license)
9. [Acknowledgments](#acknowledgements)

## Demo Video 

[![Demo](http://img.youtube.com/vi/JnOWwagUgxQ/0.jpg)](http://www.youtube.com/watch?v=JnOWwagUgxQ "PocketCare S Demo")

## The Architecture

![Architecture](https://github.com/PocketCareS/PocketCareS-Android/blob/development/assets/PocketCareS_Design_Simplified.png)

## Getting Started 

### High Level Architecture
![High Level Diagram](assets/22.jpg)

### API Documentation
[Postman Documentation Link](https://documenter.getpostman.com/view/3639058/T1DtdFNp?version=latest)

### Prerequisites for installation on local machine

Before you begin, make sure you satisfy the following requirements in order to run the server on your local system:

1. Spring Tool Suite latest version.
2. MongoDB Server setup on your local system. [Link](https://www.mongodb.com/try/download/community)
3. JAVA 8 or above installed on the system.
4. NPM (node package manager) which can be installed from [here](https://nodejs.org/en/download/)

### Steps to install server 

[Installation Guide](https://github.com/PocketCareS/server/blob/master/Installation.md)

Once the application starts, follow the on-boarding process and read how P works below. 

## How does PocketCare S Work?

### Key Highlights (Mobile Application)

1. Close encounter data will be displayed in the mobile application after a close encounter session starts. A close encounter session starts when two people are within **2 meters** for at least **5 minutes**. 
2. The **virtual bluetooth name** changes every hour to ensure **user privacy**. 
3. Data upload to the server takes place every hour.
4. Data is stored in user's phone for a maximum of 14 days. 

### Detailed Architecture 

![Working](https://github.com/PocketCareS/PocketCareS-iOS/blob/master/assets/PocketCareS_Design_Technical.png)

### Technological Advances

![Tech](https://github.com/PocketCareS/PocketCareS-Android/blob/development/assets/PocketCare_S_Road_Map.png)

### Security and Privacy 

![Security](https://github.com/PocketCareS/PocketCareS-Android/blob/development/assets/PocketCareS-Privacy.png)

https://github.com/PocketCareS/PocketCareS-Android/blob/development
