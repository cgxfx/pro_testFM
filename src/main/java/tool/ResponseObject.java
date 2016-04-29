package tool;

public class ResponseObject<T> {

	private Integer code = Constants.SUCCESS;
	private String msg;
	private T clazz;
	
	public ResponseObject() {
	}
	
	public ResponseObject(T clazz) {
		this.clazz = clazz;
	}
	
	public ResponseObject(BizException e) {
		this.code = e.getErrorCode();
		this.msg = e.getErrorMsg();
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getClazz() {
		return clazz;
	}

	public void setClazz(T clazz) {
		this.clazz = clazz;
	}
}
