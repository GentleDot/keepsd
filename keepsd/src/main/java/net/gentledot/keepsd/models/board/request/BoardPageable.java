package net.gentledot.keepsd.models.board.request;

import com.google.common.collect.Range;
import net.gentledot.common.util.Pageable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.google.common.base.Preconditions.checkArgument;

public class BoardPageable implements Pageable {
    private final Long pageOffset;
    private final Integer limitNum;

    protected BoardPageable() {
        this(0L, 10);
    }

    public BoardPageable(Long pageOffset, Integer limitNum) {
        long minimumOffsetValue = 1L;
        long maximumOffsetValue = Long.MAX_VALUE;
        int minimumLimitValue = 1;
        int maximumLimitValue = 20;

        checkArgument(Range.closed(minimumOffsetValue, maximumOffsetValue).contains(pageOffset), "허용되지 않는 값! ({} ~ {} 사이 입력 가능)", minimumOffsetValue, maximumOffsetValue);
        checkArgument(Range.closed(minimumLimitValue, maximumLimitValue).contains(limitNum), "허용되지 않는 값! ({} ~ {} 사이 입력 가능)", minimumLimitValue, maximumLimitValue);

        this.pageOffset = (pageOffset - 1);
        this.limitNum = limitNum;
    }

    @Override
    public Long offset() {
        return this.pageOffset;
    }

    @Override
    public int limit() {
        return this.limitNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("offset", pageOffset)
                .append("limit", limitNum)
                .toString();
    }
}
