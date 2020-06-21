package net.gentledot.keepsd.controller.board;

import net.gentledot.common.util.Pageable;
import net.gentledot.keepsd.models.board.Board;
import net.gentledot.keepsd.models.board.request.BoardRequest;
import net.gentledot.keepsd.models.board.response.ApiResult;
import net.gentledot.keepsd.service.board.BoardService;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.gentledot.keepsd.models.board.response.ApiResult.OK;

@RestController
@RequestMapping(value = "board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("insert")
    public ApiResult<Boolean> insertNewBoardContent(@RequestBody BoardRequest request) {
        return OK(boardService.insertNewBoardContent(request));
    }

    @GetMapping("list")
    public ApiResult<List<Board>> getBoardContentList(Pageable pageable) {
        return OK(boardService.getBoardcontentList(pageable.offset(), pageable.limit()));
    }

    @GetMapping("{boardNo}")
    public ApiResult<Board> getBoardContent(@PathVariable("boardNo") Long boardNo) {
        return OK(boardService.getBoardContent(boardNo));
    }

    @PutMapping("modify/{boardNo}")
    public ApiResult<Boolean> modifyBoardContent(@PathVariable("boardNo") Long boardNo, @RequestBody BoardRequest request) {
        return OK(boardService.modifyBoardContent(boardNo, request));
    }

    @DeleteMapping("delete/{boardNo}")
    public ApiResult<Boolean> deleteBoardContent(@PathVariable("boardNo") Long boardNo) {
        return OK(boardService.deleteBoardContent(boardNo));
    }
}
