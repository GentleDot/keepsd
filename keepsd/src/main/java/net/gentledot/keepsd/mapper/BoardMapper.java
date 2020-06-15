package net.gentledot.keepsd.mapper;

import net.gentledot.keepsd.models.board.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {
    @Insert("INSERT INTO ksd_board (board_no, title, content, reference, created_at, updated_at)\n" +
            "VALUES (#{boardNo}, #{title}, #{content}, #{reference}, #{createdAt}, #{updatedAt})")
    int save(Board board);

    @Select("SELECT board_no, title, content, reference, created_at, updated_at \n" +
            "FROM ksd_board \n" +
            "ORDER BY created_at DESC LIMIT #{contentNumber} OFFSET #{pageOffset}")
    List<Board> findAll(Integer pageOffset, Integer contentNumber);

    @Select("SELECT board_no, title, content, reference, created_at, updated_at \n" +
            "FROM ksd_board \n" +
            "WHERE board_no = #{boardNo}")
    Board findByBoardNo(Long boardNo);

    @Update("UPDATE ksd_board\n" +
            "SET\n" +
            "    title = #{title},\n" +
            "    content = #{content},\n" +
            "    reference = #{reference}\n" +
            "WHERE \n" +
            "    board_no = #{boardNo}")
    int update(Board board);

    @Delete("DELETE FROM ksd_board WHERE board_no = #{boardNo}")
    int delete(Long boardNo);

}
