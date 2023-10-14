package controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import payload.AllConstants;
import payload.PostResponse;
import payload.PostDTO;
import payload.PostDTO2;
import service.impl.PostService;

@RestController
//@RequestMapping("/api/v1/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	ModelMapper modelMapper;
	
	//create blog post rest api
	@PostMapping("/api/v1/posts")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
		
		PostDTO res =  postService.createPost(postDTO);
		if(res==null) {
			return ResponseEntity.noContent().build();
		}
		
		//location , status
		URI location  = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(res.getId()); //Shall be added as header
		return ResponseEntity.created(location).body(res);
	}
	
	//get all blog posts rest api - without pagination support
//	@GetMapping
//	public ResponseEntity<List<PostDTO>> getAllPosts(){
//		return ResponseEntity.status(HttpStatus.FOUND).body(postService.getAllPosts());
//	}
	
//	//get all blog posts rest api - with pagination support
//	@GetMapping
//	public ResponseEntity<BlogPostResponse> getAllPosts(
//			@RequestParam(value = "pageNo",required = false, defaultValue = "0" ) int pageNo,
//			@RequestParam(value = "pageSize",required = false, defaultValue = "5" ) int pageSize){
//		
//		return ResponseEntity.status(HttpStatus.FOUND).body(postService.getAllPosts(pageNo, pageSize));
//	}
	
	//get all blog posts rest api - with pagination and sorting support
	@GetMapping("/api/v1/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo",required = false, defaultValue = "0" ) int pageNo,
			@RequestParam(value = "pageSize",required = false, defaultValue = "5" ) int pageSize,
			@RequestParam(value = "sortBy",required = false, defaultValue = AllConstants.sortBy ) String sortBy,
			@RequestParam(value = "sortDirection",required = false, defaultValue = AllConstants.sortDirection ) String sortDirection){
		
		return ResponseEntity.status(HttpStatus.FOUND).body(postService.getAllPosts(pageNo, pageSize, sortBy, sortDirection));
	}
	
	//get blog post by id rest api
	@GetMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDTO> getPost(@PathVariable long id){
		return ResponseEntity.status(HttpStatus.FOUND).body(postService.getPost(id));
	}
	
	//get blog post by id rest api -> Version 2 -> PostDTO2
	@GetMapping("/api/v2/posts/{id}") //-> Versioning through URI path
	public ResponseEntity<PostDTO2> getPostv2(@PathVariable long id){
		PostDTO p =  postService.getPost(id);
		PostDTO2 res =  modelMapper.map(p, PostDTO2.class);
		List<String> tags = new ArrayList<>();
		tags.add("Spring bott basics");
		tags.add("Java advanced");
		tags.add("MySql");
		res.setTags(tags);
		return new ResponseEntity<PostDTO2>(res,HttpStatus.FOUND);
		
	}
	
	@GetMapping(value = "/api/posts/{id}", params = "version=2") //-> Versioning through query parameter 
	public ResponseEntity<PostDTO2> getPostV2(@PathVariable long id){
		PostDTO p =  postService.getPost(id);
		PostDTO2 res =  modelMapper.map(p, PostDTO2.class);
		List<String> tags = new ArrayList<>();
		tags.add("Spring bott basics");
		tags.add("Java advanced");
		tags.add("MySql");
		res.setTags(tags);
		return new ResponseEntity<PostDTO2>(res,HttpStatus.FOUND);
		
	}
	
	@GetMapping(value = "/api/posts/{id}", headers = "API-VERSION=2") //-> Versioning through Custom headers
	public ResponseEntity<PostDTO2> GetPostV2(@PathVariable long id){
		PostDTO p =  postService.getPost(id);
		PostDTO2 res =  modelMapper.map(p, PostDTO2.class);
		List<String> tags = new ArrayList<>();
		tags.add("Spring bott basics");
		tags.add("Java advanced");
		tags.add("MySql");
		res.setTags(tags);
		return new ResponseEntity<PostDTO2>(res,HttpStatus.FOUND);
		
	}
	
	
	
	//update blog post rest api
	@PutMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDTO> updatePost(@PathVariable long id,@Valid @RequestBody PostDTO p){
		
		return new ResponseEntity<PostDTO>(postService.updatePost(id, p), HttpStatus.ACCEPTED);	}
	
	//delete blog post rest api
	@DeleteMapping("/api/v1/posts")
	public ResponseEntity<String> deletePost(@RequestParam long id){
		
		return new ResponseEntity<String>("Your blog post with id:"+postService.deletePost(id)+" is successfully deleted!!",HttpStatus.OK);
	}
	
	
	
}
