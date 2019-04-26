package beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login {


	private String username;
	private String password;
	private String type;
	private int IsLogin;



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsLogin() {
		return IsLogin;
	}

	public void setIsLogin(int isLogin) {
		IsLogin = isLogin;
	}


}
