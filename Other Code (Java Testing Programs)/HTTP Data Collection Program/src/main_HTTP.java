//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//NGData. (N.D) Available from http://www.ngdata.com/parsing-a-large-json-file-efficiently-and-easily/ [Accessed 04/03/2015] JSON Parsing
//Apache Commons (N.D) Available from http://commons.apache.org/proper/commons-net/ [Accessed 20/03/2015] Networking Protocols
//FasterXML. (N.D) Available from http://wiki.fasterxml.com/JacksonInFiveMinutes [Accessed 08/03/2015] JSON Parsing
//JSON Test. (N.D) Available from http://time.jsontest.com/ [Accessed 10/04/2015] for the example JSON

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;


public class main_HTTP {
	
	static String username = "mchi2ro2";
	static String password = "";
	static boolean getSingleValue = true;
	
	public static void main(String[] args) throws ClientProtocolException, IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();	//Old code before credentials were added 
		/*CredentialsProvider credsProvider = new BasicCredentialsProvider(); //Credentials attempt
        credsProvider.setCredentials(
                new AuthScope("https://10.99.145.46/piwebapi", 443),
                new UsernamePasswordCredentials(username, password));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();*/
        try {
        	//HttpGet httpGet = new HttpGet("https://10.99.145.46/piwebapi"); //Attempting to conenct to the PI Web API
            HttpGet httpGet = new HttpGet("http://time.jsontest.com/"); //Site for testing JSON
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            
            try {
                System.out.println(response1.getStatusLine());	//Get the response from the server and print it
                HttpEntity entity1 = response1.getEntity();	//Get the entity from the response; this will be the JSON object/s
                
                //Testing
                //System.out.println(entity1);	//Print out the entity. You can see the entity type here: the entity contains JSON
                
                String s = EntityUtils.toString(entity1);	//Convert the entity (JSON) to a string so that its contents can be manipulated

                //Testing
                System.out.println(s);	//Print out the string 
                
                Map<String,String> map = new HashMap<String,String>();	//Create a new hash map to contain the JSON keys and values
                ObjectMapper mapper = new ObjectMapper();	//Create a new object mapper: this is used to convert the JSON to a POJO (Plain Old Java Object) 
                
                
                //If getting a single value
                if(!getSingleValue){
	                try{
	                	map = mapper.readValue(s, new TypeReference<HashMap<String,String>>(){});	//Map the string containing the JSON to hash map (a string is needed here for the parser)
	                }catch(Exception e){
	                	e.printStackTrace();
	                }
                } else{
                	//Process the JSON object by finding the items in the array
                	System.out.println("Handling multiple values:");
                	JsonFactory f = new JsonFactory();
                	JsonParser jp = f.createJsonParser(s);
                	jp.nextToken();	//puts jp to start of the JSON object or array
                	
                	while(jp.nextToken() != JsonToken.END_OBJECT){ //loop until the end of the object
                		String name = jp.getCurrentName(); //extract the current token name
	                	if(name == "date"){	
	                		//check if the current token is "date"
	                		//for the Android project, this could be "Value" or "Timestamp"
	                		System.out.println("Found date!");
	                		jp.nextToken();
	                		System.out.println(jp.getText());
	                	} else{
	                		System.out.println("Unprocessed property: " + name);
	                        jp.skipChildren();
	                        jp.nextToken();
	                	}
                	}		//Testing
	                		/*while (jp.nextToken() != JsonToken.END_ARRAY) {	//use this for iterating through an array
	                            // read the record into a tree model,
	                            // this moves the parsing position to the end of it
	                            JsonNode node = jp.readValueAsTree();
	                            // And now we have random access to everything in the object
	                            System.out.println("date 1: " + node.get("date").getValueAsText());
	                            //System.out.println("field2: " + node.get("field2").getValueAsText());
	                          }*/
	                		
	                		//Project PI Web API code
	                		/*while (jp.nextToken() != JsonToken.END_ARRAY) {	
	                			//process a single JSON Object
	                			while(jp.nextToken() != JsonToken.END_OBJECT){
	                				name = jp.getCurrentName();
	                				if(name == "timestamp"){
	                					jp.nextToken();
	                					//place the timestamp in an array or a map (choose)
	                				} else if(name == "value"){
	                					jp.nextToken();
	                					//place the value in an array or a map (choose)
	                				} else{	//the current token is of no interest. In fact, the rest of the object is of no interest, so we can skip it
	                					jp.skipChildren();
	                					//jp.nextToken();
	                				}
	                			}
	                			//jp.nextToken();
	                            //JsonNode node = jp.readValueAsTree();
	                            //System.out.println("date 1: " + node.get("date").getValueAsText());
	                            //System.out.println("field2: " + node.get("field2").getValueAsText());
	                      }*/
	                		
	                	/*} else{
	                		System.out.println("Unprocessed property: " + name);
	                        jp.skipChildren();
	                        jp.nextToken();
	                	}*/
                	//}
                	/*jp.nextToken();	//time
                	System.out.println(jp.getCurrentName());	//prints the tokens key
                	//System.out.println(jp.getCurrentToken());	//prints FIELD Name
                	//System.out.println(jp.getText());	//Prints the current tokens value
                	
                	jp.skipChildren();
                	jp.nextToken();
                	System.out.println(jp.getCurrentName());	//Prints the tokens key
                	System.out.println(jp.getCurrentToken());	//Prints Value_String
                	System.out.println(jp.getText());	//Prints the current tokens value
                	
                	jp.nextToken();
                	System.out.println(jp.getCurrentName());	//Prints time
                	System.out.println(jp.getCurrentToken());	//Prints Value_String
                	System.out.println(jp.getText());	//Prints the current tokens value
                	// and then each time, advance to opening START_OBJECT
                	while (jp.nextToken() != JsonToken.END_OBJECT) {
                		String fieldName = jp.getCurrentName();
                		//System.out.println(fieldName);
                		
	                	//Foo foobar = mapper.readValue(jp, Foo.class);
	                	// process
	                	// after binding, stream points to closing END_OBJECT
                		
                	}	*/
                }
                //Testing
                //System.out.println("\nStart");	//Notice
                //System.out.println(map);		//Print out the contents of the hash map
                //System.out.println(map.get("time"));	//Print out a specific value from the hash map
                //System.out.println(map.get("date"));	//Print out a specific value from the hash map
                
                EntityUtils.consume(entity1);	//Ensure the entity is fully consumed
 
            } finally {
                response1.close();
            }
        } finally {
            httpclient.close();
        }
		
	}

}
