package com.ssafy.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.Member.model.MemberDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberDto member = (MemberDto) session.getAttribute("memberInfo");
        System.out.println(member);
        
        String check = null;
        
        if(member != null)
        	check=member.getRole();
        	
    	// id가 admin으로 로그인된 사용자인지 확인        
        if(!check.equals("ADMIN"))  {
            response.sendRedirect(request.getContextPath()+"/");
            return false;
        }
        return true;
    }
}
