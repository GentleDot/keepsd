package net.gentledot.keepsd.models.board.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BoardRequest {
    private String title;
    private String content;
    private String reference;

    public BoardRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getReference() {
        return reference;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("content", content)
                .append("reference", reference)
                .toString();
    }
}
