# fxcmjavalogin
It is a very basic example about how to log in from pure java into FXCM servers, using the java SDK provided by FXCM.

## Installation
Download or clone the repository, The example  has been built on a gradle project. Please, be sure you have a recent version of Java installed. The only dependencies are: fxcm-api.jar, fxmsg.jar (both already included into lib folder) and apache commons loggings. Open the login_demo.info, fill the username/password properties and rename to login.info. That's should be enough to go.

## Usage
The example is ready to play with. The flow mainly is:

1. Create IGateway instance:

IGateway gateway = GatewayFactory.createGateway();

2. Fill FXCMLoginProperties to be able to log in.

//server can be Demo or Real and host: "http://www.fxcorporate.com/Hosts.jsp";
```
  FXCMLoginProperties login = new FXCMLoginProperties("username","password", "server", "host");
  gateway.login(this.login);
```

3. Implement and register Listeners:
  a. Interface IGenericMessageListener: It handles incoming data from fxcm servers.
  b. Interface IStatusMessageListener: It handles messages status from fxcm servers.
  ```
    gateway.registerGenericMessageListener(this);
    gateway.registerStatusMessageListener(this);
  ```

4. Run. If all steps are correct you will start receiving messages from FXCM servers. In this example we log in, wait for ten seconds, and then logout.

For more reference: 
https://github.com/fxcm/JavaAPI

## Disclaimer

It is only an example of how to use the FXCM Java SDK API. The usage of this is at your own risk and under the terms of FXCM, for more info: https://github.com/fxcm/JavaAPI.





