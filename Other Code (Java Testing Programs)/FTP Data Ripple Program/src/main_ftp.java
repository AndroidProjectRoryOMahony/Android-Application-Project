//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//Apache Commons (N.D) Available from http://commons.apache.org/proper/commons-net/examples/ftp/FTPClientExample.java [Accessed 14/02/2015] Networking Protocol FTP

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

public class main_ftp {
	
	String synthetic_values[];
	String filename = "Data.txt";
	FTPClient ftp = new FTPClient();

	public static void main(String[] args) {
		
		int valueNumber, betweenMin, betweenMax, decimalPlaces;
		String answer;
		
		main_ftp program = new main_ftp();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Do you want to create you own values? Y = yes, N = no and will use default values");
		answer = scan.next();
		
		System.out.println(answer);
		
		if(answer.equals("N")){
			valueNumber = 20;
			betweenMin = 50;
			betweenMax = 100;
			decimalPlaces = 3;
		}else{
			System.out.println("Enter the number of data values to create:");
			valueNumber = scan.nextInt();
			System.out.println("Enter the minimum value of the data:");
			betweenMin = scan.nextInt();
			System.out.println("Enter the maximum value of the data:");
			betweenMax = scan.nextInt();
			System.out.println("Enter the number of decimal places for the data:");
			decimalPlaces = scan.nextInt();
		}
		
		System.out.println("Type \"quit\" to exit");
		
		program.createSyntheticValues(valueNumber, betweenMin, betweenMax, decimalPlaces); //number of values to create, range of values, decimal places
		//while(true){
			program.connectToFTPServer();
		
			System.out.println("Printing synthetic values");
			program.printSyntheticValues();
			System.out.println("Rippling values");
			program.rippleValues();
			//System.out.println("Sleeping for 1 second");
			program.logout();
			try{
				Thread.sleep(1000);
			}catch(InterruptedException ie){
				
			}
			//System.out.println("Awake");
			program.createSyntheticValues(valueNumber, betweenMin, betweenMax, decimalPlaces); //number of values to create, range of values, decimal places
		//}
		//program.logout();
		
		System.out.println("\nProgram Finished");
		
	}
	
