package beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Permission {
	private int id;
	private String ReqPersonId;
	private String UpdatePersonId;
	private String title;
	private String payAt;
	private int money;
	private int status;
	private String RequestedDate;
	private String UpdatedDate;
	private String reason;





	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

        sb.append(this.getReqPersonId());
        sb.append(",");
        sb.append(this.getUpdatePersonId());
        sb.append(",");
        sb.append(this.getTitle());
        sb.append(",");
        sb.append(this.getPayAt());
        sb.append(",");
        sb.append(this.getMoney());
        sb.append(",");
        sb.append(this.getStatus());
        sb.append(",");
        sb.append(this.getRequestedDate());
        sb.append(",");
        sb.append(this.getUpdatePersonId());
        String s = this.getReason();
        sb.append(s != null ? s : "");
        // バッファに書き出します
		return sb.toString();
	}

	public String getReqPersonId() {
		return ReqPersonId;
	}
	public void setReqPersonId(String reqPersonId) {
		ReqPersonId = reqPersonId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdatePersonId() {
		return UpdatePersonId;
	}
	public void setUpdatePersonId(String updatePersonId) {
		UpdatePersonId = updatePersonId;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRequestedDate() {
		return RequestedDate;
	}
	public void setRequestedDate(String requestedDate) {
		RequestedDate = requestedDate;
	}
	public String getUpdatedDate() {
		return UpdatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}
	public String getPayAt() {
		return payAt;
	}
	public void setPayAt(String payAt) {
		this.payAt = payAt;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



}
