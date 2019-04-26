package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Login;

/**
 * 部署データを扱うDAO
 */
public class LoginDao {
	/**
	 * クエリ文字列
	 */
	private static final String SELECT_ID = "select USER_CD,PASSWORD,USER_TYPE from MS_USER where USER_CD = ?";


	public Login checkAccount(Login login) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return login;
		}
		try (PreparedStatement statement =  connection.prepareStatement(SELECT_ID)) {
			statement.setString(1, login.getUsername());
			ResultSet rs = statement.executeQuery();

			if (rs.next()){
			String PasswordAns = rs.getString("PASSWORD");
				if( login.getPassword().equals(PasswordAns)){
					String Usertype = rs.getString("USER_TYPE");
					login.setType(Usertype);
					login.setPassword(null);
					login.setIsLogin(1);
				}
				else {
					login.setIsLogin(0);
				}

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return login;
	}


}