	private void logout() {
			try {
				ftp.logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	        try {
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}    	
	}

	private void rippleValues() {
            String dataReceived = "";
            
            //create a file called Test
            OutputStream dataStream = null;
			try {
				dataStream = ftp.storeFileStream("Test.txt");
				System.out.println("Data stream created");
				PrintStream printDataStream = new PrintStream(dataStream);
				for(int i = 0; i < synthetic_values.length; i++){	//write all of the synthetic values to it
            		printDataStream.print(synthetic_values[i] + "\n");
            	}
				printDataStream.flush(); //close the streams
	            printDataStream.close();
	            dataStream.close();
				
			} catch (Exception e1) {
				System.out.println("Error with output data stream");
				e1.printStackTrace();
			}

            try {
				if(!ftp.completePendingCommand()){ //must be called after using the output stream
					//File transfer failed
					System.out.println("File append failed");
					System.exit(1);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            
            
            //delete the previous data file
            try{
	            System.out.println(ftp.deleteFile(filename));
	            System.out.println("File deleted");
            }catch(Exception e){
            	System.out.println("Error");
            }

            
            //rename the recently create file with the old name
            try{
            	System.out.println(ftp.rename("Test.txt", filename));
	            System.out.println("File renamed");
            }catch(Exception e){
            	System.out.println("Error renaming file");
            }
            
            
            //append to a file
            /*OutputStream streamToWriteTo = ftp.appendFileStream(filename);
            PrintStream printStream = new PrintStream(streamToWriteTo);
            try{
            	//printStream.print("Test");
            	//ftp.appendFile(filename, inStream);
            }
            catch (Exception e) {
	            e.printStackTrace();
	         } 
            
            printStream.flush();
            printStream.close();
            streamToWriteTo.close();
            
            if(!ftp.completePendingCommand()){ //must be called after using the output stream
            	//File transfer failed
            	System.out.println("File append failed");
            	System.exit(1);
            }*/
            
            
            try{
            	String files[] = ftp.listNames();
            	for(int i = 0; i < files.length; i++){
            		System.out.println(files[i]);
            	}
            }catch(Exception e){
            	System.out.println("Error");
            }
            
            //read from the file
            
            try{
            	InputStream inStream2 = ftp.retrieveFileStream(filename);
	            InputStreamReader isr = new InputStreamReader(inStream2, "UTF8");
            int j;
            char c;
	            while((j=isr.read())!=-1){ //Loop through the file printing out the contents while storing in a string
	               c=(char)j; // cast int to character
	               //System.out.print(c); // print char
	               dataReceived += c;
	            }
	            isr.close(); //Close the input stream
	            inStream2.close();
	         } catch (Exception e) {
	            e.printStackTrace();
	         } 
            
            System.out.println(dataReceived);
		
	}

	private void connectToFTPServer() {
		
		boolean localActive = false, binaryTransfer = false, useEpsvWithIPv4 = false;
			
		int portNumber = 21;									
		//FTP details
		String server = "ftp.uomsmartgrid.t15.org";
		String user = "u703673749";
		String password = "UoM3rdyearproject";
		
				
			    try
		        {
			    	System.out.println("Attempting to connect...");
			    	//ftp = new FTPClient();
		            int reply;
		            ftp.connect(server);
		            System.out.println("Connected to " + server + " on " + (portNumber>0 ? portNumber : ftp.getDefaultPort()));
		            reply = ftp.getReplyCode(); //get reply code to verify success
		            System.out.println(reply);
		            if (!FTPReply.isPositiveCompletion(reply))
		            {
		                ftp.disconnect();
		                System.err.println("FTP server refused connection.");
		            }
		        }
		        catch (IOException e)
		        {
		            if (ftp.isConnected())
		            {
		                try
		                {
		                    ftp.disconnect();
		                }
		                catch (IOException f)
		                {
		                    // do nothing
		                }
		            }
		            System.err.println("Could not connect to server.");
		            e.printStackTrace();
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println(ftp.getReplyCode());
		            System.out.println("Error");
		        }
			    
			    try
		        {
			    	boolean success = ftp.login(user, password); //Attempt to login
		            if (!success){ //If the login failed
		                ftp.logout();
		                System.out.println("Could not log in");
		            }
		            System.out.println("Remote system is " + ftp.getSystemType());
		            if (binaryTransfer) {
		                ftp.setFileType(FTP.BINARY_FILE_TYPE);
		            } else {
		                // "in theory this should not be necessary as servers should default to ASCII
		                // but they don't all do so - see NET-500"
		                ftp.setFileType(FTP.ASCII_FILE_TYPE);
		            }

		            // "Use passive mode as default because most of us are behind firewalls these days."
		            if (localActive) {
		                ftp.enterLocalActiveMode();
		            } else {
		                ftp.enterLocalPassiveMode();
		            }
		            ftp.setUseEPSVwithIPv4(useEpsvWithIPv4);

		            System.out.println("Connected");
		            //connected and everything is ok from here
		            ftp.noop(); // check that control connection is working OK
		        }
	            catch(FTPConnectionClosedException e){
	    	        //error = true;
	    	        System.err.println("Server closed connection.");
	    	        e.printStackTrace();
	    	    }catch (IOException e){
	    	        //error = true;
	    	        e.printStackTrace();
	    	    }
	}

	//creates a string array of x values formatted to y decimal places, ranging between a min and max value
	public void createSyntheticValues(int numberOfValuesToCreate, int minValue, int maxValue, int decimalPlaces){
		
		synthetic_values = new String[numberOfValuesToCreate];
		String pattern = "###.";
		double tempValue;
		
		for(int i = 0; i < decimalPlaces; i++){
			pattern = pattern + "0";
		}
		
		DecimalFormat formatter = new DecimalFormat(pattern);
		
		for(int i = 0; i < numberOfValuesToCreate; i++){
			tempValue = (double) (Math.random() * (maxValue-minValue)) + minValue;
			synthetic_values[i] = formatter.format(tempValue);
		}
		
	}
	
	public void printSyntheticValues(){
		for(int i = 0; i < synthetic_values.length; i++){
			System.out.println(synthetic_values[i]);
		}
	}

}
