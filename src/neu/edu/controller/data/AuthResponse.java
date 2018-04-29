package neu.edu.controller.data;

public class AuthResponse {
	 private boolean success = true;
	 private UserSession user;
	 
	 public AuthResponse(UserSession user) {
		// TODO Auto-generated constructor stub
		 this.user = user;
	}
	 
	 
	 public boolean getSuccess(){
		 return success;
	 }
	 
	 public void setSuccess(boolean success){
		 this.success = success;
	 }
	 
	 public UserSession getUser() {
		return user;
	}
	 
	 public void setUser(UserSession user) {
		this.user = user;
	}
	 

}
