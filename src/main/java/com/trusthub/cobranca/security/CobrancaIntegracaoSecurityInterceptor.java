package com.trusthub.cobranca.security;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
 
/**
 * Irá interceptar o cobranca acesso para validar se o token está nulo, e se está cadastrado.
 * Caso esteja tudo ok na seguranca deixa os endpoint serem acionados caso contrario da excecao.
 * @author alan.franco
 */
@Component
public class CobrancaIntegracaoSecurityInterceptor implements HandlerInterceptor  {
 
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private CobrancaIntegracaoSecurityService cobrancaIntegracaoSecurityService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(Constantes.AUTHORIZATION);
        String method = request.getMethod();
        String urlExecutada = request.getServletPath();
        
        //Percorre as Urls que serão liberadas para acesso
        for (UrlLiberadas url : UrlLiberadas.values()) {
        	 if(urlExecutada.contains(url.getDescricao()) ) {
             	return true;
             }
		}
        
        //IGNORA A PRIMEIRA REQUISICAO DO BROWSER PARA DEPOIS PEGAR A SEGUNDA REQUISICAO.
        if(method != null && !request.getMethod().equalsIgnoreCase(Constantes.OPTIONS)) {
	        if(authorization == null || authorization.equals(Constantes.VAZIO)) {
	        	 throw new CobrancaIntegracaoSecurityException(new StringBuilder(Mensagens.SECURITY_NO_AUTHORIZATION).toString()
	     				,CobrancaIntegracaoError.ERROR_COBRANCA_SECURITY, HttpStatus.UNAUTHORIZED);
	        }
	        
	        String validarToken = cobrancaIntegracaoSecurityService.validarToken(method.toUpperCase() + urlExecutada);
	        
	        if(Constantes.TOKEN_INVALIDO.equalsIgnoreCase(validarToken)){
	        	 throw new CobrancaIntegracaoSecurityException(Mensagens.SECURITY_TOKEN_INVALID
	      				,CobrancaIntegracaoError.ERROR_COBRANCA_SECURITY, HttpStatus.FORBIDDEN);
	        }
	        
	        if(Constantes.NAO_AUTORIZADO.equalsIgnoreCase(validarToken)){
	        	 throw new CobrancaIntegracaoSecurityException(Mensagens.SECURITY_PERFIL_NO_AUTHORIZATION
	      				,CobrancaIntegracaoError.ERROR_COBRANCA_SECURITY, HttpStatus.FORBIDDEN);
	        }
	    }
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
            Object handler, ModelAndView modelAndView) throws Exception {
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
            Object handler, Exception ex) throws Exception {
    	  response.setContentType("application/json");
    }
 
}