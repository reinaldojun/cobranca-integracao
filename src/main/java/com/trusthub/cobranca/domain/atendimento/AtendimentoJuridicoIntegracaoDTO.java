package com.trusthub.cobranca.domain.atendimento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trusthub.cobranca.domain.arquivo.FileRequestDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO - Atendimento Juridico Integracao
 * @author alan.franco
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AtendimentoJuridicoIntegracaoDTO extends AuditoriaIntegracaoDTO implements Serializable {
	
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
	
	@ApiModelProperty(value = "Id Status Jurídico")
	@JsonProperty("idStatusJuridico")  
	private Long idStatusJuridico;
	
	@ApiModelProperty(value = "Status Jurídico")
	@JsonProperty("statusJuridico")  
	private String statusJuridico;
	
	@ApiModelProperty(value = "Número do Processo")
	@JsonProperty("numeroProcesso")  
	private String numeroProcesso;
	
	@ApiModelProperty(value = "Data de Distribuição")
	@JsonProperty("dataDistribuicao") 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date dataDistribuicao;

	@ApiModelProperty(value = "Autor")
	@JsonProperty("autor")  
	private String autor;
	
	@ApiModelProperty(value = "Réu")
	@JsonProperty("reu")  
	private String reu;

	@ApiModelProperty(value = "Comarca")
	@JsonProperty("comarca") 
	private String comarca;
	
	@ApiModelProperty(value = "Vara")
	@JsonProperty("vara")  
	private String vara;

	@ApiModelProperty(value = "Objeto")
	@JsonProperty("objeto") 
	private String objeto;

	@ApiModelProperty(value = "Uf")
	@JsonProperty("uf") 
	private String uf;
	
	@ApiModelProperty(value = "Descrição")
	@JsonProperty("descricao")   
	private String descricao;
	
	@ApiModelProperty(value = "Data de Retorno")
	@JsonProperty("dataRetorno")  
	private String dataRetorno;
	
	@ApiModelProperty(value = "Data de Previsao")
	@JsonProperty("dataPrevisao")  
	private String dataPrevisao;
	
	@ApiModelProperty(value = "Lista de Arquivo DTO")
	@JsonProperty("arquivos")
	private List<FileRequestDTO> arquivos= new ArrayList<>();
	
	@ApiModelProperty(value = "Lista de Titulos")
	@JsonProperty("titulos")
	private List<Long> idsTitulos = new ArrayList<>();
	

}
