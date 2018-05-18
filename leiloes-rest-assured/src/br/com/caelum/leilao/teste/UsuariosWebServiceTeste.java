/**
 * 
 */
package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.modelo.Usuario;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
/**
 * @author geovan.goes
 *
 */
public class UsuariosWebServiceTeste
{
	
	private Usuario mauricio;
	private Usuario esperado2;

	@Before
	public void antes ()
	{
		mauricio = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	
	@Test
	public void deveRetornarListaDeUsuarios()
	{
		XmlPath path = given()
						.header("Accept","application/xml")
						.get("/usuarios?_format=xml")
						.andReturn()
						.xmlPath();
		
		Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
		Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);
		
		
		
		assertEquals(usuario1, mauricio);
		assertEquals(usuario2, esperado2);
		
	}
	
	@Test
	public void deveRetornarUsuarioPeloId()
	{
		JsonPath jsonPath = given()
							.header("Accept","application/json")
							.parameter("usuario.id", 1)
							.get("/usuarios/show")
							.andReturn()
							.jsonPath();
		Usuario usuario1 = jsonPath.getObject("usuario", Usuario.class);
		
		assertEquals(usuario1, mauricio);
	}
	
	@Test
	public void deveAdicionarUsuario()
	{
		Usuario joao = new Usuario("João da Silva", "joao.silva@caelum.com.br");
		XmlPath path = given()
							.header("Accept","application/xml")
							.contentType("application/xml")
							.body(joao)
							.expect()
							.statusCode(200)
							.when()
							.post("/usuarios")
							.andReturn()
							.xmlPath();
		 Usuario resposta = path.getObject("usuario", Usuario.class);
		 
		 assertEquals(joao.getNome(), resposta.getNome());
		 assertEquals(joao.getEmail(), resposta.getEmail());
		 
		 given()
		 .contentType("application/xml").body(resposta)
		 .expect().statusCode(200)
		 .when().delete("/usuarios/deleta").andReturn().asString();
	}
}
