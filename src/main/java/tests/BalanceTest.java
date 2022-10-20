package tests;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static utils.Utils.getIdAccountByName;

import org.junit.Test;
import core.BaseTest;

public class BalanceTest extends BaseTest {

	@Test
	public void shouldGetAccountBalance() {
		int ACCOUNT_ID = getIdAccountByName("Conta para saldo");
		
		when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+ACCOUNT_ID+"}.saldo", is("534.00"))
	;}
}
