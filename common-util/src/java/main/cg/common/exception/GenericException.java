package cg.common.exception;

/*
 *$Log: GenericException.java,v $
 *Revision 1.1  2007/05/22 14:40:08  dorelv
 *moved projects from Charon CVS root, and created new ones for the publishing platform
 *
 *Revision 1.2  2005/12/19 17:57:34  dorelv
 *call super in constructor with the error code
 *
 *Revision 1.1  2005/12/05 15:35:14  dorelv
 *added new projects by braking the "dev" project and adding a couple more, to have a base for building components
 *
 *Revision 1.2  2005/10/07 19:20:16  ziak
 *added support to include the tokens to be replaced inside the localized message from the resoursebundle
 *
 *Revision 1.1  2005/09/14 20:35:05  ziak
 *added the logging and Exception stuff for the first time
 *
 * */

public class GenericException extends Exception {
    
	static final long serialVersionUID = -3387516993124229940L;
	
	private String _errorCode=null;
	private Object [] params = null;
	
	public GenericException() {
		super();
	}
	
	public GenericException(String errorCode) {
		super(errorCode);
		_errorCode=errorCode;
	}
	public GenericException(String errorCode, Object [] params) {
		super(errorCode);
		_errorCode=errorCode;
		this.params = params;
	}
	
	public GenericException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		_errorCode=errorCode;
	}

	public GenericException(String errorCode, String message, Object [] params, Throwable throwable) {
		super(message, throwable);
		_errorCode=errorCode;
		this.params=params;
	}
	
	public GenericException(String errorCode, String message) {
		super(message);
		_errorCode=errorCode;
	}
	
	public GenericException(String errorCode, String message, Object [] params) {
		super(message);
		_errorCode=errorCode;
		this.params=params;
	}

	public GenericException(String errorCode, Throwable throwable) {
		super(throwable);
		_errorCode=errorCode;
	}
	
	public GenericException(String errorCode, Object [] params, Throwable throwable) {
		super(throwable);
		_errorCode=errorCode;
		this.params=params;
	}

	public String getErrorCode() {
		return _errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		_errorCode = errorCode;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
