package com.trusthub.cobranca.controller.atendimento;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trusthub.cobranca.application.business.atendimento.AtendimentoJuridicoIntegracaoBusiness;
import com.trusthub.cobranca.domain.atendimento.AtendimentoJuridicoIntegracaoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller que disponibiliza APIs de atendimento juridico
 * @author alan.franco
 */
@RestController
@CrossOrigin(value = "*")
@RequestMapping("/trusthub-cobranca-integracao/v1")
@Api(value = "API - Atendimento Jurídico - Serviço que gerencia as regras de Atendimento Jurídico.")
public class AtendimentoJuridicoIntegracaoController{
	
	@Autowired
	private AtendimentoJuridicoIntegracaoBusiness business;
	
	@ApiOperation(value = "Inserir Atendimento Jurídico")
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
														+ "  - errorCode: 6 - Erro na api (Cobrança Integracao) com integração com o componente cobranca acesso \n"
														+ "  - errorCode: 1000 - Erro na api (Emprestimo Operação) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Emprestimo Operação) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Emprestimo Operação) na camada de Acesso a dados \n"
							)
			}
	)
	@PostMapping("/atendimento/juridico")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ResponseBody
	public ResponseEntity<Void> incluirAtendimentoJuridico(@RequestParam(value = "atendimento") String atendimento, @RequestPart("arquivos") List<MultipartFile> arquivos) {
		business.inserirAtendimentoJuridicoArquivo(atendimento, arquivos);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Consulta dados Historico Juridico")
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
														+ "  - errorCode: 6 - Erro na api (Cobrança Integracao) com integração com o componente cobranca acesso \n"
														+ "  - errorCode: 1000 - Erro na api (Emprestimo Operação) na camada de Business \n"
														+ "  - errorCode: 2000 - Erro na api (Emprestimo Operação) na camada de Service \n"
														+ "  - errorCode: 3000 - Erro na api (Emprestimo Operação) na camada de Acesso a dados \n"
							)
			}
	)
	@GetMapping("atendimento/historico/juridico/cedente/{idCedente}/sacado/{idSacado}")
	public ResponseEntity<List<AtendimentoJuridicoIntegracaoDTO>> consultarHistoricoJuridico(@PathVariable(value = "idCedente", required = false) String idCedente, 
			@PathVariable(value = "idSacado", required = false) String idSacado, @RequestParam(required = false) Integer idTitulo) {
		List<AtendimentoJuridicoIntegracaoDTO> listaAtendimentoArquivoDTO = business.consultarHistoricoJuridicoPorCedenteSacado(idCedente, idSacado, idTitulo);
		return ResponseEntity.ok().body(listaAtendimentoArquivoDTO);
	}
	
}
