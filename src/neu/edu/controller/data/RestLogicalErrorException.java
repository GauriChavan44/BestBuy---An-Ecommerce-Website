package neu.edu.controller.data;

public class RestLogicalErrorException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResponseError responseError;
	
	
	
	public RestLogicalErrorException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
		responseError = new ResponseError(message);
	}

	public ResponseError getResponseError() {
		return responseError;
	}
	
}
