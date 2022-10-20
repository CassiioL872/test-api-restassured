package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.Utils.getIdAccountByName;

import org.junit.Test;

import core.BaseTest;
import pojo.AccountPojo;

public class AccountTest extends BaseTest {

	private static AccountPojo conta = new AccountPojo();
	
	@Test
	public void shouldCreateAccount() {
		conta.setNome("insert account");
		
		given()
			.body(conta)
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
	;}
	
	@Test
	public void shouldUpdateAnAccount() {
		int ACCOUNT_ID = getIdAccountByName("Conta para alterar");
		conta.setNome("updated account");
		
		given()
			.body(conta)
			.pathParam("id", ACCOUNT_ID)
		.when()
			.put("/contas/{id}")
		.then()
			.statusCode(200)
			.body("id", is(ACCOUNT_ID))
			.body("nome", is("updated account"))
			.body("visivel", is(true))
			.body("usuario_id", notNullValue())
	;}
	
	@Test
	public void shouldNotCreateAccountWithSameName() {
		conta.setNome("Conta mesmo nome");
		
		given()
			.body(conta)
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
	;}
	
}
