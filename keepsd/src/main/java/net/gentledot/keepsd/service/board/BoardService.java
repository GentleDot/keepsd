package net.gentledot.keepsd.service.board;

import net.gentledot.keepsd.models.board.Board;
import net.gentledot.keepsd.models.board.request.BoardRequest;

import java.util.List;

public interface BoardService {
    boolean insertNewBoardContent(BoardRequest request);

    List<Board> getBoardcontentList(Integer page, Integer contentNum);

    Board getBoardContent(Long boardNo);

    boolean modifyBoardContent(Long boardNo, BoardRequest request);

    boolean deleteBoardContent(Long boardNo);
}
