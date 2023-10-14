package service.impl;

import java.util.List;

import payload.CommentsDTO;

public interface CommentsService {

	public CommentsDTO createComment(long post_id, CommentsDTO comment);
	
	public List<CommentsDTO> getAllComments(long post_id);
	
	public CommentsDTO getComment(long post_id, long comment_id);

	public CommentsDTO updateComment(long post_id, long comment_id, CommentsDTO comment);

	public long deleteComment(long post_id, long comment_id);
}
