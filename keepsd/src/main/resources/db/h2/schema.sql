DROP TABLE IF EXISTS ksd_board;
DROP TABLE IF EXISTS tbl_tmp;

CREATE TABLE ksd_board(
    board_no   bigint PRIMARY KEY AUTO_INCREMENT COMMENT '게시물 번호',
    title      VARCHAR(200)                        NOT NULL COMMENT '게시물 제목',
    content    TEXT                                NOT NULL COMMENT '게시물 내용',
    reference  VARCHAR(250)                        NULL COMMENT '출처 정보',
    created_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    updated_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
);

CREATE TABLE tbl_tmp(
    tmpno bigint primary key  AUTO_INCREMENT,
    tmpstr varchar(100)
)
