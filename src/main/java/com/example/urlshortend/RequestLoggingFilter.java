package com.example.urlshortend;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final PrintWriter logger;

    public RequestLoggingFilter() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("logs.txt", true);
        this.logger = new PrintWriter(fos);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.println("METHOD: " + request.getMethod());
        logger.println("PATH: " + request.getRequestURI());
        logger.println("TIME: " + LocalDateTime.now());
        logger.println("---------------------------");
        logger.flush();

        filterChain.doFilter(request, response);
    }
}
