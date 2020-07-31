# server
https://documenter.getpostman.com/view/3639058/T1DtdFNp?version=latest

# Running the Server

### On a local system

1. Clone the repository
2. Download spring tool suite on your system using the link https://spring.io/tools
3. Download Mongo DB on your system using link https://www.mongodb.com/try/download/community
4. Once the Mongo DB server is up and IDE is downloaded.
5. Open the project on the IDE.
6. Right-click on the project and run as spring boot app
7. Now you must see the project running on the server.
8. Get the IP address of your system.
9. Access the URL \&lt;ip\&gt;:8080/user/sample. If it prints &quot;Hello World&quot; Then the server is up and running.
10. Update the file src\&gt;main\&gt;resources\&gt;application.properties file
11. Update the **spring.data.mongodb.uri = mongodb://localhost:27017/${spring.data.mongodb.name}**
12. Configure the IP address in the Mobile applications and web portal at the location described in the instructions at the main/respective repository.

### On IBM Cloud

1. Steps to run the bootstrap application on the IBM Cloud
  1. Get a RedHat openshift device on IBM cloud with the configuration of your choice.

![](RackMultipart20200731-4-1p1bw0t_html_dbf4a1870a6845c2.png)

![](RackMultipart20200731-4-1p1bw0t_html_1c309bffeda063cd.png)

  1. Open the redshift cloud console: Dashboard \&gt; Clusters \&gt; open cluster \&gt; openshift web console

![](RackMultipart20200731-4-1p1bw0t_html_ff69cd97a6fb4dde.png)

  1.

  1. After clicking on the project, a screen similar to the image below should appear. Just click on the Administrator in the top left corner and select Developer from the drop-down menu. ![](RackMultipart20200731-4-1p1bw0t_html_84ebe914834c887b.png)
  2. On the screen that loads, click on From Git.

![](RackMultipart20200731-4-1p1bw0t_html_2d00f721eaf2d28e.png)

  1. In the Git Repo URL section, paste the link &quot; **https://github.com/PocketCareS/server**&quot; from the Github repository.

Note: Click on More Advanced Options to specify the branch name(if-any). By default, the branch is master and we do not need to specify it.

1. Scroll down to select Builder Image. It is supposed to be Java in our case. After clicking on Java, make sure to change the Java version to 8 from the drop-down that appears. ![](RackMultipart20200731-4-1p1bw0t_html_7d48a7a733b4dd62.png)
2. Let other options be as they are, no fiddling with them. Just make sure that the checkbox in the Advanced Options section is checked as shown in the image below and then click on Create.
3. Click on the penguin logo on the screen. We can see that a build is already in progress. Keep a little patience and wait for the build to finish.
4. As soon as the build is finished, a pod will automatically be deployed. Congratulations, you have successfully deployed your spring-boot application on Redhat Openshift nonchalantly.

![](RackMultipart20200731-4-1p1bw0t_html_84ebe914834c887b.png)

1. To get the URL go under the Routes section.

The link in this section is your newly deployed spring-boot application.

![](RackMultipart20200731-4-1p1bw0t_html_458b2837319d95fb.png)

1. Get the IP address of your system.
2. Access the URL \&lt;ip\&gt;/user/sample. If it prints &quot;Hello World&quot; Then the server is up and running.
3. Configure the IP address in the Mobile applications and webportal at the location described in the instructions at the main/respective repository.
4. If on the deployment logs the SERVER\_PORT issue appears. Navigate to the

Environment variables of the POD and enter the variable name as SERVER\_PORT and value as 8080

![](RackMultipart20200731-4-1p1bw0t_html_eefe4fbabbab52ef.png)

#### Steps to configure MongoDB database on IBM Openshift

1. Open the redshift console
2. After clicking on the project, a screen similar to the image below should appear. Just click on the Administrator in the top left corner and select Developer from the drop-down menu.

![](RackMultipart20200731-4-1p1bw0t_html_db3fb2d45b671fcf.png)

1. On screen select Database.
2. From the options displayed on the screen. Select the database as MongoDB service with persistent storage.

![](RackMultipart20200731-4-1p1bw0t_html_bd934cf42260985e.png)

1. Select the DB name as &quot;pocketCare&quot;
2. provide the credentials as required. Remember the credentials as these will be required in configuring the server.
3. Let all other options be as is.
4. Drag to the bottom and select create.

![](RackMultipart20200731-4-1p1bw0t_html_21902662e644d7e1.png)

