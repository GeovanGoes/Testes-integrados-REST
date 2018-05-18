/**
 * 
 */
package br.com.caelum.leilao.teste;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.*;

/**
 * @author geovan.goes
 *
 */
public class OutrosTeste
{
	@Test
	public void deveGerarUmCookie()
	{
		expect().cookie("rest-assured", "funciona").when().get("/cookie/teste");
	}

	@Test
	public void deveGerarUmHeader()
	{
		expect().header("novo-header", "abc").when().get("/cookie/teste");
	}

}
