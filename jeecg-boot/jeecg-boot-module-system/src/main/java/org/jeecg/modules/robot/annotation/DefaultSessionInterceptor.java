
package org.jeecg.modules.robot.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class DefaultSessionInterceptor implements HandlerInterceptor {

    /*private SessionService sessionService;
    private UserService userService;*/

    public DefaultSessionInterceptor() {
        /*this.sessionService = sessionService;
        this.userService = userService;*/
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        /*
        Principal principal = sessionService.principal(request.getHeader(HttpHeader.TOKEN), terminal);
        if (handlerMethod.getMethodAnnotation(NeedlessSession.class) == null) {
            if (principal == null) {
                throw new SessionException(Status.SESSION_EXPIRED, "为了您的账户安全，请重新登录！");
            } else if (sessionService.conflict(principal)) {
                throw new SessionException(Status.SESSION_CONFLICT, "为了您的账户安全，请重新登录！");
            }
        }
        if (principal != null) {
            request.setAttribute(RequestAttributes.PRI, principal);
            // 如果可以识别身份,则判断当前师傅账号是否已经注销
            MasterPO masterPO = userService.inquireStoreMasterById(principal.getMaster());
            if (null != masterPO && masterPO.getStatus() == MasterStatus.STATUS_CANCEL) {
                throw new SessionException(Appcode.FORBID_ACCOUNT_CANCEL, "当前账号已注销");
            }
        }
         */
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
