package service.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import entity.Post;
import exception.ResourceNotFound;
import payload.PostResponse;
import payload.PostDTO;
import repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	private PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}


	//create Post
	@Override
	public PostDTO createPost(PostDTO p) {
		// TODO Auto-generated method stub
		
		//Convert DTO to entity 
		Post post = mapToPostEntity(p); //DTO -> Entity 
		
		//insert it into table
		Post result =  postRepository.save(post);
		
		//convert result entity to resultant of DTO
		PostDTO res = mapToPostDTO(result);
		
		
		return res;
	}


	//Get All Posts
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection) {
		// TODO Auto-generated method stub
		
		
		//List<Post> l =  postRepository.findAll();  -> normal findAll()
		
		//Create a sort object
		Sort sort = (sortDirection.equalsIgnoreCase(Sort.Direction.ASC.toString()))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		Page<Post> page_of_posts =   postRepository.findAll(pageable);
		
		//retrieve only contents from post pages and convert them into List<PostDTO>
	//	List<Post> l = page_of_posts.getContent(); //fetches all posts present in above said page 
		
		List<PostDTO> l = page_of_posts.getContent().stream().map(i -> mapToPostDTO(i)).collect(Collectors.toList());
		
		//create blogPostRespnse object and return the same
		PostResponse res = new PostResponse();
		res.setContent(l);
		res.setPageNo(page_of_posts.getNumber());
		res.setPageSize(page_of_posts.getNumberOfElements());
		res.setTotalElements(page_of_posts.getTotalElements());
		res.setTotalPages(page_of_posts.getTotalPages());
		res.setLast(page_of_posts.isLast());
		
		return res;
	}
	
	//Get Post
	@Override
	public PostDTO getPost(long id) {
		// TODO Auto-generated method stub
		Optional<Post> o =  postRepository.findById(id);
		if(o.isPresent() && o.get().getId()>0) {
			//fetch res
			Post p = o.get();
			
			//con it to DTO
			PostDTO res = mapToPostDTO(p);
			
			return res;
		}else {
			throw new ResourceNotFound("Post", "id", id+"");
		}
	}
	
	//update  post
	@Override
	public PostDTO updatePost(long id, PostDTO p) {
		Post post = postRepository.findById(id).orElseThrow(() -> {throw new ResourceNotFound("POST", "id", id+"");});
		//now update the resource
		post.setContent(p.getContent());
		post.setDescription(p.getDescription());
		post.setTitle(p.getTitle());
		
		postRepository.save(post);
		
		return mapToPostDTO(post);
	}


	@Override
	public long deletePost(long id) {
		// TODO Auto-generated method stub
		Post post  = postRepository.findById(id).orElseThrow(() -> {return new ResourceNotFound("POST", "id", id+"");});
		
		//delete it in db
		postRepository.delete(post);
		
		return post.getId();
	}
	
	//convert Post to PostDTO
		private PostDTO mapToPostDTO(Post p){
//			PostDTO res = new PostDTO();
//			res.setContent(p.getContent());
//			res.setDescription(p.getDescription());
//			res.setTitle(p.getTitle());
//			res.setId(p.getId());
			PostDTO res = modelMapper.map(p, PostDTO.class);
			return res;
		}
		
		//Convert PostDTO to Post
		private Post mapToPostEntity(PostDTO p) {
//			Post post = new Post();
//			post.setContent(p.getContent());
//			post.setDescription(p.getDescription());
//			post.setTitle(p.getTitle());
			Post post = modelMapper.map(p, Post.class);
			return post;
		}
		
}
