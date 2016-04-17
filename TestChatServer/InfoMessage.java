import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class InfoMessage implements JSONAware {

	private String nameUser;
	private int id;
	private String text;
	private boolean deleted;
	private boolean changed;
	
	public InfoMessage() {
		nameUser = "none";
		text = "";
		id = -1;		
	}
	public InfoMessage(String text,String nameUser) {
		this.nameUser = nameUser;
		this.text = text;
		id = -1;		
	}
	public InfoMessage(int id, String nameUser, String message) {
		this.id = id;
		this.nameUser = nameUser;
		this.text = message;
	}
	public void setMessage(String message) {
		this.text = message;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public void setID(int id) {
		this.id = id;
	}
	public void setDelete(boolean deleted) {
		this.deleted = deleted;
	}
	public void setChange(boolean changed) {
		this.changed = changed;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getNameUser() {
		return nameUser;
	}
	public int getID() {
		return id;
	}
	public String getText() {
		return text;
	}
	public boolean isDelete() {
		return deleted;
	}
	public boolean getChange() {
		return changed;
	}
	
	public void deleteMessage() {
		if(deleted != true) {
			this.text = "message has deleted.";
			this.setDelete(true);
		}
	}
	public static InfoMessage parseInfoMessage(JSONObject obj) {
		InfoMessage info = new InfoMessage();
		
		info.setNameUser((String)obj.get("user"));
		info.setMessage((String)obj.get("message"));
		if(obj.get("id") != null) {
		info.setID(Integer.parseInt(obj.get("id").toString()));
		}
		return info;
	}	

	@Override
	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("user", nameUser);
		obj.put("message", text);
		obj.put("id", id);
		return obj.toString();
	}
	@Override
	public String toString() {
		return nameUser+" : "+text;
	}
	@Override
	public boolean equals(Object obj) {
		return (((InfoMessage)obj).getID()==id);
	}
	@Override
    public int hashCode() {
         return 1*id+2*text.hashCode()+3*nameUser.hashCode();
 }
}
