package payload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDTO2 {
	
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
	
	private List<String> tags = new ArrayList<>();
	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public PostDTO2() {
		
	}

	public PostDTO2(Long id, String title, String description, String content, Set<CommentsDTO> comments) {
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
