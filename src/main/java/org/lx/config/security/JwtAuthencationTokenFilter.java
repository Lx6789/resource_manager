package org.lx.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.tokenHeader:Authorization}")
    private String tokenHeader;

    @Value("${jwt.tokenHead:Bearer}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. 从请求头获取 Token
        String header = request.getHeader(tokenHeader);

        // 2. Token 存在且以 Bearer 开头
        if (header != null && header.startsWith(tokenHead)) {
            String token = header.substring(tokenHead.length()).trim();

            // 3. 从 Token 中取出用户名
            String username = jwtTokenUtils.getUsernameFromToken(token);

            // 4. 用户名不为空 且 SecurityContext 里还没有认证信息
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 5. 用 UserDetailsService 加载用户信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 6. 校验 Token 有效性
                if (jwtTokenUtils.validateToken(token)) {
                    // 7. 组装认证对象，存入 SecurityContext
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // 8. 放行请求
        filterChain.doFilter(request, response);
    }
}