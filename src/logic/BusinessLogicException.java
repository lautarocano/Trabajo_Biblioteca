package logic;

public class BusinessLogicException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public BusinessLogicException(String errorMessage) {
		super(errorMessage);
	}
}
