package com.trusthub.cobranca.domain.arquivo;


import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO - Dados de resposta arquivo
 * @author alan.franco
 */
@Data
public class FileResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Id do Arquivo")
	@JsonProperty("idArquivo")
	@NotEmpty
	private Integer idArquivo;
	
	@ApiModelProperty(value = "Nome do Arquivo")
	@JsonProperty("arquivoNomeOriginal")
	@NotEmpty
	private String name;
	
	@ApiModelProperty(value = "Extensão do Arquivo")
	@JsonProperty("arquivoExtensao")
	@NotEmpty
	private String extension;


}
