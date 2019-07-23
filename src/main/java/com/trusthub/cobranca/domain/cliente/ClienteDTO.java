package com.trusthub.cobranca.domain.cliente;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO - Cliente
 * @author alan.franco
 */
@Data
public class ClienteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Id")
	@JsonProperty("id")
	private Integer id;
	
	@ApiModelProperty(value = "Id Segmento")
	@JsonProperty("idSegmento")
	private Long idSegmento;
	
	@ApiModelProperty(value = "Id Pessoa")
	@JsonProperty("idPessoa")
	private Long idPessoa;
	
	@ApiModelProperty(value = "Faturamento Anual")
	@JsonProperty("faturamentoAnual")
	private BigDecimal faturamentoAnual;
	
	@ApiModelProperty(value = "Id Conta Externa")
	@JsonProperty("idContaExterna")
	private Long idContaExterna;

}
