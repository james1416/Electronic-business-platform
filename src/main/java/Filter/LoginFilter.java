package Filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemberVO;



public class LoginFilter implements Filter {


	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//切記不可用轉型過後的req放行，否則進入tomcat之後再次產生一個httpservletreq不知會發生什麼事情
		//也不要寫printwriter
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		if(memberVO==null) {
			System.out.println("我是filter警察，已攔截住不明請求");
			res.sendRedirect("/CFA101G4/front-end/login.jsp");
		}else if(memberVO!=null){
			if((memberVO.getMem_sta())!=0) {
				System.out.println("非停權狀態會員，已放行");
				chain.doFilter(request, response);
			}else {
				System.out.println("停權狀態會員，已阻擋");
				session.setAttribute("suspended", "您已被停權");
				res.sendRedirect("/CFA101G4/front-end/FrontMain.jsp");
			}
		}
	}
	
	public void init(FilterConfig Config) throws ServletException {
		
	}

}
