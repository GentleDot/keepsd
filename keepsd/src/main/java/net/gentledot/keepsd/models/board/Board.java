package net.gentledot.keepsd.models.board;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public class Board {
    private Long boardNo;
    private String title;
    private String content;
    private String reference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Board(Long boardNo, String title, String content, String reference, LocalDateTime createdAt, LocalDateTime updatedAt) {
        checkArgument(StringUtils.isNotBlank(title), "게시물 제목은 필수항목입니다.");
        checkArgument(title.getBytes(StandardCharsets.UTF_8).length <= 200, "게시물 제목은 200 byte 내외로 입력할 수 있습니다.");
        checkArgument(StringUtils.isNotBlank(content), "게시물 내용은 필수항목입니다.");
        if (reference != null) {
            checkArgument(reference.getBytes(StandardCharsets.UTF_8).length <= 250, "출처 정보는 250 byte 내외로 입력할 수 있습니다.");
        }

        this.boardNo = boardNo == null ? 0L : boardNo;
        this.title = title;
        this.content = content;
        this.reference = reference;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
        this.updatedAt = updatedAt == null ? LocalDateTime.now() : updatedAt;
    }

    public Long getBoardNo() {
        return boardNo;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private String getReference() {
        return reference;
    }

    public Optional<String> getReferenceInfo() {
        return Optional.ofNullable(reference);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("boardNo", boardNo)
                .append("title", title)
                .append("content", content)
                .append("reference", reference)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equal(boardNo, board.boardNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(boardNo);
    }

    public static final class Builder {
        private Long boardNo;
        private String title;
        private String content;
        private String reference;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder() {
        }

        public Builder(Board board) {
            this.boardNo = board.getBoardNo();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.reference = board.getReference();
            this.createdAt = board.getCreatedAt();
            this.updatedAt = board.getUpdatedAt();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Board build() {
            return new Board(boardNo, title, content, reference, createdAt, updatedAt);
        }
    }
}
