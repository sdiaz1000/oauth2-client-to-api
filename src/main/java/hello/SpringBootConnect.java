package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableOAuth2Client
@SpringBootApplication
public class SpringBootConnect {


	static final String username = "<username>";
	static final String password = "<password>";
	static final String clientId = "<clientid";
	static final String clientSecret = "<clientsecret>";

	static final String grantService = "https://test.salesforce.com/services/oauth2/token";

	static final String url = "https://ons-connect--ons.my.salesforce.com";



	static final String adeccoBaseUrl = "https://ons-connect--ons.my.salesforce.com/services/data/v20.0/query/?q=";
	static final String query = "SELECT+TR1__External_Candidate_Id__c,FirstName,LastName,Address_Line_1__c,Address_Line_2__c,Town__c,County__c,Postcode__c,Primary_Email__c,TR1__Work_Email__c,HomePhone,MobilePhone,Emergency_Contact__c,Emergency_Contact_Number__c,TR1__Language__c,Are_you_prepared_to_drive_for_work__c,Are_you_prepared_to_travel_for_work__c,How_many_hours_per_week_can_you_work__c,Are_you_a_current_Civil_Servant__c,Have_you_ever_been_a_civil_servant__c,Pension_Benefit_Taken__c,TR1__Birthdate__c,Employment_Status__c,TR1__Drivers_License__c,Ethnicity__c,Disability_or_Health_Condition__c,Nationality__c,Gender__c,Sexual_Orientation__c,Religion_or_belief__c,Line_Manager__c,Desired_Work_Location__c+from+Contact";


	@Autowired(required = false)
	ClientHttpRequestFactory clientHttpRequestFactory;


	public static void main(String[] args) {
		SpringApplication.run(SpringBootConnect.class, args);
	}

	@Bean
	public CommandLineRunner demo(RestTemplate restTemplate) {
		return (args) -> {
			String string = restTemplate.getForObject(adeccoBaseUrl + query,String.class);
			System.out.println("by golly it worked");
			System.out.println(string);
			string = restTemplate.getForObject(adeccoBaseUrl + query,String.class);
			System.out.println("by golly it worked");
			System.out.println(string);
			string = restTemplate.getForObject(adeccoBaseUrl + query,String.class);
			System.out.println("by golly it worked");
			System.out.println(string);
		};
	}

	@Bean
	public OAuth2ClientContext oAuth2ClientContext() {
		return new DefaultOAuth2ClientContext();
	}

	@Bean
	public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {

		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

		resource.setAccessTokenUri(grantService);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setClientAuthenticationScheme(AuthenticationScheme.form);
		resource.setUsername(username);
		resource.setPassword(password);
		return resource;
	}

	@Bean
	public RestTemplate oAuthRestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails adecoOAuth2ProtectedResourceDetails) {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(adecoOAuth2ProtectedResourceDetails, oAuth2ClientContext);
		restTemplate.setAccessTokenProvider(userAccessTokenProvider());
		return restTemplate;
	}

	@Bean
	public AccessTokenProvider userAccessTokenProvider() {
		ResourceOwnerPasswordAccessTokenProvider accessTokenProvider = new ResourceOwnerPasswordAccessTokenProvider();
		accessTokenProvider.setRequestFactory(getClientHttpRequestFactory());
		return accessTokenProvider;
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		if (clientHttpRequestFactory == null) {
			clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		}
		return clientHttpRequestFactory;
	}

}
