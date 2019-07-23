package com.trusthub.cobranca.domain.atendimento;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO - Com dados para auditoria 
 * @author alan.franco/jose.viana
 */
@Data
public class AuditoriaIntegracaoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "empresa")
	private String empresa;

	@ApiModelProperty(value = "id do Usuário")
	@JsonProperty("idUsuario")
	private Long idUsuario;
	
	@ApiModelProperty(value = "Nome do Usuário")
	@JsonProperty("nomeUsuario")
	private String nomeUsuario;
	
	@ApiModelProperty(value = "Data de Inclusão")
	@JsonProperty("dataInclusao")
	private LocalDateTime dataInclusao;
	
	@ApiModelProperty(value = "Data de Exclusão")
	@JsonProperty("dataExclusao")
	private LocalDateTime dataExclusao;

}
