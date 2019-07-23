package com.trusthub.cobranca.domain.atendimento;

import java.io.Serializable;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO - Atendimento Status cobranca
 * @author alan.franco
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class AtendimentoStatusCobrancaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Id")
	@JsonProperty("id")
	private Long id;
	
	@ApiModelProperty(value = "Descrição")
	@JsonProperty("descricao")
	private String descricao;

}
