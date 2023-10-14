package exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import payload.ErrorDetails;

@ControllerAdvice  //-> handles exceptions globally
public class GlobalException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFound e, WebRequest request){
		ErrorDetails error = new ErrorDetails(e.getMessage(), new Date(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
	}

	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, 
			HttpStatus status,
			WebRequest request) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldname =  ((FieldError)error).getField();
			String message = ((FieldError)error).getDefaultMessage(); //message attribute of fields
			map.put(fieldname, message);
			
		});
		return new ResponseEntity<Object>(map,HttpStatus.BAD_REQUEST);
		
	}
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Object> handleMethodArgNotValid(MethodArgumentNotValidException e, WebRequest request){
//		Map<String, String> map = new HashMap<>();
//		e.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldname =  ((FieldError)error).getField();
//			String message = ((FieldError)error).getDefaultMessage(); //message attribute of fields
//			map.put(fieldname, message);
//			
//		});
//		return new ResponseEntity<Object>(map,HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(Exception e, WebRequest request){
		ErrorDetails error = new ErrorDetails(e.getMessage(), new Date(), request.getDescription(true));
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
	}
	
	
}
