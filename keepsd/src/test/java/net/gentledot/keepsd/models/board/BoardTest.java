package net.gentledot.keepsd.models.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("Board 생성")
    public void createBoardTest() {
        Board board = getTestBoard();

        log.info("생성된 객체 : {}", board);

        assertThat(board.getBoardNo(), is(0L));
        assertThat(board.getTitle(), is("testTitle"));
        assertThat(board.getContent(), is("testContent"));
        assertThat(board.getReference(), is("www.test-site.com"));
        assertThat(board.getCreatedAt(), is(notNullValue()));
        assertThat(board.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    @DisplayName("Board 생성 : Exception 발생 - 제목이 없거나 너무 긴 경우")
    public void createBoardTestWithEmptyOrTooLongTitle() {
        Board board = getTestBoard();
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Board.Builder(board)
                            .title("")
                            .build();
                },
                "게시물 제목은 필수항목입니다.");

        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Board.Builder(board)
                            .title("안녕하세요. 이번에 제가 소개할 내용은 바로                                                                                                                                          " +
                                    " 그렇게 대단한 것은 아니지만 대단한 내용입니다. 어떤 내용인지 궁금하시다면 클릭해주세요.")
                            .build();
                },
                "게시물 제목은 200 byte 내외로 입력할 수 있습니다.");

    }

    @Test
    @DisplayName("Board 생성 : Exception 발생 - 내용이 없는 경우")
    public void createBoardTestWithEmptyContent() {
        Board board = getTestBoard();
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Board.Builder(board)
                            .content("")
                            .build();
                },
                "게시물 내용은 필수항목입니다.");
    }

    @Test
    @DisplayName("Board 생성 : Exception 발생 - 출처 정보가 너무 긴 경우")
    public void createBoardTestWithTooLongReference() {
        Board board = getTestBoard();
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Board.Builder(board)
                            .reference("공식 홈페이지 - ㄱ > ㄴ > ㄷ > ㄹ > ㅁ > ㅂ > ㅅ > Advice me cousin an spring of needed. Tell use paid law ever yet new. Meant to learn of vexed if style allow he there. Tiled man stand tears ten joy there terms any widen. Procuring continued suspicion its ten. Pursuit brother are had fifteen distant has. Early had add equal china quiet visit. Appear an manner as no limits either praise in. In in written on charmed justice is amiable farther besides. Law insensible middletons unsatiable for apartments boy delightful unreserved. ")
                            .build();
                },
                "출처 정보는 250 byte 내외로 입력할 수 있습니다.");
    }

    private Board getTestBoard() {
        return new Board.Builder()
                .title("testTitle")
                .content("testContent")
                .reference("www.test-site.com")
                .build();
    }
}