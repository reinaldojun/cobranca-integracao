package com.trusthub.cobranca.domain.auditoria;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO - Usuario
 * @author alan.franco
 */
@Data
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer idEmpresa;
	private String nome;
	private String email;
	private String senha;
	private EmpresaDTO empresaDTO;
	
	
}
