package com.trusthub.cobranca.domain.arquivo;


import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO - Dados de request Arquivo 
 * @author alan.franco
 */
@Data
public class FileRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Id")
	@JsonProperty("componente_id")
	@NotEmpty
	private Integer id;
	
	@ApiModelProperty(value = "Id do Arquivo")
	@JsonProperty("idArquivo")
	@NotEmpty
	private Long idArquivo;

	@ApiModelProperty(value = "Conteúdo do Arquivo")
	@JsonProperty("arquivo")
	@NotEmpty
	private byte[] file;
	
	@ApiModelProperty(value = "Nome do Arquivo")
	@JsonProperty("arquivo_nome_original")
	@NotEmpty
	private String name;
	
	@ApiModelProperty(value = "Extensão do Arquivo")
	@JsonProperty("arquivo_extensao")
	@NotEmpty
	private String extension;
		
}
