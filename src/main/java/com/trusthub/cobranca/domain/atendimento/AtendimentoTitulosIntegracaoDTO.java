package com.trusthub.cobranca.domain.atendimento;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO - Atendimento titulo Integracao
 * @author alan.franco
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AtendimentoTitulosIntegracaoDTO extends AuditoriaIntegracaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Id")
	@JsonProperty("id")
	private Integer id;

	@ApiModelProperty(value = "Id do Título")
	@JsonProperty("idTitulo")
	private Long idTitulo;

	@ApiModelProperty(value = "Tipo do Arquivo")
	@JsonProperty("tipoArquivo")
	private String tipoArquivo;

}