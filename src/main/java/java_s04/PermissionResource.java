package java_s04;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import beans.Permission;
import dao.PermissionDAO;

/**
 * 従業員関連のサービス実装。
 * Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
 */
@Path("Permission")
public class PermissionResource {
	private final PermissionDAO PerDao = new PermissionDAO();

	@GET
//	@Path("{username},{type}")
	public List<Permission> findAll(@QueryParam("username") String id,@QueryParam("type") String type) {
		return PerDao.findByParam(id,type);
	}


	/**
	 * 指定した従業員情報を登録する。
	 *
	 * @param form 従業員情報（画像含む）を収めたオブジェクト
	 * @return DB上のIDが振られた従業員情報
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */

	@POST
	public Permission create(final FormDataMultiPart form) throws WebApplicationException {

		Permission permission = new Permission();
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		permission.setRequestedDate(formatter.format(date));
		permission.setUpdatedDate(formatter.format(date));
		permission.setReqPersonId(form.getField("username").getValue());
		permission.setTitle(form.getField("title").getValue());
		permission.setPayAt(form.getField("payAt").getValue());
		permission.setMoney(Integer.parseInt(form.getField("money").getValue()));
		permission.setStatus(1);
		permission.setUpdatePersonId(form.getField("username").getValue());

		return PerDao.create(permission);
	}

	/**
	 * 指定した情報でDBを更新する。
	 *
	 * @param form 更新情報を含めた従業員情報
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */
	@PUT
	@Path("{id}")
	public Permission update(@PathParam("id") int id,
			final FormDataMultiPart form) throws WebApplicationException {
		Permission permission = new Permission();

		permission.setId(id);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		permission.setRequestedDate(form.getField("created_date").getValue());
		permission.setUpdatedDate(formatter.format(date));
		permission.setReqPersonId(form.getField("request_person").getValue());
		permission.setTitle(form.getField("title").getValue());
		permission.setPayAt(form.getField("payAt").getValue());
		permission.setMoney(Integer.parseInt(form.getField("money").getValue()));
		permission.setStatus(1); //申請中
		permission.setUpdatePersonId(form.getField("username").getValue());

		return PerDao.update(permission);
	}

	/**
	 * 指定したIDの社員情報を削除する。同時に画像データも削除する。
	 *
	 * @param id 削除対象の社員情報のID
	 */
	@DELETE
	@Path("{id}")
	public void remove(@PathParam("id") int id) {
		PerDao.remove(id);
	}

}
