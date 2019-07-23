package com.trusthub.cobranca.domain.atendimento;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO - Atendimento Arquivo Integracao
 * @author alan.franco
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AtendimentoArquivoIntegracaoDTO extends AuditoriaIntegracaoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Id")
	@JsonProperty("id")
	private Integer id;

	@ApiModelProperty(value = "Id do Arquivo")
	@JsonProperty("idArquivo")
	private Long idArquivo;

	@ApiModelProperty(value = "Tipo do Arquivo")
	@JsonProperty("tipoArquivo")
	private String tipoArquivo;

	@ApiModelProperty(value = "Nome")
	@JsonProperty("nome")
	private String nome;

	@ApiModelProperty(value = "Extensão")
	@JsonProperty("extensao")
	private String extensao;

}


