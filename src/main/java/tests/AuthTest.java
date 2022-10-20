package tests;

import static io.restassured.RestAssured.when;

import org.junit.Test;

import core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTest extends BaseTest {
	
	@Test
	public void shouldNotAcessAPIWithoutToken() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		
		when()
			.get("/contas")
		.then()
			.statusCode(401)
	;}
	
}
