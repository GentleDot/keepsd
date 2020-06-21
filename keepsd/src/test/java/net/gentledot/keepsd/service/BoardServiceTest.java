package net.gentledot.keepsd.service;

import net.gentledot.keepsd.mapper.board.BoardMapper;
import net.gentledot.keepsd.models.board.Board;
import net.gentledot.keepsd.models.board.request.BoardRequest;
import net.gentledot.keepsd.service.board.BoardServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class BoardServiceTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    BoardServiceImpl boardService;

    @Mock
    BoardMapper boardMapper;

    @Test
    @DisplayName("새로운 게시물 등록")
    void insertNewBoardContent() {
        // given
        BoardRequest request = new BoardRequest();
        request.setContent("testContent");
        request.setTitle("testTitle");
        log.debug("request 확인 : {}", request);

        // when
        when(boardMapper.save(any(Board.class))).thenReturn(1);
        boolean result = boardService.insertNewBoardContent(request);

        // then
        assertThat(result, is(true));
    }

    @Test
    @DisplayName("게시물 목록 확인")
    void getBoardcontentList() {
        // given
        long page = 1;
        int num = 10;

        // * 실제 반환되는 list는 List<Board>
        // when
        when(boardMapper.findAll(anyLong(), anyInt())).thenReturn(new ArrayList<>());
        List<Board> boardcontentList = boardService.getBoardcontentList(page, num);

        // then
        log.debug("list 확인 : {}", boardcontentList);
        assertThat(boardcontentList, is(notNullValue()));
    }

    @Test
    @DisplayName("게시물 확인")
    void getBoardContent() {
        // given
        Long boardNo = 1L;

        // when
        when(boardMapper.findByBoardNo(boardNo)).thenReturn(getTestBoard());
        Board board = boardService.getBoardContent(boardNo);

        // then
        assertThat(board, is(notNullValue()));
        assertThat(board.getTitle(), is("testTitle"));
        assertThat(board.getContent(), is("testContent"));
        assertThat(board.getReferenceInfo().get(), is("www.test-site.com"));
        assertThat(board.getCreatedAt(), is(notNullValue()));
        assertThat(board.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    @DisplayName("게시물 수정")
    void modifyBoardContent() {
        // given
        Long boardNo = 1L;
        String title = "anotherTitle";
        String content = "modified Content";
        String reference = "TestBook";

        BoardRequest request = new BoardRequest();
        request.setTitle(title);
        request.setContent(content);
        request.setReference(reference);

        given(boardMapper.findByBoardNo(1L)).willReturn(getTestBoard());

        // when
        when(boardMapper.update(any(Board.class))).thenReturn(1);
        boolean result = boardService.modifyBoardContent(boardNo, request);

        // then
        assertThat(result, is(true));
    }

    @Test
    @DisplayName("게시물 삭제")
    void deleteBoardContent() {
        // given
        Long boardNo = 1L;

        // when
        when(boardMapper.delete(boardNo)).thenReturn(1);
        boolean result = boardService.deleteBoardContent(boardNo);

        // then
        assertThat(result, is(true));
    }

    private Board getTestBoard() {
        return new Board.Builder()
                .boardNo(1L)
                .title("testTitle")
                .content("testContent")
                .reference("www.test-site.com")
                .build();
    }
}