package br.com.starcode.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.starcode.domain.Login;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class LoginDaoTest {
	
	private static LoginDAO loginDAO;
	
	@BeforeClass
	public static void setup() throws Exception {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			MysqlDataSource ds = new MysqlDataSource();
			ds.setUrl("jdbc:mysql://localhost/login_test?"
					+ "user=root&password=");
			ds.setUser("root");
			//Connection c = DriverManager.getConnection(
			//		"jdbc:mysql://localhost/login_test?"
			//		+ "user=root&password=");
			
			loginDAO = new LoginDAO(ds);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public static void destroy() throws Exception {
	}

	@Test
	public void testTurma() throws Exception {
		
		Login novoLogin = new Login();
		novoLogin.setEmail("utluiz@gmail.com");
		novoLogin.setNome("Luiz");
		novoLogin.setSenha("123456");

		//insere
		loginDAO.insert(novoLogin);
		
		//recupera
		Login login = loginDAO.find(novoLogin.getCodigo());

		//verifica
		Assert.assertEquals(novoLogin.getCodigo(), login.getCodigo());
		Assert.assertEquals("utluiz@gmail.com", login.getEmail());
		Assert.assertEquals("Luiz", login.getNome());
		Assert.assertEquals(0, login.getTentativasLogin());
		
		//sucesso!
		
	}
	
}
