package com.ssafy.crit.boards.service;


import com.ssafy.crit.boards.entity.Board;
import com.ssafy.crit.boards.entity.LikeTable;
import com.ssafy.crit.boards.repository.LikeRepository;
import com.ssafy.crit.imsimember.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void like(Member member, Board board) {
        if (likeRepository.findByMemberAndBoard(member, board).isEmpty()) {
            LikeTable like = new LikeTable();
            like.setMember(member);
            like.setBoard(board);
            likeRepository.save(like);
        }
    }

    public void unlike(Member member, Board board) {
        likeRepository.deleteByMemberAndBoard(member, board);
    }
}

