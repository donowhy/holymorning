package com.ssafy.crit.boards.controller;

import com.ssafy.crit.boards.entity.Board;
import com.ssafy.crit.boards.repository.BoardRepository;
import com.ssafy.crit.boards.service.LikeService;
import com.ssafy.crit.imsimember.entity.Member;
import com.ssafy.crit.imsimember.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Value("${app.auth.token-secret}")
    private String secret;


    @PostMapping("/{boardId}")
    public ResponseEntity<String> like(HttpServletRequest httpServletRequest, @PathVariable Long boardId) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = authorizationHeader.substring(7);

        String memberId = getUsernameFromToken(token);

        Member member = memberRepository.findByName(memberId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow();
        likeService.like(member, board);
        return new ResponseEntity<>("Board liked", HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> unlike(HttpServletRequest httpServletRequest, @PathVariable Long boardId) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = authorizationHeader.substring(7);

        String memberId = getUsernameFromToken(token);

        Member member = memberRepository.findByName(memberId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow();
        likeService.unlike(member, board);
        return new ResponseEntity<>("Board unliked", HttpStatus.OK);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", String.class);
    }
}
