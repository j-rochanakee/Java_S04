package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Permission;

/**
 * 社員データを扱うDAO
 */
public class PermissionDAO {
	/**
	 * クエリ文字列
	 */

	private static final String INSERT_QUERY = "INSERT INTO "
			+"BUDGET_REQ(CREATED_DATE,UPDATED_DATE,REQUEST_PERSON,TITLE,PAY_AT,AMOUNT_OF_MONEY,STATUS,UPDATED_PERSON) "
			+"VALUES(?,?,?,?,?,?,?,?)";


	private static final String UPDATE_QUERY = "UPDATE BUDGET_REQ "
							+"SET CREATED_DATE=?,UPDATED_DATE=?,REQUEST_PERSON=?,TITLE=?,PAY_AT=?,AMOUNT_OF_MONEY=?,STATUS=?,"
							+"UPDATED_PERSON=? WHERE ID = ?";

	private static final String DELETE_QUERY = "DELETE FROM BUDGET_REQ WHERE ID = ?";

	private static final String SELECT_ALL_QUERY = "select distinct ID,CREATED_DATE,UPDATED_DATE,REQUEST_PERSON,TITLE,AMOUNT_OF_MONEY,STATUS" +
			" from BUDGET_REQ,MS_USER" +
			" where MS_USER.USER_CD = BUDGET_REQ.REQUEST_PERSON" +
			" and request_person = ?" +
			" or MS_USER.USER_TYPE < ? order by ID" ;

	private static final String SELECT_BY_ID_QUERY = "select ID,CREATED_DATE,UPDATED_DATE,REQUEST_PERSON,TITLE,PAY_AT,AMOUNT_OF_MONEY,STATUS,UPDATED_PERSON,USER_TYPE" +
			" from BUDGET_REQ,MS_USER  where ID = ? and USER_CD=BUDGET_REQ.REQUEST_PERSON ";


	public List<Permission> findByParam(String username, String type) {
		List<Permission> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}


		try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
			statement.setString(1, username);
			statement.setString(2, type);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {

				result.add(processRow(rs,false));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	public Permission findById(int id) {
		Permission result = null;

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				result = processRow(rs,true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}


	public Permission create(Permission permission) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return permission;
		}
		String[] str_id =  { "ID" };//why is it neccessary
		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,str_id)) {
			// INSERT実行
			setParameter(statement, permission, false);
			statement.executeUpdate();

			// INSERTできたらKEYを取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			permission.setId(id);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return permission;
	}



	/**
	 * 指定されたEmployeeオブジェクトを使ってDBを更新する。
	 *
	 * @param employee 更新対象オブジェクト
	 * @return 更新に成功したらtrue、失敗したらfalse
	 */
	public Permission update(Permission permission) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return permission;
		}

		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
			setParameter(statement, permission, true);
			statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return permission;
	}

	/**
	 * 指定されたIDのPostデータを削除する。
	 *
	 * @param id 削除対象のPostデータのID
	 * @return 削除が成功したらtrue、失敗したらfalse
	 */
	public boolean remove(int id) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
			// DELETE実行
			statement.setInt(1, id);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}
		return count == 1;
	}


	/**
	 * 検索結果からオブジェクトを復元する。
	 *
	 * @param rs 検索結果が収められているResultSet。rs.next()がtrueであることが前提。
	 * @return 検索結果を収めたオブジェクト
	 * @throws SQLException 検索結果取得中に何らかの問題が発生した場合に送出される。
	 */
	private Permission processRow(ResultSet rs,boolean isDetail) throws SQLException {
		Permission result = new Permission();


		result.setId(rs.getInt("ID"));
		result.setUpdatedDate(rs.getString("UPDATED_DATE").substring(0,10));
		result.setRequestedDate(rs.getString("CREATED_DATE").substring(0,10));
		result.setReqPersonId(rs.getString("REQUEST_PERSON"));
		result.setTitle(rs.getString("TITLE"));
		result.setMoney(rs.getInt("AMOUNT_OF_MONEY"));
		result.setStatus(rs.getInt("STATUS"));
		if (isDetail){
			result.setPayAt(rs.getString("PAY_AT"));
			result.setUpdatePersonId(rs.getString("UPDATED_PERSON"));
			result.setReqPersonType(rs.getString("USER_TYPE"));
		}


		return result;
	}



	private void setParameter(PreparedStatement statement, Permission permission, boolean forUpdate) throws SQLException {
		int count = 1;

		statement.setString(count++, permission.getRequestedDate());
		statement.setString(count++, permission.getUpdatedDate());
		statement.setString(count++, permission.getReqPersonId());
		statement.setString(count++, permission.getTitle());
		statement.setString(count++, permission.getPayAt());
		statement.setInt(count++, permission.getMoney());
		statement.setInt(count++, permission.getStatus());
		statement.setString(count++, permission.getUpdatePersonId());
		if (forUpdate) {
			statement.setInt(count++, permission.getId());
		}
	}
}
