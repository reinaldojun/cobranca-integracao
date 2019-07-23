package com.trusthub.cobranca.controller.base.cadastral;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trusthub.cobranca.application.business.base.cadastral.DocumentoClientelntegracaoBusiness;
import com.trusthub.cobranca.domain.documento.DocumentoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
* Controller que disponibiliza APIs de documento
* @author alan.franco
*/
@RestController
@CrossOrigin(value = "*")
@RequestMapping("/trusthub-cobranca-integracao/v1")

@Api(value = "API - Documento - Serviço que gerencia as regras Documento.")
public class DocumentoIntegracaoController{
	
	@Autowired
	private DocumentoClientelntegracaoBusiness documentoIntegracaoBusiness;
	

	@ApiOperation(value = "Consultar Documento do Cliente")
	@ApiResponses(
			value= {
					@ApiResponse(code = 200 , message = "Sucessfull"),
					@ApiResponse(code = 401 , message = "Unauthorized"),
					@ApiResponse(code = 403 , message = "Access denied"),
					@ApiResponse(code = 500, message = "Erro Internal Server Error: Contact your support \n"
														+ "Mensagem de erro:  { timestamp, errorCode, errorDescription, message, path  }  \n"							
														+ "  - errorCode: 1 - Erro na api (Cobrança Integracao) na camada de Business \n"
														+ "  - errorCode: 2 - Erro na api (Cobrança Integracao) na camada de Service \n"
														+ "  - errorCode: 3 - Erro na api (Cobrança Integracao) com integração com o cobrança operação \n"
														+ "  - errorCode: 4 - Erro na api (Cobrança Integracao) com integração com o componente de arquivos \n"
														+ "  - errorCode: 5 - Erro na api (Cobrança Integracao) com integração com a base Cadastrais \n"
														+ "  - errorCode: 6 - Erro na api (Cobrança Integracao) com integração com o componente cobranca acesso \n"
														+ "  - errorCode: 1000 - Erro na api (Emprestimo Operação) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Emprestimo Operação) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Emprestimo Operação) na camada de Acesso a dados \n"
							)
			}
	)
	@GetMapping("/documentos/clientes/{documento}")
	@ResponseBody
	public ResponseEntity<List<DocumentoDTO>> consultarDocumentoCliente(@PathVariable(value = "documento", required = false) String documento) {
		List<DocumentoDTO> listDocumentDto = documentoIntegracaoBusiness.consultarDocumentoCliente(documento);
		return ResponseEntity.ok().body(listDocumentDto);
	}
	
}
