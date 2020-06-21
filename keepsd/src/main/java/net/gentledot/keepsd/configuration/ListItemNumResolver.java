package net.gentledot.keepsd.configuration;

import net.gentledot.common.util.Pageable;
import net.gentledot.keepsd.models.board.request.BoardPageable;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.apache.commons.lang3.ClassUtils.isAssignable;

public class ListItemNumResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return isAssignable(methodParameter.getParameterType(), Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        long pageOffset = NumberUtils.toLong(nativeWebRequest.getParameter("page"), 1L);
        int pageLimitNum = NumberUtils.toInt(nativeWebRequest.getParameter("num"), 10);

        return new BoardPageable(pageOffset, pageLimitNum);
    }
}
