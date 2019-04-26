package java_s04;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import beans.Login;
import dao.LoginDao;

/**
 * 従業員関連のサービス実装。
 * Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
 */
@Path("login")
public class LoginResource {
	private final LoginDao dao = new LoginDao();

	/**
	 * 一覧用に部署情報を全件取得する。
	 * @return 部署情報のリストをJSON形式で返す。
	 */
//	@POST
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Login login(final FormDataMultiPart form) throws WebApplicationException {
//			Login login = new Login();
//			login.setUsername(form.getField("username").getValue());
//			login.setPassword(form.getField("password").getValue());
//			return dao.checkAccount(login);
//		}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Login login(final FormDataMultiPart form)throws WebApplicationException {
			Login login = new Login();
			login.setUsername(form.getField("username").getValue());
			login.setPassword(form.getField("password").getValue());

			return dao.checkAccount(login);
		}



}
