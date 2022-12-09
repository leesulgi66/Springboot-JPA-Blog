package com.cos.blog.service;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository; //final 선언시 반드시 초기화가 필요한데 @RequiredArgsConstructor이 대신 해준다.
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public void 글쓰기(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id) {
        return boardRepository.findById(id)
                .orElseThrow(()->{
                        return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾 을 수 없습니다.");
                });
    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
                }); //영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        //해당 함수 종료시에(Service가 종료될 때) 트랜젝션이 종료. - 더티채킹 - 자동 업데이트가 됨. db flush
    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {

        User user = userRepository.findById(replySaveRequestDto.getUserId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("사용자 찾기 실패 : 유저 아이디를 찾을 수 없습니다.");
                }); //영속화 완료;

        Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 아이디를 찾을 수 없습니다.");
                }); //영속화 완료;

//        Reply reply = Reply.builder()  //빌더를 사용해서 새로운 객체로 만들어도 됨.
//                .user(user)
//                .board(board)
//                .content(replySaveRequestDto.getContent())
//                .build();

        Reply reply = new Reply(); //model에서 update 함수를 만든 후 사용도 가능.
        reply.update(user, board, replySaveRequestDto.getContent());

        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
