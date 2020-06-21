package net.gentledot.common.util;

public interface Pageable {
    Long offset();  // offset (시작 row)

    int limit();  // limit (row 수)
}
