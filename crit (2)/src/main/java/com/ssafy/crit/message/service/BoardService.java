package com.ssafy.crit.message.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ssafy.crit.auth.entity.User;
import com.ssafy.crit.message.dto.BoardDto;
import com.ssafy.crit.message.entity.Board;
import com.ssafy.crit.message.entity.BoardRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    //전체 게시물
    @Transactional(readOnly = true)
    public List<BoardDto> getBoards(){
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        boards.forEach(s -> {
            boardDtos.add(BoardDto.toDto(s));
        });
        return boardDtos;
    }

    //개별 게시물 조회
    @Transactional(readOnly = true)
    public BoardDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다.");
        });
        return BoardDto.toDto(board);
    }

    // 게시물 작성
    // public BoardDto write(BoardDto boardDto, User user, MultipartFile file) throws Exception{
    //
    //
    //     /*우리의 프로젝트경로를 담아주게 된다 - 저장할 경로를 지정*/
    //     String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
    //
    //     /*식별자 . 랜덤으로 이름 만들어줌*/
    //     UUID uuid = UUID.randomUUID();
    //
    //     /*랜덤식별자_원래파일이름 = 저장될 파일이름 지정*/
    //     String fileName = uuid + "_" + file.getOriginalFilename();
    //
    //     /*빈 껍데기 생성*/
    //     /*File을 생성할건데, 이름은 "name" 으로할거고, projectPath 라는 경로에 담긴다는 뜻*/
    //     File saveFile = new File(projectPath, fileName);
    //
    //     file.transferTo(saveFile);
    //
    //     Board board = new Board();
    //     board.setTitle(boardDto.getTitle());
    //     board.setContent(board.getContent());
    //     /*디비에 파일 넣기*/
    //     board.setFilename(fileName);
    //     /*저장되는 경로*/
    //     board.setFilepath("/files/" + fileName); /*저장된파일의이름,저장된파일의경로*/
    //     board.setUser(user);
    //     /*파일 저장*/
    //     boardRepository.save(board);
    //
    //     return BoardDto.toDto(board);
    // }
    public BoardDto write(BoardDto boardDto, User user) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent()); // 수정된 부분
        board.setUser(user);
        /*파일 저장*/
        boardRepository.save(board);
        return BoardDto.toDto(board);
    }




    // 게시물 수정
    public BoardDto update(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());

        return BoardDto.toDto(board);
    }


    // 게시글 삭제
    public void delete(Long id) {
        // 매개변수 id를 기반으로, 게시글이 존재하는지 먼저 찾음
        // 게시글이 없으면 오류 처리
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });

        // 게시글이 있는 경우 삭제처리
        boardRepository.deleteById(id);

    }
}
