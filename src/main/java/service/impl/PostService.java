package service.impl;


import payload.PostResponse;
import payload.PostDTO;

public interface PostService {
	PostDTO createPost(PostDTO p);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection);
	
	PostDTO getPost(long id);
	
	PostDTO updatePost(long id, PostDTO p);
	
	long deletePost(long id);
}
