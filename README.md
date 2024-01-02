# DB Setup

 - Go to https://dev.mysql.com/downloads/installer/ 
 - Download the following mySQL installer, and start intallation 
 ![Alt text](image.png)
 - Select the full installation as shown below, click on next and then execute. 
 ![Alt text](db2.jpg)
 - After the installation, keep clicking next without changing anything, until you reach the following page: **Authentication Method**. Here, select the first option.Click next.  
 ![Alt text](db3.jpg)
 - In the **Accounts and Roles** page, set the MySQL Root Password to **Password@123** . This is a case-sensitive password, so make sure it is set correctly. Click on next. 
 ![Alt text](db4.jpg)
 - Leave the default options in the next page as shown below
 ![Alt text](db5.jpg)
 - keep clicking next without changing any default settings. Finally, click the Execute option in the **Apply Configuration** page. Click Finish after done. 
 - In the next phase of the setup, you'll be prompted to to configure the products that were installed. Don't change any default settings, keep clicking next until you reach this page. 
 ![Alt text](db6.jpg)
 - In the above page, in the password input, provide with the password: **Password@123** and the click on the check button. The status should be should reflect that the connection is successful. 
 - Click on next, and the Execute. Click next, and finally Finish. MySQL Workbench should open automatically and should look something like this. 
 ![Alt text](db7.jpg)
 - Double click on "Local instance MySQL80". This will prompt you to enter a password. Enter the following password: **Password@123**. Upon successful login, switch to the "Schemas" tab as shown below. 
 ![Alt text](db8.jpg)
 - Click on the new schema option and give the name as **grocery_application** as shown below. Make sure to give this properly as this is case sensitive. Click next, next and finish. 
 ![Alt text](db9.jpg)

<br />
<br />

 # Backend Setup
 - Make sure to have Java 17 installed in your computer. If not yet installed, you can do this from https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html using the Windows x64 MSI Installer. 
 - Create a folder named **photos** in your C: Drive. Make sure the folder name is correctly given as it is case sensitive. When you put "C:\photos" into the navigation bar is explorer, it should open up. 
 - Go to https://www.jetbrains.com/idea/download/?section=windows and download the IntelliJ IDEA Community Edition. Install IntelliJ with all the default settings. 
 - Go to https://github.com/srisri332/groceryCRUDApp and download the code as zip. Extract the zip file to find the frontend, backend and resources folder. 
 - From IntelliJ, click on open and navigate to the backend folder as shown in the picture below. Click on ok and open the project. 
  ![Alt text](be1.jpg)
 - Since this is first time of opening the project, IntelliJ might complaint and throw some erros. Don't pay attention to these. 
 - In the maven tab, you should see backend automatically added as shown below. If you don't, right click on the pom.xml file and select the option "Add as Maven Project". This option is only visible only if the backend folder is not added as maven project automatically. ![Alt text](be2.jpg)
 - Click on File -> Project Structure. It opens up this windows. Select the SDK version as 17 which should be installed in the system already. Click on ok. ![Alt text](be3-1.jpg)
 - Navigate to ./src/main/java/com.grocery.backend and open the **BackendApplication.java** file. next to the BackendApplication class, there should be a run button as shown in the picture below. Click on it and click the Run BackendApplication() option. 
 ![Alt text](be4.jpg)
 - If you setup DB and Backend correctly, the application should run successfully and this should show up.
 ![Alt text](be5.jpg)
 - If you open http://localhost:8080/test in the browser, this text will comeup. 
 ![Alt text](be6.jpg)
 
<br />
<br />

 # Frontend Setup

- In the downloaded project code, navigate to ./groceryCRUDApp-main/frontend and open the index.html file. Application should open like this. 
![Alt text](fe1.jpg)
- Make sure the folder **photos** is created in **C:** drive as mentioned in the backend setup step 2. This is where the uploaded photos are stored. 
- Watch the following video to see the demo of the product https://www.youtube.com/watch?v=j-2PoZFIpXY&list=PLW64Hn2lR3lkQHfD4V37tjH17rSch4nF3 
