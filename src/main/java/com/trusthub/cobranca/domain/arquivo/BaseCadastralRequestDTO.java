package com.trusthub.cobranca.domain.arquivo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * DTO - Dados cadastral arquivo 
 * @author alan.franco
 */
public class BaseCadastralRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Id do Arquivo")
	@JsonProperty("arquivo_id")
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "DocumentoBaseCadastralRequestDto [fileId=" + fileId + "]";
	}
}
