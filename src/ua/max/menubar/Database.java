package ua.max.menubar;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public Connection con;
	public Statement st;
	public ResultSet rs;

	public String login;
	public String password;
	private String sql;
	private boolean access = false;

	public Database() {
		connect();
	}

	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AuthorFX?user=root");
			st = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean register(String login, String password) {
		this.login = login;
		this.password = password;

		try {
			sql = "INSERT INTO users (login, password)" + " VALUES ('" + login + "','" + password + "');";

			String qq = "SELECT login, password FROM users WHERE login = '" + login + "' and password = '" + password
					+ "'";
			rs = st.executeQuery(qq);
			try {
				if (!rs.next()) {
					st.executeUpdate(sql);
					access = true;
				} else {
					access = false;
				}
			} catch (HeadlessException | SQLException z) {
				z.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return access;

	}

	public boolean login(String login, String password) {
		this.login = login;
		this.password = password;

		try {
			String sql = "SELECT login, password FROM users WHERE login = '" + login + "' and password = '" + password
					+ "'";
			rs = st.executeQuery(sql);
			try {
				if (!rs.next()) {
					access = false;
				} else {
					;
					access = true;
					rs.close();
					st.close();
					con.close();

				}
			} catch (HeadlessException | SQLException z) {
				z.printStackTrace();
			}
		} catch (Exception ex) {

		}
		return access;
	}

}
