package br.com.starcode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import br.com.starcode.domain.Login;

public class LoginDAO {
	
	private DataSource ds;
	
	public LoginDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public Login find(Integer id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("select * from login where codigo = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Login l = new Login();
				l.setCodigo(rs.getInt("codigo"));
				l.setEmail(rs.getString("email"));
				l.setNome(rs.getString("nome"));
				l.setSenha(rs.getString("senha"));
				l.setDateTime(rs.getDate("ultimo_acesso"));
				l.setTentativasLogin(rs.getInt("tentativas"));
				return l;
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
	
	public void insert(Login l) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(
					"insert into login " +
					"(email, nome, senha, ultimo_acesso) "+ 
					"values (?, ?, ?, null)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, l.getEmail());
			ps.setString(2, l.getNome());
			ps.setString(3, l.getSenha());
			ps.executeUpdate();
			ResultSet key = ps.getGeneratedKeys();
			if (key.next()) {
				l.setCodigo(key.getInt(1));
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
	/*
	public void update(Login t) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("update TB_TURMA set descricao = ?, inicio_aulas = ? where id = ?");
			ps.setString(1, t.getDescricao());
			ps.setDate(2, new java.sql.Date(t.getInicioAulas().getTime()));
			ps.setInt(3, t.getId());
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
	
	public void delete(Integer id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("delete TB_TURMA where id = ?");
			ps.setInt(1, id);
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
			ResultSet rs = st.executeQuery("select * from TB_TURMA");
			while (rs.next()) {
				Login t = new Login();
				t.setId(rs.getInt("id"));
				t.setDescricao(rs.getString("descricao"));
				t.setInicioAulas(rs.getDate("inicio_aulas"));
				res.add(t);
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
	 */	
}
