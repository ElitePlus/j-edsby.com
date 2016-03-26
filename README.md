![logo](http://edsby.com/wp-content/uploads/2011/07/edsby_student1.png)

#Unofficial (Official) Java Edsby API!
Finally, there is a way to access Edsby data without having to use Edsby website or their application. Finally you can create your own applications that interface with Edsby. 

##Description
edsby.com is a website for educators to share grades and information to students. Currently my school district, Hillsborough county, uses this service. There is no official API or documentation on how to use their service to access edsby data and that is a problem in the 21st century.

##Quick Start Guide
Edsby requires that you have a `student_id` and a `student_pass` to access the data. When you go onto the website, your GET request returns some data containing cookies that you will need to save for your session. Then you have to make a request to gather `formkey`  and `sauthdata`. Your login request passes that data, username, and password, all together to get you into the website. 

The API currently have the ability to log a student in and access their grades along with teachers identifiers. Looking at the `JEdsbyTest.java` file can shed more light on the subject.

###Packet Generation
     Packet packet = new Packet("https://" + HOST_NAME + ".edsby.com", Packet.ERequestMethod.GET);
     packet.setScheme("https");
     packet.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
     packet.setUserAgent(JEdsby.USER_AGENT());
     
This how you craft a simple packet. in this case, it is from the initial request you would make to Edsby for the session cookies. The request has many fields that can be specified and some have to be filled to work (POST request, postDATA cannot be null, content-length).

When you want to start collecting data, simply <i>send</i> your packet 

	packet.sendPacket();
	
If you want to `stash cookies` meaning that you want to save the cookies into the built in `cookies` variable in the `Packet` class do

	packet.stashCookies();

When reading data from either BufferedReader or GZIPInputStream

	packet.getDataFromBufferedReader();
	packet.getDataFromGZIP();
	
####Using the API
~~Currently the test class shows you how to create all the requests by hand (problem!) and how to use that data a little.~~

~~However that isn't very nice is it, having to handle all the requests yourself. In a future update, the API will handle all the login functionality and requests away from the user.~~ 

<b>The future is now!</b> I have changed the packet and data handling system so it won't require intimate knowledge of the API. 

You can still use the default `Packet.java` class to craft all your requests if you want (such as if you wanted to extend the functionality of the API beyond its currently limitations). However, now all the functionality for logging in, storing cookies, and setting up the basic 

##Use Cases
Currently I have been using the API to access my grade data. Using the JavaMAIL API I was able to make a simple program that checks the time of day and if it was `06:00` to send myself an e-mail and a text-message on my cellular phone alerting me if my grades had changed at all.