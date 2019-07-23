package com.trusthub.cobranca.domain.arquivo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * DTO- Dados de resposta  
 * @author alan.franco
 */
public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Conteúdo do Arquivo")
	@JsonProperty("arquivo_conteudo")
	private String fileContent;

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	@Override
	public String toString() {
		return "DocumentResponseDto [fileContent=" + fileContent + "]";
	}
}
