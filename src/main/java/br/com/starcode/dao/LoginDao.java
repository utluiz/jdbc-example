package br.com.starcode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.starcode.domain.Login;

public class LoginDao {
	
	private DataSource ds;
	
	public LoginDao(DataSource ds) {
		this.ds = ds;
	}
	
	public Login find(Integer codigo) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"select * from login where codigo = ?");
			ps.setInt(1, codigo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Login login = new Login();
				login.setCodigo(rs.getInt("codigo"));
				login.setEmail(rs.getString("email"));
				login.setNome(rs.getString("nome"));
				login.setSenha(rs.getString("senha"));
				login.setUltimoAcesso(rs.getTimestamp("ultimo_acesso"));
				login.setTentativasLogin(rs.getInt("tentativas"));
				return login;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Login findByEmail(String email) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"select * from login where email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Login login = new Login();
				login.setCodigo(rs.getInt("codigo"));
				login.setEmail(rs.getString("email"));
				login.setNome(rs.getString("nome"));
				login.setSenha(rs.getString("senha"));
				login.setUltimoAcesso(rs.getTimestamp("ultimo_acesso"));
				login.setTentativasLogin(rs.getInt("tentativas"));
				return login;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void insert(Login login) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"insert into login " +
					"(email, nome, senha, ultimo_acesso) "+ 
					"values (?, ?, ?, null)", 
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, login.getEmail());
			ps.setString(2, login.getNome());
			ps.setString(3, login.getSenha());
			ps.executeUpdate();
			ResultSet key = ps.getGeneratedKeys();
			if (key.next()) {
				login.setCodigo(key.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void update(Login login) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"update login "
					+ "set email = ?, nome = ?, senha = ?, "
					+ "ultimo_acesso = ?, tentativas = ? "
					+ "where codigo = ?");
			ps.setString(1, login.getEmail());
			ps.setString(2, login.getNome());
			ps.setString(3, login.getSenha());
			ps.setTimestamp(4, new java.sql.Timestamp(login.getUltimoAcesso().getTime()));
			ps.setInt(5, login.getTentativasLogin());
			ps.setInt(6, login.getCodigo());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void delete(Integer codigo) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"delete from login where codigo = ?");
			ps.setInt(1, codigo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<Login> list() {
		Connection con = null;
		Statement st = null;
		List<Login> res = new ArrayList<Login>();
		try {
			con = ds.getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select * from login");
			while (rs.next()) {
				Login login = new Login();
				login.setCodigo(rs.getInt("codigo"));
				login.setEmail(rs.getString("email"));
				login.setNome(rs.getString("nome"));
				login.setSenha(rs.getString("senha"));
				login.setUltimoAcesso(rs.getTimestamp("ultimo_acesso"));
				login.setTentativasLogin(rs.getInt("tentativas"));
				res.add(login);
			}
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<Login> listByNome(String nome) {
		Connection con = null;
		PreparedStatement ps = null;
		List<Login> res = new ArrayList<Login>();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"select * from login where upper(nome) like concat('%', ?, '%')");
			ps.setString(1, nome.toUpperCase());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Login login = new Login();
				login.setCodigo(rs.getInt("codigo"));
				login.setEmail(rs.getString("email"));
				login.setNome(rs.getString("nome"));
				login.setSenha(rs.getString("senha"));
				login.setUltimoAcesso(rs.getTimestamp("ultimo_acesso"));
				login.setTentativasLogin(rs.getInt("tentativas"));
				res.add(login);
			}
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