1. Navigate to the Developer\&gt;Topology on the left pane.
2. Select the mongodb icon on the screen.
3. On the window pop on right select services.
4. From the service menu, select the Cluster IP that appears at the position marked in the image below.

![](RackMultipart20200731-4-1p1bw0t_html_e62720c129b0c859.png)

1. Open the server application on your system.
2. Open the src\&gt;main\&gt;resources\&gt;application.properties file
3. #mongodb://\&lt;db-username\&gt;:\&lt;db-password\&gt;@\&lt;db-ip\&gt;:\&lt;db-port\&gt;/${spring.data.mongodb.name}
4. Mention the username, password.
5. Mention the cluster IP in place of \&lt;db-ip\&gt;
6. By default dbport is 27017. IF not, mention the customised port in place of db-port

#### Deploy the server changes on the server.

1. For the IBM openshift.
2. Open the openshift web console.
3. Navigate to Developer\&gt;Topology
4. Click on Server.
5. On the right window, select start build.

![](RackMultipart20200731-4-1p1bw0t_html_3d46aac11680b657.png)

#### IBM Push Notification

1. Service instance for push notification: [https://cloud.ibm.com/docs/mobilepush?topic=mobilepush-push\_step\_1a](https://cloud.ibm.com/docs/mobilepush?topic=mobilepush-push_step_1a)

1. Obtain notification service provider credentials for Android based on the steps mentioned in the link [https://cloud.ibm.com/docs/mobilepush?topic=mobilepush-push\_step\_1](https://cloud.ibm.com/docs/mobilepush?topic=mobilepush-push_step_1)

1. Configure the service instance following the link as : [https://cloud.ibm.com/docs/mobilepush?topic=mobilepush-push\_step\_2](https://cloud.ibm.com/docs/mobilepush?topic=mobilepush-push_step_2)

1. Setup the client SDK : [https://cloud.ibm.com/docs/mobilepush?topic=mobilepush-push\_step\_3](https://cloud.ibm.com/docs/mobilepush?topic=mobilepush-push_step_3)

##### Things to change on Android

1. Setup the JAVA SDK service.
  1. Got to your IBM push notification service. [https://cloud.ibm.com/services/imfpush/crn%3Av1%3Abluemix%3Apublic%3Aimfpush%3Aus-south%3Aa%2F446673b322a041c3852f5abaf675bae9%3A2abe5c40-d5aa-4ff0-9b2e-d76327e76ee6%3A%3A?paneId=credentials](https://cloud.ibm.com/services/imfpush/crn%3Av1%3Abluemix%3Apublic%3Aimfpush%3Aus-south%3Aa%2F446673b322a041c3852f5abaf675bae9%3A2abe5c40-d5aa-4ff0-9b2e-d76327e76ee6%3A%3A?paneId=credentials)
  2. On the image given below. Select &#39;Service Credentials&#39; from left pane.
  3. Create new credentials if not already.

![](RackMultipart20200731-4-1p1bw0t_html_45f503c88ac01449.png)

  1. Open the JAVA server application code on the IDE (sts or intelli J)
  2. Navigate to the Class src/main/java/com/PocketCare/pocketCare/Service/IBMNotificationService.java.
  3. Copy the appGuid from IBM console and paste at private static final String **APIID= &quot;YOUR IBM push notification APP ID&quot;;**
  4. Copy the apikey from IBM console and paste at private static final String **APIKEY = &quot;Your IBM push notifcation API KEY&quot;;**
  5. Build the server application on local or cloud console.

### Installing the fronend application

1. Install Visual Studio Code
2. Install the Nodejs package on your system. https://nodejs.org/en/download/
3. Clone the repository from the link : [https://github.com/PocketCareS/webportal](https://github.com/PocketCareS/webportal)
4. run the following commands.
  1. npm install
  2. To run on local system run command \&gt; npm start
  3. To deploy the application on openshift cloud
  4. Install the OC cli on your system using the following [https://mirror.openshift.com/pub/openshift-v4/clients/oc/](https://mirror.openshift.com/pub/openshift-v4/clients/oc/)
  5. Once installed.
  6. Open the openshift console
  7. Get the login command
  8. ![](RackMultipart20200731-4-1p1bw0t_html_4695d32a90c2a8da.png)
  9. Copy and paste the command in terminal at the folder where the react application source code resides.
  10. Run\&gt; npm install
  11. Run\&gt; npx nodeshift --strictSSL=false --dockerImage=nodeshift/ubi8-s2i-web-app --imageTag=10.x --build.env YARN\_ENABLED=true --expose
  12. This will install the react application on the openshift and give the public url in the console.


