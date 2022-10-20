package suite;

import static io.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import core.BaseTest;
import core.Constants;
import io.restassured.RestAssured;
import pojo.AuthPojo;
import tests.AccountTest;
import tests.AuthTest;
import tests.TransactionTest;
import tests.BalanceTest;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
	AccountTest.class,
	TransactionTest.class,
	BalanceTest.class,
	AuthTest.class
})
public class Suite extends BaseTest implements Constants {

	@BeforeClass
	public static void login() {
		AuthPojo auth = new AuthPojo();
		auth.setEmail(USER_EMAIL);
		auth.setSenha(USER_PASSWORD);
		
		 String TOKEN = given()
			.body(auth)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
		 
		 RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
		 RestAssured.get("/reset").then().statusCode(200);
	}
}
