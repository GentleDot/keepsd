package net.gentledot.keepsd.mapper.board;

import net.gentledot.keepsd.mapper.BoardMapper;
import net.gentledot.keepsd.models.board.Board;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardMapperTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BoardMapper boardMapper;

    @Test
    @Order(1)
    @DisplayName("게시물 저장")
    void saveTest() {
        Board testBoard = getTestBoard();
        log.debug("저장할 board : {}", testBoard);
        int result = boardMapper.save(testBoard);

        assertThat(result, is(1));
    }

    @Test
    @Order(2)
    @DisplayName("게시물 목록 조회")
    void findAllTest() {
        List<Board> boardList = boardMapper.findAll(0, 10);
        // SELECT board_no, title, content, reference, created_at, updated_at FROM ksd_board ORDER BY
        //created_at DESC LIMIT 10 OFFSET 0

        log.debug("boardList : {}", boardList);
        assertThat(boardList, is(notNullValue()));
        assertThat(boardList.get(0).getTitle(), is("testTitle"));
        assertThat(boardList.get(0).getContent(), is("testContent"));
        assertThat(boardList.get(0).getReferenceInfo().get(), is("http://www.test.com"));
        assertThat(boardList.get(0).getCreatedAt(), is(notNullValue()));
        assertThat(boardList.get(0).getUpdatedAt(), is(notNullValue()));
    }

    @Test
    @Order(3)
    @DisplayName("게시물 조회")
    void findByBoardNoTest() {
        Board board = boardMapper.findByBoardNo(1L);

        log.debug("조회된 board : {}", board);

        assertThat(board.getTitle(), is("testTitle"));
        assertThat(board.getContent(), is("testContent"));
        assertThat(board.getReferenceInfo().get(), is("http://www.test.com"));
        assertThat(board.getCreatedAt(), is(notNullValue()));
        assertThat(board.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    @Order(4)
    @DisplayName("게시물 업데이트")
    void updateTest() {
        Board oldBoard = boardMapper.findByBoardNo(1L);
        String oldTitle = oldBoard.getTitle();
        String oldContent = oldBoard.getContent();
        Optional<String> oldReferenceInfo = oldBoard.getReferenceInfo();

        Board updateBoardRequest = new Board.Builder(oldBoard)
                .title("updatedTitle")
                .content("updatedContent")
                .reference("updatedReference")
                .build();

        int result = boardMapper.update(updateBoardRequest);

        Board board = boardMapper.findByBoardNo(1L);

        assertThat(result, is(1));
        assertThat(board.getTitle().equals(oldTitle), is(false));
        assertThat(board.getTitle(), is("updatedTitle"));
        assertThat(board.getContent().equals(oldContent), is(false));
        assertThat(board.getContent(), is("updatedContent"));
        assertThat(board.getReferenceInfo().get().equals(oldReferenceInfo.get()), is(false));
        assertThat(board.getReferenceInfo().get(), is("updatedReference"));
        assertThat(board.getCreatedAt(), is(notNullValue()));
        assertThat(board.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    @Order(5)
    @DisplayName("게시물 삭제")
    void delete() {
        int result = boardMapper.delete(1L);

        assertThat(result, is(1));
    }

    private Board getTestBoard() {
        return new Board.Builder()
                .title("boardMapper-testTitle")
                .content("boardMapper-testContent")
                .build();
    }
}