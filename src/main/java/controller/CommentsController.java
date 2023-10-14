package controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import payload.CommentsDTO;
import service.impl.CommentsService;

@RestController
@RequestMapping("/api")
public class CommentsController {
	
	@Autowired
	private CommentsService commentsService;
	
	//create comment for post id
	@PostMapping(value = "/posts/{postId}/comments")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<CommentsDTO> createComment(@PathVariable(value = "postId")long post_id,@Valid @RequestBody CommentsDTO comment){
		CommentsDTO newComment = commentsService.createComment(post_id, comment);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(newComment.getId());
		return ResponseEntity.created(location).body(newComment);
	}
	
	//get all comments of post id
	
	@GetMapping(value = "/posts/{postId}/comments")
	public ResponseEntity<List<CommentsDTO>> getAllComments(@PathVariable(value = "postId") long post_id){
		List<CommentsDTO> l =commentsService.getAllComments(post_id);
		return ResponseEntity.status(HttpStatus.FOUND).body(l);
	}
	
	//get comment for post id
	@GetMapping(value = "/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentsDTO> getComment(@PathVariable(value = "postId") long post_id, @PathVariable(value = "id")long comment_id){
		return new ResponseEntity<CommentsDTO>(commentsService.getComment(post_id, comment_id),HttpStatus.FOUND);
	}
	
	//update comment by id
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentsDTO> updateComment(@PathVariable(value = "postId") long post_id, @PathVariable(value = "id")long comment_id,@Valid @RequestBody CommentsDTO comment){
		return new ResponseEntity<CommentsDTO>(commentsService.updateComment(post_id, comment_id,comment),HttpStatus.OK);
	}
	
	//delete comment by id
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long post_id, @PathVariable(value = "id")long comment_id){
		return new ResponseEntity<String>("Comment with id:"+commentsService.deleteComment(post_id, comment_id)+" has been deleted successfully!!",HttpStatus.OK);
	}
	
}
