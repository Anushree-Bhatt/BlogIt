package service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import entity.Comments;
import entity.Post;
import exception.BlogApiException;
import exception.ResourceNotFound;
import payload.CommentsDTO;
import repository.CommentRepository;
import repository.PostRepository;

@Service
public class CommentsServiceImpl implements CommentsService{
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	

	@Override
	public CommentsDTO createComment(long post_id, CommentsDTO c) {
		// TODO Auto-generated method stub
		Comments comment = mapToEntity(c);
		
		//get Post
		Post post = postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFound("Post", "id", post_id+""));
		
		//map it to comment 
		comment.setPost(post);
		
		//save the comment
		comment =  commentRepository.save(comment);
		return mapToDTO(comment);
	}

	@Override
	public List<CommentsDTO> getAllComments(long post_id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFound("post", "id", post_id+""));
		
		Set<Comments> s = post.getComments();
		
		List<CommentsDTO> l =  s.stream().map(i -> mapToDTO(i)).collect(Collectors.toList());
		
		return l;
	}

	@Override
	public CommentsDTO getComment(long post_id, long comment_id) {
		// TODO Auto-generated method stub
		//fetch post id
		Post post= postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFound("post", "id", post_id+""));
		//fetch comment id
		Comments comment = commentRepository.findById(comment_id).orElseThrow(() -> new ResourceNotFound("comments", "id", comment_id+""));
		
		//see if comment is for given post only
		if((comment.getPost().getId()!=post.getId()))
			throw new BlogApiException(HttpStatus.BAD_REQUEST.toString(), "Comment doesn't belong to this post!");
		
		return mapToDTO(comment);
	}

	@Override
	public CommentsDTO updateComment(long post_id, long comment_id, CommentsDTO c) {
		// TODO Auto-generated method stub
		Post post= postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFound("post", "id", post_id+""));
		//fetch comment id
		Comments comment = commentRepository.findById(comment_id).orElseThrow(() -> new ResourceNotFound("comments", "id", comment_id+""));
				
		//see if comment is for given post only
		if((comment.getPost().getId()!=post.getId()))
			throw new BlogApiException(HttpStatus.BAD_REQUEST.toString(), "Comment doesn't belong to this post!");
		
		comment.setBody(c.getBody());
		comment.setEmail(c.getEmail());
		comment.setName(c.getName());
		
		comment =  commentRepository.save(comment);

		
		return mapToDTO(comment);
	}

	@Override
	public long deleteComment(long post_id, long comment_id) {
		// TODO Auto-generated method stub
		Post post= postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFound("post", "id", post_id+""));
		//fetch comment id
		Comments comment = commentRepository.findById(comment_id).orElseThrow(() -> new ResourceNotFound("comments", "id", comment_id+""));
				
		//see if comment is for given post only
		if((comment.getPost().getId()!=post.getId()))
			throw new BlogApiException(HttpStatus.BAD_REQUEST.toString(), "Comment doesn't belong to this post!");
		
		commentRepository.delete(comment);
		
		return comment_id;
	}
	
	//create helper methods
		//mapToDTO and mapTOEntity
		
		private CommentsDTO mapToDTO(Comments c) {
//			CommentsDTO cdto = new CommentsDTO();
//			cdto.setBody(c.getBody());
//			cdto.setEmail(c.getEmail());
//			cdto.setId(c.getId());
//			cdto.setName(c.getName());
			CommentsDTO cdto = modelMapper.map(c, CommentsDTO.class);
			return cdto;
		}
		
		private Comments mapToEntity(CommentsDTO cdto) {
//			Comments c = new Comments();
//			c.setBody(cdto.getBody());
//			c.setEmail(cdto.getEmail());
//			c.setId(cdto.getId());
//			c.setName(cdto.getName());
			Comments c = modelMapper.map(cdto, Comments.class);
			return c;
		}

}
