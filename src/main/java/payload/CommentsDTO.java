package payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CommentsDTO {
	private long id;
	
	@NotEmpty(message = "Name should not be empty")
	private String name;
	
	@NotEmpty(message = "Email should not be empty")
	@Email(message = "Should be in abc@gmail.com format") //You have to give regex, bcz it doesn't follow any default email pattern
	private String email;
	
	@NotEmpty(message = "Content should not be empty")
	private String body;
	
	public CommentsDTO() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "CommentsDTO [id=" + id + ", name=" + name + ", email=" + email + ", body=" + body + "]";
	}
	
	
}
