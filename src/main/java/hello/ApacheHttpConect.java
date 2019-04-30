package hello;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ApacheHttpConect {

	static final String USERNAME = "<username>";
	static final String password = "<password>";
	static final String clientID = "<clientid";
	static final String clientSecret = "<clientsecret>";
	static final String grantService = "https://test.salesforce.com/services/oauth2/token?grant_type=password";

	static final String url = "https://ons-connect--ons.my.salesforce.com";

	static final String adeccoBaseUrl = "https://ons-connect--ons.my.salesforce.com/services/data/v20.0/query/?q=";
	static final String query = "SELECT+TR1__External_Candidate_Id__c,FirstName,LastName,Address_Line_1__c,Address_Line_2__c,Town__c,County__c,Postcode__c,Primary_Email__c,TR1__Work_Email__c,HomePhone,MobilePhone,Emergency_Contact__c,Emergency_Contact_Number__c,TR1__Language__c,Are_you_prepared_to_drive_for_work__c,Are_you_prepared_to_travel_for_work__c,How_many_hours_per_week_can_you_work__c,Are_you_a_current_Civil_Servant__c,Have_you_ever_been_a_civil_servant__c,Pension_Benefit_Taken__c,TR1__Birthdate__c,Employment_Status__c,TR1__Drivers_License__c,Ethnicity__c,Disability_or_Health_Condition__c,Nationality__c,Gender__c,Sexual_Orientation__c,Religion_or_belief__c,Line_Manager__c,Desired_Work_Location__c+from+Contact";

	
	
	public static void main(String args[]) throws IOException {

		HttpClient httpClient = HttpClientBuilder.create().build();
		String loginURL = grantService + "&client_id=" + clientID + "&client_secret=" + clientSecret + "&username="
				+ USERNAME + "&password=" + password;

		System.out.println(loginURL);

		HttpPost httpPost = new HttpPost(loginURL);

		HttpResponse response = null;

		String token = null;
		
		try {
			response = httpClient.execute(httpPost);

		
		      JSONObject obj = new JSONObject(EntityUtils.toString(response.getEntity()));
		      token = obj.getString("access_token");
		      System.out.println(token);
		      
		      String queryUrl = adeccoBaseUrl + query;
		      HttpGet httpGet = new HttpGet(queryUrl);
		      
		      httpGet.addHeader("Authorization", "Bearer " + token); 
	          response = httpClient.execute(httpGet); 
	          int responseCode = response.getStatusLine().getStatusCode();  
	          System.out.println(responseCode);
	          System.out.println(EntityUtils.toString(response.getEntity()));
		      
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}