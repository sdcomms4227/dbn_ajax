package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3307/ajax";
			String dbID = "dbn";
			String dbPassword = "1234";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<UserDTO> search(String userName) {
		String sql = "select * from user where userName like ?";
		ArrayList<UserDTO> userList = new ArrayList<UserDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + userName + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserName(rs.getString(1));
				userDTO.setUserAge(rs.getInt(2));
				userDTO.setUserGender(rs.getString(3));
				userDTO.setUserEmail(rs.getString(4));
				userList.add(userDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	public int register(UserDTO userDTO) {
		String sql = "insert into user values(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userDTO.getUserName());
			pstmt.setInt(2, userDTO.getUserAge());
			pstmt.setString(3, userDTO.getUserGender());
			pstmt.setString(4, userDTO.getUserEmail());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
