package com.trusthub.cobranca.domain.atendimento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trusthub.cobranca.domain.arquivo.FileRequestDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO - Atendimento Cobranca Integracao
 * @author alan.franco
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AtendimentoCobrancaIntegracaoDTO extends AuditoriaIntegracaoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Id")
	@JsonProperty("id")
	private Integer id;
	
	@ApiModelProperty(value = "Id do Sacado")
	@JsonProperty("idSacado")
	private String idSacado;
	
	@ApiModelProperty(value = "Id do Cedente")
	@JsonProperty("idCedente")
	private String idCedente;
	
	@ApiModelProperty(value = "Id do Sacado do SRM")
	@JsonProperty("idSacadoSRM")
	private Integer idSacadoSRM;
	
	@ApiModelProperty(value = "Id do Cedentedo SRM")
	@JsonProperty("idCedenteSRM")
	private Integer idCedenteSRM;
	
	@ApiModelProperty(value = "Id Status Atendimento")
	@JsonProperty("idStatusAtendimento") 
	private Long idStatusAtendimento;
	
	@ApiModelProperty(value = "Status Atendimento")
	@JsonProperty("statusAtendimento") 
	private String statusAtendimento;
	
	@ApiModelProperty(value = "Descrição")
	@JsonProperty("descricao")   
	private String descricao;
	
	@ApiModelProperty(value = "Data de Retorno")
	@JsonProperty("dataRetorno")  
	private String dataRetorno;
	
	@ApiModelProperty(value = "Data de Previsao")
	@JsonProperty("dataPrevisao")  
	private String dataPrevisao;
	
	@ApiModelProperty(value = "Lista de Arquivos DTO")
	@JsonProperty("arquivos")
	private List<FileRequestDTO> arquivos = new ArrayList<>();
	
	@ApiModelProperty(value = "Lista de Titulos")
	@JsonProperty("titulos")
	private List<Long> idsTitulos = new ArrayList<>();

}
