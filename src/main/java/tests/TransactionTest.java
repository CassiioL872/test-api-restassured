package tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;
import static utils.Utils.*;

import org.junit.Test;

import core.BaseTest;
import pojo.TransactionPojo;

public class TransactionTest extends BaseTest {
	
	@Test
	public void shouldCreateTransaction() {
		TransactionPojo mov = getMovimentacao();  
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			.body("id", notNullValue())
			.body("descricao", is("descricao da movimentacao"))
			.body("envolvido", is("envolvido na mov"))
			.body("tipo", is("REC"))
			.body("valor", is("100.00"))
			.body("status", is(true))
			.body("conta_id", notNullValue())
			.body("usuario_id", notNullValue())
	;}
	
	@Test
	public void shouldValidateRequiredFieldTransaction() {
		given()
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg", hasItems(
					"Data da Movimenta��o � obrigat�rio",
					"Data do pagamento � obrigat�rio",
					"Descri��o � obrigat�rio",
					"Interessado � obrigat�rio",
					"Valor � obrigat�rio",
					"Valor deve ser um n�mero",
					"Conta � obrigat�rio",
					"Situa��o � obrigat�rio"
					))
			;}
	
	@Test
	public void shouldNotCreateFutureTransaction() {
		TransactionPojo mov = getMovimentacao();
		mov.setData_transacao(getDateWithDaysDifference(2));
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("msg", hasItems("Data da Movimenta��o deve ser menor ou igual � data atual"))
	;}
	
	@Test
	public void shouldNotRemoveAccountWithTransaction() {
		int ACCOUNT_ID = getIdAccountByName("Conta com movimentacao");
		
		given()
			.pathParam("id", ACCOUNT_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
	;}
	
	@Test
	public void shouldRemoveTransaction() {
		int TRANSACTION_ID = getIdTransactionByDesc("Movimentacao para exclusao");
		
		given()
			.pathParam("id", TRANSACTION_ID)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
	;}
	
	private TransactionPojo getMovimentacao() {
		TransactionPojo mov = TransactionPojo.builder()
				.conta_id(getIdAccountByName("Conta para movimentacoes"))
				.descricao("descricao da movimentacao")
				.envolvido("envolvido na mov")
				.tipo("REC")
				.data_transacao(getDateWithDaysDifference(-1))
				.data_pagamento(getDateWithDaysDifference(5))
				.valor(100f)
				.status(true)
				.build();
		return mov;
	}
}
