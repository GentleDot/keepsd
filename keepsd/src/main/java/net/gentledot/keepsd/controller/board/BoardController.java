package net.gentledot.keepsd.controller.board;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.gentledot.common.util.Pageable;
import net.gentledot.keepsd.models.board.Board;
import net.gentledot.keepsd.models.board.request.BoardRequest;
import net.gentledot.keepsd.models.board.response.ApiResult;
import net.gentledot.keepsd.service.board.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.gentledot.keepsd.models.board.response.ApiResult.OK;

@Tag(name = "정보 게시판", description = "사회적 거리두기와 관련된 정보를 입력, 조회, 수정, 삭제 가능한 공간")
@RestController
@RequestMapping(value = "board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(summary = "게시물 등록", description = "요청한 내용 (제목, 내용, 참조)을 게시물로 등록합니다.")
    @PostMapping("insert")
    public ApiResult<Boolean> insertNewBoardContent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "제목(필수), 내용(필수), 참조정보를 요청으로 전송") @RequestBody BoardRequest request) {
        return OK(boardService.insertNewBoardContent(request));
    }

    @Operation(summary = "게시물 목록 조회", description = "등록된 게시물의 목록을 조회합니다.")
    @Parameters({
            @Parameter(name = "page", description = "게시물 목록의 시작 게시물 offset", in = ParameterIn.QUERY, example = "1"),
            @Parameter(name = "num", description = "게시물 목록의 게시물 갯수 (limit)", in = ParameterIn.QUERY, example = "10")
    })
    @GetMapping("list")
    public ApiResult<List<Board>> getBoardContentList(
            @Parameter(hidden = true, name = "pageable 관련 설정",
                    description = "page와 num을 입력하여 offset과 limit을 설정할 수 있습니다.",
                    example = "{\"page\" : 1, \"num\" : 10}") Pageable pageable) {
        return OK(boardService.getBoardcontentList(pageable.offset(), pageable.limit()));
    }


    @Operation(summary = "게시물 조회", description = "등록된 게시물을 조회합니다.")
    @GetMapping("{boardNo}")
    public ApiResult<Board> getBoardContent(
            @Parameter(description = "게시물 번호") @PathVariable("boardNo") Long boardNo) {
        return OK(boardService.getBoardContent(boardNo));
    }

    @Operation(summary = "게시물 수정", description = "등록된 게시물을 수정합니다.")
    @PutMapping("modify/{boardNo}")
    public ApiResult<Boolean> modifyBoardContent(
            @Parameter(description = "게시물 번호") @PathVariable("boardNo") Long boardNo,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "제목(필수), 내용(필수), 참조정보를 요청으로 전송") @RequestBody BoardRequest request) {
        return OK(boardService.modifyBoardContent(boardNo, request));
    }

    @Operation(summary = "게시물 삭제", description = "등록된 게시물을 삭제합니다.")
    @DeleteMapping("delete/{boardNo}")
    public ApiResult<Boolean> deleteBoardContent(
            @Parameter(description = "게시물 번호") @PathVariable("boardNo") Long boardNo) {
        return OK(boardService.deleteBoardContent(boardNo));
    }
}
