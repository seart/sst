package org.example.latestspringsecurity.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

public class SecurityAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        // 不会返回 json格式，直接重定向到目标URL
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        String targetUrl = savedRequest != null ? savedRequest.getRedirectUrl() : getDefaultTargetUrl();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);


        // json 格式的
//        Object principal = authentication.getPrincipal();
//        User user = (User) principal;
//        response.setContentType("application/json;charset=utf-8");
//        PrintWriter out = response.getWriter();
//        Map<String,Object> dataMap = new HashMap<>();
//        Map<String,Object> map = new HashMap<>();
//        dataMap.put("username",user.getUsername());
//        map.put("message", "登录成功");
//        map.put("code", 200);
//        map.put("data",dataMap);
//        ObjectMapper objectMapper = new ObjectMapper();
//        out.write(objectMapper.writeValueAsString(map));
//        out.flush();
//        out.close();
    }
}
