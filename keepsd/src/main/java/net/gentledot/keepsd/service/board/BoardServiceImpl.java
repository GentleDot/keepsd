package net.gentledot.keepsd.service.board;

import net.gentledot.keepsd.mapper.board.BoardMapper;
import net.gentledot.keepsd.models.board.Board;
import net.gentledot.keepsd.models.board.request.BoardRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Transactional
    @Override
    public boolean insertNewBoardContent(BoardRequest request) {
        Board newBoard = new Board.Builder()
                .title(request.getTitle())
                .content(request.getContent())
                .reference(request.getReference())
                .build();

        return boardMapper.save(newBoard) > 0;
    }

    @Override
    public List<Board> getBoardcontentList(Integer page, Integer contentNum) {
        if (page == null || page < 1) {
            page = 0;
        }

        return boardMapper.findAll((page - 1), contentNum);
    }

    @Override
    public Board getBoardContent(Long boardNo) {
        return boardMapper.findByBoardNo(boardNo);
    }

    @Transactional
    @Override
    public boolean modifyBoardContent(Long boardNo, BoardRequest request) {
        Board targetBoard = boardMapper.findByBoardNo(boardNo);

        Board updatedBoard = new Board.Builder(targetBoard)
                .title(request.getTitle())
                .content(request.getContent())
                .reference(request.getReference())
                .build();

        return boardMapper.update(updatedBoard) > 0;
    }

    @Transactional
    @Override
    public boolean deleteBoardContent(Long boardNo) {
        return boardMapper.delete(boardNo) > 0;
    }
}
