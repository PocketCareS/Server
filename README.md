# PocketCare S - Server

<p align="center">
<img src="https://github.com/PocketCareS/PocketCareS-Android/blob/development/assets/logo.png" width="300">
</p>

**Call for code submission for COVID-19 track.**

PocketCare S is a comprehensive smartphone-based solution for monitoring close encounters. It is a bluetooth low energy (BLE) based solution which enables smartphones to send and receive anonymous beacon signals. It checks the distance between a smartphone and another beacon (or smartphone running PocketCare S) to see if they are close to each other (less than 2m). If so, the smartphone records the duration of such a close encounter with another beacon. 

PocketCare S is designed to report social distance information without collecting or revealing any personally identifiable information about any specific individual.


## Contents 
1. [Demo Video](#demo-video) 
2. [The Architecture](#the-architecture)
3. [Getting Started](#getting-started)
4. [How does PocketCare S Work?](#how-does-pocketcare-s-work)
5. [Built With](#built-with)
6. [Project Road Map](#project-roadmap)
7. [Further Readings](#further-readings)
8. [License](#license)
9. [Acknowledgments](#acknowledgements)

## Demo Video

[![Demo](https://github.com/PocketCareS/PocketCareS-iOS/blob/master/assets/Video%20Thumbnail.png)](https://youtu.be/JUTQIcdgXwc "PocketCare S Demo")

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

1. PocketCare S uses **Bluetooth Low Energy (BLE)** to discover and compute the duration **close encounters**. 
2. A **close encounter** session starts when two people are within **2 meters** for at least **5 minutes**. 
3. **Close encounter** data will be displayed in the mobile application after a close encounter session starts. 
4. Users are **notified immediately** if a close encounter session exceeds **10 minutes**.
5. The **virtual bluetooth name** changes **every hour** to ensure **user privacy**. 
6. Data stored in the mobile application is **anonymized** (contains no Personally Identifiable Information) and consists of **daily health report** and **close encounters** for a **maximum period of 14 days.**
7. Data upload to the server takes place **every hour**.

### Detailed Architecture 

![Working](https://github.com/PocketCareS/PocketCareS-iOS/blob/master/assets/PocketCareS_Design_Technical.png)

### Security and Privacy 

PocketCare S cares values the security and privacy of its users. The app does not collect any private information about an individual person.  All the data collected is anonymous and will not reveal any personally identifiable information. An Infographic with this information can be found [here](https://engineering.buffalo.edu/content/dam/engineering/computer-science-engineering/images/pocketcare/PocketCareS.pdf).

**For a more detailed description, refer to the [additional information](#additional-information) section.**


## Built With

### iOS
- [BeaconMonitor](https://github.com/sebk/BeaconMonitor) - Used for close contact detection
- [Charts](https://github.com/danielgindi/Charts) - Used to visualize data
- [CryptoSwift](https://github.com/krzyzanowskim/CryptoSwift) - Used for encryption

### Android 
- [Android Beacon Library](https://altbeacon.github.io/android-beacon-library/) - Used for close contact detection
- [High Charts](https://www.highcharts.com/) - Used to visualize data
- [IBM Push Notifications](https://www.ibm.com/cloud/push-notifications) - Push Notification for Exposure 

### Server 
- [Red Hat OpenShift on IBM Cloud](https://www.ibm.com/cloud/openshift)
  - Server using [OpenJDK 8](https://www.ibm.com/cloud/support-for-runtimes)
  - Database using [MongoDB](https://www.ibm.com/cloud/databases-for-mongodb)
  - Web Portal hosted using [Node JS Server](https://developer.ibm.com/node/cloud/)
- [React](https://reactjs.org/) - Used to build the web portal 
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework for the Server

## Project Road Map 

![Road Map](https://github.com/PocketCareS/PocketCareS-Android/blob/development/assets/PocketCare_S_Road_Map.png)

## Additional Information 

You can read more about PocketCare S on our [website](https://engineering.buffalo.edu/computer-science-engineering/pocketcares.html). We also have a [White Paper](https://docs.google.com/document/d/e/2PACX-1vT6UqA3HByzG5Di576gmz-JWzgKOFx5KLYGgJMpxcmWkOXYJ_vUFz2h1w2LnDNWI4y-xnyKhPi_s70p/pub) which can be accessed here.  

PocketCare S is also available on [Google Play](https://play.google.com/store/apps/details?id=com.ub.pocketcares) and to the University at Buffalo (UB) community using the [Apple Developer Enterprise Program](https://engineering.buffalo.edu/computer-science-engineering/pocketcares/pocketcares-ios.html).

## License 

This project is licensed under the Apache 2 License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

Special thanks to all who helped bring the project to fruition:

Sourav Samanta, Rishabh Joshi, Jeetendra Gan, Shanelle Ileto, Aritra Paul, Dr. Peter Winkelstein, Dr. Matthew R. Bonner, Kevin Wang, Chen Yuan, Dheeraj Bhatia, Latheeshwarraj Mohanraj, Dr. Wen Dong, Dr. Tong Guan, Dr. Marina Blanton, Sasha Shapiro, Stephen Fung

And our deepest gratitude for the support of **University at Buffalo**.
