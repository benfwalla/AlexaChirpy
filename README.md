# Alexa Chirpy

Alexa Chirpy is a Java-programmed Twitter client for the Amazon Alexa. Utilizing the powerful API Library of 
[Twitter4J](http://twitter4j.org/en/) and [Amazon's Java Alexa Skills Kit](https://github.com/amzn/alexa-skills-kit-java), 
Chirpy is a real-time, dynamic Alexa Skill that provides information to the client. Chirpy's features and popular calls
include:
* Finding out how many followers you have and how many people follow you
    * _"Alexa, ask Chirpy how many followers I have."_
    * _"Alexa, ask Chirpy how many people follow me."_
    * _"Alexa, ask Chirpy how many people I follow"_
    * _"Alexa, ask Chirpy the amount of users I am following"_
* Discovering the current trending topic on Twitter
    * _"Alexa, ask Chirpy what's trending"_
    * _"Alexa, ask Chirpy what's popular on Twitter"_
* Seeing if a celebrity has a Twitter account, if so, what his/her Twitter handle is
    * _"Alexa, ask Chirpy if Lebron James has a Twitter"_
    * _"Alexa, ask Chirpy for the Twitter handle of Taylor Swift"_
* Sending a Tweet from your active profile
    * _"Alexa, ask Chirpy to tweet go indiana hoosiers"_
    * _"Alexa, ask Chirpy to tweet i like them apples"_
    
This README is going to be a basic, step-by-step process of how I went about completing this project. Additionally,
I will provide helpful links to documentation and other resources that I used at the bottom of this page.

### 1. Creating a Maven Project and Installing Dependencies
To launch this project on the AWS servers, you will need to create it as a Maven project. As explained in Step 6,
you will need to save your code as a .jar file.

There are 2 dependencies needed for this project: Twitter4J and the Alexa Skills Kit. Twitter4J is used to authenticate
your Twitter account and make all of the Twitter calls. The Alexa Skills Kit is used to connect the Maven project to the
appropriate Alexa Skill and program all of the Speechlet Responses. The dependencies implemented in my pom.xml file are
shown below:
```xml
<dependencies>
    <dependency>
        <groupId>org.twitter4j</groupId>
        <artifactId>twitter4j-stream</artifactId>
        <version>4.0.5</version>
    </dependency>
    <dependency>
        <groupId>com.amazon.alexa</groupId>
        <artifactId>alexa-skills-kit</artifactId>
        <version>1.6.0</version>
    </dependency>
</dependencies>
```

### 2. Authenticating Twitter and writing Twitter methods
In order to access the Twitter APIs, you will need to register an app on [apps.twitter.com](https://apps.twitter.com/).
After doing that, you have access to your use your unique consumer key, consumer secret, access token, and access token
secret. It's important to note that all of Chirpy's Twitter commands will be executed with this user's account.
Whenever you ask for your follower/following count or post a tweet, it will be under the user that registered the app 
online. You can see how I authenticated my own app in [src/main/java/TwitterAuth.java](https://github.com/benfwalla/AlexaChirpy/blob/master/src/main/java/TwitterAuth.java).

All of my Twitter calls can be found in [src/main/java/TwitterCalls.java](https://github.com/benfwalla/AlexaChirpy/blob/master/src/main/java/TwitterCalls.java). 
As you can see, most of my return objects are either Strings or integers. This will allow me to easily convert my calls
into a Speechlet Response. 

### 3. Creating a Skill with the Alexa Skills Kit
In order to put this project on an Amazon Echo, you will need to create an Alexa Skill. You can do this by creating a
skill on Amazon's [Alexa Skills Kit](https://developer.amazon.com/alexa-skills-kit) website. In the Skill Information tab, 
you create a name for your Alexa Skill an the invocation name. You will also be given a unique Application ID that you 
will need to put in your RequestStreamHandler file (Step 5). In the Configuration tab, make sure to set "AWS Lambda ARN"
as your Endpoint Type. Leave the "Default" field empty for the moment. Now, you are ready to set up the interaction 
model.

### 4. Designing the Interaction Model
The interaction model is how you initially create your Alexa Skill's intents. For example, I created an intent called
"Trending Intent". The purpose of it is to return the top 5 trending topics on Twitter at that time. Each intent needs
sample utterances, or phrases that a user may say to invoke this specific intent. So, when a user says, _"Alexa, ask
Chirpy what's trending"_, the phrase "what's trending" is a sample utterance. Ultimately, the goal is to write out all
of the sample utterances which a human may reasonably say to invoke the intent.

All of the contents in the interaction model are organized in a .json file. You can view mine in [interaction-model/schema.json](https://github.com/benfwalla/AlexaChirpy/blob/master/interaction-model/schema.json).

### 5. Writing Speechlet Responses
To complete the backend component of your Alexa skill, you need 2 classes that implement `Speechlet` and 
`SpeechletRequestStreamHandler`, respectively. My Speechlet class can be found in [src/main/java/ChirpySpeechlet.java](https://github.com/benfwalla/AlexaChirpy/blob/master/src/main/java/ChirpySpeechlet.java).
This is where I reference all of my methods from `TwitterCalls` into the relevant intent. Each request requires you return
a SpeechletResponse object. Using the common methods from the Alexa Skills Kit, it is easy to turn a String type into a
SpeechletResponse.

My `SpeechRequestStreamHandler` class can be found in [src/main/java/ChirpySpeechletRequestStreamhandler.java](https://github.com/benfwalla/AlexaChirpy/blob/master/src/main/java/ChirpySpeechletRequestStreamHandler.java).
The purpose of this class is to reference the Alexa Skill that I setup online to this project. If you go to the Skill 
Information tab in the Alexa Skill environment, you can find the App ID of your skill. Copy and paste that into your
`SpeechletRequestStreamHandler` class. 

### 6. Launching the AWS Lambda Function
In order for your Alexa Skill to work, your Maven project must be housed in an [AWS Lambda](https://aws.amazon.com/lambda/) 
environment. When you reach the Lambda website, create a new function. After giving your function a name, set the runtime
to Java 8 and give it a custom role. You will not need to change any advanced settings. When you reach your function's 
homepage, add "Alexa Skills Kit" as a trigger. 

You will notice a unique ARN code in the top right corner. Copy that code and return to your Alexa Skill page. Paste the 
code in the "Default" field on the Skill Information tab as mentioned in Step 3.

Now, you are ready to launch your skill. Locate your project in the command prompt and build your project with the command
`mvn package`. This creates a .jar file for you in your target folder. On your Lambda function's homepage, upload the
.jar file. Ensure that the runtime is Java 8 and set "ChirpySpeechletRequestStreamHandler" as the Handler.

### 7. Testing and Logging
To test your Alexa Skill, visit [Echosim.io](https://echosim.io/welcome)- Amazon's official testing environment for 
Alexa Developers. You can ask it whatever intents from your Alexa Skill that you like. Your first invocation will take
the longest amount of time, as the program needs to create a Twitter instance.

In terms of logging and error review, Amazon makes it really easy with [AWS CloudWatch](https://aws.amazon.com/cloudwatch/).
Whenever a new instance of your skill is created, CloudWatch will automatically log all of its activity. You can even 
setup custom dashboards to view the popularity of each intent, total errors made in the last x days, or anything else
that you deem relevant.

### 8. Helpful Links
* [Twitter4J 4.0.4 Library](http://twitter4j.org/javadoc/index.html) - javadoc for Twitter4J
* [Alexa Skills Kit](https://developer.amazon.com/alexa-skills-kit) - create an Alexa Skill
* [Alexa Skills Kit Java Library Reference](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/javadocs/alexa-skills-kit-java-library-reference) - javadoc for Alexa
* [AWS Lambda](https://aws.amazon.com/lambda/) - store the Maven project
* [Echosim.io](https://echosim.io/welcome) - Alexa testing environment
* [AWS CloudWatch](https://aws.amazon.com/cloudwatch/) - view logs of your Skill

        