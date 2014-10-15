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
					"values (?, ?, ?, null)", 
					Statement.RETURN_GENERATED_KEYS);
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
	
}
