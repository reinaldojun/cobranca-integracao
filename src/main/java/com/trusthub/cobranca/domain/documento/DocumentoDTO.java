package com.trusthub.cobranca.domain.documento;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO - Documento
 * @author alan.franco
 */
@Data
public class DocumentoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Id do Documento")
	@JsonProperty("id_documento")
	private Long idDocumento;
	
	@ApiModelProperty(value = "id Status do Documento")
	@JsonProperty("id_documento_status")
	private Long idDocumentoStatus;
	
	@ApiModelProperty(value = "Nome Status do Documento")
	@JsonProperty("nome_status_documento")
	private String nomeStatusDocumento;
	
	@ApiModelProperty(value = "Sigla")
	@JsonProperty("sigla")
	private String sigla;
	
	@ApiModelProperty(value = "Nome")
	@JsonProperty("nome")
	private String nome;
	
	@ApiModelProperty(value = "Id Arquivo")
	@JsonProperty("id_arquivo")
	private Long idArquivo;
	
	@ApiModelProperty(value = "Extensão")
	@JsonProperty("extensao")
	private String extensao;
	
	@ApiModelProperty(value = "Data de Exclusão")
	@JsonProperty("data_exclusao")
	private Date dataExclusao;
	
	
}
