package payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDTO {
	
	private Long id;
	
	//title should not be empty
	//title should have atleast 2 characters
	@NotEmpty(message = "Title should not be empty")
	@Size(min = 2, message = "Post title should have atleast 2 characters")
	private String title;
	
	@NotEmpty(message = "Description should not be empty")
	@Size(min = 10, message = "Post description should have atleast 10 characters")
	private String description;

	@NotEmpty(message = "Content should not be empty")
	private String content;
	
	private Set<CommentsDTO> comments  = new HashSet<CommentsDTO>();
	
	public PostDTO() {
		
	}

	public PostDTO(Long id, String title, String description, String content, Set<CommentsDTO> comments) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<CommentsDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentsDTO> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content + "]";
	}
	
	
}
