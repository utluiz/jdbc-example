package br.com.starcode.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.starcode.domain.Login;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class LoginDaoTest {
	
	private static LoginDao loginDao;
	private static DataSource ds;
	
	/**
	 * Método executado uma vez antes de começar os testes da classe.
	 * Inicializa o DataSource que conecta ao banco de dados.
	 * Cria tabela no banco em memória.
	 */
	@BeforeClass
	public static void setup() throws Exception {
		
		try {
			
			//carregar Driver do banco de dados MySQL
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			//inicializar DataSource do MySQL
			MysqlDataSource mysqlDataSource = new MysqlDataSource();
			mysqlDataSource.setUrl("jdbc:mysql://localhost:3311/login_test");
			mysqlDataSource.setUser("root");
			mysqlDataSource.setPassword("root");
			
			//criar uma conexão diretamente com o DriverManager, caso necessário
			//Connection c = DriverManager.getConnection(
			//	"jdbc:mysql://localhost:3311/login_test", "root", "root");
			
			//Obter conexão do DataSource
			Connection c = mysqlDataSource.getConnection();
			
			//Cria tabela no banco 
			c.createStatement().execute(
					"create table if not exists LOGIN ( " +
			        " codigo int auto_increment primary key, " +
			        " email varchar(70), " +
			        " nome varchar(70), " +
			        " senha varchar(30), " +
			        " ultimo_acesso datetime, " +
			        " tentativas int default 0)"
			    );
			
			//cria DAO do login passando o DataSource
			loginDao = new LoginDao(mysqlDataSource);
			
			//disponibiliza o dataSource para uso nos testes da classe
			ds = mysqlDataSource;
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public static void destroy() throws Exception {
		//finalização: não é necessário
	}

	@Test
	public void testTurma() throws Exception {
		
		//Limpa o registro da tabela, caso exista
		ds.getConnection().createStatement().execute("delete from login");
		
		//cria novo login
		Login novoLogin = new Login();
		novoLogin.setEmail("utluiz@gmail.com");
		novoLogin.setNome("Luiz");
		novoLogin.setSenha("123456");
		loginDao.insert(novoLogin);
		
		//recupera
		Login login = loginDao.find(novoLogin.getCodigo());

		//verifica
		Assert.assertEquals(novoLogin.getCodigo(), login.getCodigo());
		Assert.assertEquals("utluiz@gmail.com", login.getEmail());
		Assert.assertEquals("Luiz", login.getNome());
		Assert.assertEquals(0, login.getTentativasLogin());
		
		//atualiza
		novoLogin.setSenha("654321");
		novoLogin.setTentativasLogin(1);
		Date ultimoAcesso = new Date();
		novoLogin.setUltimoAcesso(ultimoAcesso);
		loginDao.update(novoLogin);
		
		//recupera de novo
		login = loginDao.find(novoLogin.getCodigo());
		
		//verifica de novo
		Assert.assertEquals(novoLogin.getCodigo(), login.getCodigo());
		Assert.assertEquals("utluiz@gmail.com", login.getEmail());
		Assert.assertEquals("Luiz", login.getNome());
		Assert.assertEquals("654321", login.getSenha());
		Assert.assertEquals(1, login.getTentativasLogin());
		Assert.assertEquals(
				new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ultimoAcesso),
				new SimpleDateFormat("dd/MM/yyyy HH:mm").format(login.getUltimoAcesso()));
		
		//lista
		List<Login> logins = loginDao.list();
		Assert.assertEquals(1, logins.size());
		
		//cria outro login
		novoLogin = new Login();
		novoLogin.setEmail("luiza@gmail.com");
		novoLogin.setNome("Luiza");
		novoLogin.setSenha("456789");
		loginDao.insert(novoLogin);
		
		//lista de novo
		logins = loginDao.list();
		Assert.assertEquals(2, logins.size());
		
		//busca por email
		login = loginDao.findByEmail("luiza@gmail.com");
		Assert.assertEquals("Luiza", login.getNome());
		
		//filtra por nome
		logins = loginDao.listByNome("uiz");
		Assert.assertEquals(2, logins.size());
		
		//filtra por nome de novo
		logins = loginDao.listByNome("uiza");
		Assert.assertEquals(1, logins.size());
		
		//deleta
		loginDao.delete(login.getCodigo());
		
		//verifica se apagou
		logins = loginDao.listByNome("uiz");
		Assert.assertEquals(1, logins.size());
		
		logins = loginDao.list();
		Assert.assertEquals(1, logins.size());
		
		//sucesso!
		
	}
	
}
