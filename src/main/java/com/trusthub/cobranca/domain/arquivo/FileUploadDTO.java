package com.trusthub.cobranca.domain.arquivo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * DTO - Dados de upload arquivo
 * @author alan.franco
 */
public class FileUploadDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Mensagem")
	@JsonProperty("mensagem")
	private String mensagem;
	
	@ApiModelProperty(value = "Sucesso")
	@JsonProperty("sucesso")
	private Boolean sucesso;
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Boolean getSucesso() {
		return sucesso;
	}
	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
	
	
}
