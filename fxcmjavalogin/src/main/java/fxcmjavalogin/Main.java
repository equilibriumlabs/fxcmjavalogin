/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxcmjavalogin;

import com.fxcm.external.api.transport.FXCMLoginProperties;
import com.fxcm.external.api.transport.GatewayFactory;
import com.fxcm.external.api.transport.IGateway;
import com.fxcm.external.api.transport.listeners.IGenericMessageListener;
import com.fxcm.external.api.transport.listeners.IStatusMessageListener;
import com.fxcm.messaging.ISessionStatus;
import com.fxcm.messaging.ITransportable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Luis Pinto at EquilibriumLabs
 * 
 * This class is an example how to quick log in into FXCM servers from java, using FXCM JAVA API.
 */

public class Main implements IGenericMessageListener, IStatusMessageListener{
    
    private IGateway gateway = GatewayFactory.createGateway();
    private FXCMLoginProperties login = null;
    private static final String server = "http://www.fxcorporate.com/Hosts.jsp";
    
    private static final Log log = LogFactory.getLog( Main.class.getName()  );
    private static final boolean isDebugging = log.isDebugEnabled();
    
    /**
     * Method to perform log In into FXCM servers and register Listeners.
     * 
     * @return True if successfully logged IN. False failed.
     */
    public boolean login() {

		try {
			gateway.registerGenericMessageListener(this);
			gateway.registerStatusMessageListener(this);

			if(!gateway.isConnected())
				gateway.login(this.login);

                        log.info("Connection to FXCM SERVER status: " + gateway.isConnected());

			//return that this process was successful
			return true;
		}catch(Exception e) {
			log.error("Error: Log In into FXCM Server. " + e.getMessage());
		}

		//if any error occurred, return that this process failed
		return false;
	}
    
    /**
     * This method logouts from server and the remove any listener previously registered.
     * 
     */
    public void logout() {		
		//remove the generic message listener, stop listening to updates
		gateway.removeGenericMessageListener(this);
		//remove the status message listener, stop listening to status changes
		gateway.removeStatusMessageListener(this);
                //attempt to logout of the api
                log.info("Logging out.");
		gateway.logout();
                System.exit(0);
	} 
    
    
    /**
     * This method read login credentials from login.info properties file and loads up the content into login variable.
     */
    public void read_login_credentials_from_file() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("login.info"));
            this.login = new FXCMLoginProperties(p.getProperty("username"), p.getProperty("password"), p.getProperty("server"), p.getProperty("host"));
        } catch (FileNotFoundException ex) {
            log.error("Error: File login.info not found. " + ex.getMessage());
        } catch (IOException ex) {
            log.error("Error: Reading login.info file. " + ex.getMessage());
        }
    }
    

/**
 *  Method implementation from IGenericMessage Interface
 * @param it incoming messages receiver.
 */
    @Override
    public void messageArrived(ITransportable it) {
        log.info(it);
    }
    
    /**
     * Method implementation from IStatusMessageListener
     * @param iss session status receiver.
     */
    
    @Override
    public void messageArrived(ISessionStatus iss) {
        log.info(iss);
    }
    
        
    public static void main(String[] args) {
        Main m = new Main();
        m.read_login_credentials_from_file();
        m.login();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            log.error("Error: Thread.sleep Exception " + ex.getMessage());
        }
        m.logout();
    }
    
}
