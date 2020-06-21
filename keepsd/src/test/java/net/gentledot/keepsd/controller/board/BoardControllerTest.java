package net.gentledot.keepsd.controller.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gentledot.keepsd.models.board.Board;
import net.gentledot.keepsd.models.board.request.BoardRequest;
import net.gentledot.keepsd.service.board.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BoardController.class})
class BoardControllerTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @Test
    @DisplayName("게시물 저장 요청")
    void insertNewBoardContentTest() throws Exception {
        // given
        BoardRequest boardRequest = new BoardRequest();
        boardRequest.setTitle("testTitle");
        boardRequest.setContent("testContent");

        ObjectMapper objectMapper = new ObjectMapper();
        String insertRequest = objectMapper.writeValueAsString(boardRequest);

        // when
        when(boardService.insertNewBoardContent(any(BoardRequest.class))).thenReturn(true);

        ResultActions actions = mockMvc.perform(post("/board/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(insertRequest));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.processResult").value(true))
                .andExpect(jsonPath("$.response").value(true))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName("게시물 목록 조회 요청")
    void getBoardContentListTest() throws Exception {
        // given
        int page = 1;
        int contentNum = 10;

        // when
        when(boardService.getBoardcontentList(page, contentNum)).thenReturn(Arrays.asList(getTestBoard()));

//        ResultActions actions = mockMvc.perform(get("/board/list")
//                .param("page", String.valueOf(page))
//                .param("num", String.valueOf(contentNum)));
//        ResultActions actions = mockMvc.perform(get("/board/list?page=" + page + "&contentNum=" + contentNum));
        ResultActions actions = mockMvc.perform(get("/board/list"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.processResult").value(true))
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName("게시물 조회 요청")
    void getBoardContentTest() throws Exception {
        // given
        long boardNo = 1L;

        // when
        when(boardService.getBoardContent(boardNo)).thenReturn(getTestBoard());

        ResultActions actions = mockMvc.perform(get("/board/1"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.processResult").value(true))
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName("게시물 수정 요청")
    void modifyBoardContent() throws Exception {
        // given
        long boardNo = 1L;
        BoardRequest boardRequest = new BoardRequest();
        boardRequest.setTitle("modifiedTitle");
        boardRequest.setContent("modifiedContent");

        ObjectMapper objectMapper = new ObjectMapper();
        String modifyRequest = objectMapper.writeValueAsString(boardRequest);

        // when
        when(boardService.modifyBoardContent(eq(boardNo), any(BoardRequest.class))).thenReturn(true);

        ResultActions actions = mockMvc.perform(put("/board/modify/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(modifyRequest));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.processResult").value(true))
                .andExpect(jsonPath("$.response").value(true))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    void deleteBoardContent() throws Exception {
        // given
        long boardNo = 1L;

        // when
        when(boardService.deleteBoardContent(boardNo)).thenReturn(true);

        ResultActions actions = mockMvc.perform(delete("/board/delete/1"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.processResult").value(true))
                .andExpect(jsonPath("$.response").value(true))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    private Board getTestBoard() {
        return new Board.Builder()
                .title("testTitle")
                .content("testContent")
                .reference("www.test-site.com")
                .build();
    }
}