package br.com.starcode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.starcode.domain.Turma;

public class TurmaDAO {
	
	private DataSource ds;
	
	public TurmaDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public Turma find(Integer id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("select * from TB_TURMA where id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Turma t = new Turma();
				t.setId(rs.getInt("id"));
				t.setDescricao(rs.getString("descricao"));
				t.setInicioAulas(rs.getDate("inicio_aulas"));
				return t;
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
	
	public void insert(Turma t) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement("insert into TB_TURMA (descricao, inicio_aulas) values (?, ?)");
			ps.setString(1, t.getDescricao());
			ps.setDate(2, new java.sql.Date(t.getInicioAulas().getTime()));
			ps.executeUpdate();
			ResultSet key = ps.getGeneratedKeys();
			if (key.next()) {
				t.setId(key.getInt(1));
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
	
	public void update(Turma t) {
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
	
	public List<Turma> list() {
		Connection con = null;
		Statement st = null;
		List<Turma> res = new ArrayList<Turma>();
		try {
			con = ds.getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from TB_TURMA");
			while (rs.next()) {
				Turma t = new Turma();
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
	
}
