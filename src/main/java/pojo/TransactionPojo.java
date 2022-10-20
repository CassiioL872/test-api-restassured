package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPojo {

	private int id;
	private String descricao;
	private String envolvido;
	private String tipo;
	private String data_transacao;
	private String data_pagamento;
	private Float valor;
	private boolean status;
	private int conta_id;
	private int usuario_id;
}
