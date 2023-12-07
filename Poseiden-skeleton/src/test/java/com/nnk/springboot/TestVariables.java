package com.nnk.springboot;

import com.nnk.springboot.config.CustomUserDetails;
import com.nnk.springboot.domain.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

public abstract class TestVariables {
    protected Model model;
    protected Authentication authentication;
    protected HttpServletRequest request;

    protected ModelAndView mav;

    protected String longString31; // string of 31 characters used for tests
    protected String longString11; // string of 11 characters used for tests
    protected String longString126; // string of 126 characters used for tests
    protected String longString513; // string of 513 characters used for tests

    protected BidList bidList;
    protected List<BidList> bidListList;
    protected Optional<BidList> bidListOptional;

    protected String bidListAccountSize;
    protected String bidListAccountNotBlank;
    protected String bidListTypeSize;
    protected String bidListTypeNotBlank;
    protected String bidListBidQuantityPositiveOrZero;
    protected String bidListAskQuantityPositiveOrZero;
    protected String bidListBidPositiveOrZero;
    protected String bidListAskPositiveOrZero;
    protected String bidListBenchmarkSize;
    protected String bidListCommentarySize;
    protected String bidListSecuritySize;
    protected String bidListStatusSize;
    protected String bidListTraderSize;
    protected String bidListBookSize;
    protected String bidListCreationNameSize;
    protected String bidListRevisionNameSize;
    protected String bidListDealNameSize;
    protected String bidListDealTypeSize;
    protected String bidListSourceListIdSize;
    protected String bidListSideSize;
    protected Integer bidListId; // id of the bidList created for integration tests

    protected CurvePoint curvePoint;
    protected List<CurvePoint> curvePointList;
    protected Optional<CurvePoint> curvePointOptional;

    protected String curvePointCurveIdPositiveOrZero;
    protected Integer curvePointId; // id of the curvePoint created for integration tests
    
    protected Rating rating;
    protected List<Rating> ratingList;
    protected Optional<Rating> ratingOptional;

    protected String ratingFitchRatingSize;
    protected String ratingMoodysRatingSize;
    protected String ratingOrderMin;
    protected String ratingOrderMax;
    protected String ratingSandPRatingSize;
    protected Integer ratingId; // id of the rating created for integration tests
    
    protected RuleName ruleName;
    protected List<RuleName> ruleNameList;
    protected Optional<RuleName> ruleNameOptional;

    protected String ruleNameNameSize;
    protected String ruleNameDescriptionSize;
    protected String ruleNameJsonSize;
    protected String ruleNameTemplateSize;
    protected String ruleNameSqlStrSize;
    protected String ruleNameSqlPartSize;
    protected Integer ruleNameId; // id of the ruleName created for integration tests

    protected Trade trade;
    protected List<Trade> tradeList;
    protected Optional<Trade> tradeOptional;

    protected String tradeAccountSize;
    protected String tradeAccountNotBlank;
    protected String tradeTypeSize;
    protected String tradeTypeNotBlank;
    protected String tradeBuyQuantityPositiveOrZero;
    protected String tradeSellQuantityPositiveOrZero;
    protected String tradeBuyPricePositiveOrZero;
    protected String tradeSellPricePositiveOrZero;
    protected String tradeSecuritySize;
    protected String tradeStatusSize;
    protected String tradeTraderSize;
    protected String tradeBenchmarkSize;
    protected String tradeBookSize;
    protected String tradeCreationNameSize;
    protected String tradeRevisionNameSize;
    protected String tradeDealNameSize;
    protected String tradeDealTypeSize;
    protected String tradeSourceListIdSize;
    protected String tradeSideSize;
    protected Integer tradeId; // id of the trade created for integration tests


    protected User user;
    protected List<User> userList;
    protected Optional<User> userOptional;
    protected String passwordIncorrect;
    protected String userUsernameSize;
    protected String userPasswordSize;
    protected String userFullNameSize;
    protected String userRoleSize;
    protected String userUsernameNotBlank;
    protected String userPasswordNotBlank;
    protected Integer userId; // id of the user created for integration tests

    public void initializeVariables() {
        model = new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };

        authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new CustomUserDetails(user).getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return user.getUsername();
            }
        };

        request = new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String name) {
                return 0;
            }

            @Override
            public String getHeader(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String name) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return user.getUsername();
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean create) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return new HttpSession() {
                    @Override
                    public long getCreationTime() {
                        return 0;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public long getLastAccessedTime() {
                        return 0;
                    }

                    @Override
                    public ServletContext getServletContext() {
                        return null;
                    }

                    @Override
                    public void setMaxInactiveInterval(int interval) {

                    }

                    @Override
                    public int getMaxInactiveInterval() {
                        return 0;
                    }

                    @Override
                    public Object getAttribute(String name) {
                        return null;
                    }

                    @Override
                    public Enumeration<String> getAttributeNames() {
                        return null;
                    }

                    @Override
                    public void setAttribute(String name, Object value) {

                    }

                    @Override
                    public void removeAttribute(String name) {

                    }

                    @Override
                    public void invalidate() {

                    }

                    @Override
                    public boolean isNew() {
                        return false;
                    }
                };
            }

            @Override
            public String changeSessionId() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String username, String password) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String name) throws IOException, ServletException {
                return null;
            }

            @Override
            public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public long getContentLengthLong() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String name) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String name, Object o) {

            }

            @Override
            public void removeAttribute(String name) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String path) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }

            @Override
            public String getRequestId() {
                return null;
            }

            @Override
            public String getProtocolRequestId() {
                return null;
            }

            @Override
            public ServletConnection getServletConnection() {
                return null;
            }
        };

        mav = new ModelAndView();

        // 11 characters string used for size validation
        longString11 = "85368220096";

        // 31 characters string used for size validation
        longString31 = "6357544527718847525994334578369";

        // 126 characters string used for size validation
        longString126 = "85368220096847824275049423209683" +
                "35786401076532718070358521773728" +
                "25842050212176069074456918678132" +
                "635754452771884752599433457836";

        // 513 characters string used for size validation
        longString513 = "57967611163009735911423152900562" +
                "97323731849038779577575329043705" +
                "15892693415038843924665989616769" +
                "75199818829449044168899552114360" +
                "57967611163009735911423152900562" +
                "97323731849038779577575329043705" +
                "15892693415038843924665989616769" +
                "75199818829449044168899552114360" +
                "57967611163009735911423152900562" +
                "97323731849038779577575329043705" +
                "15892693415038843924665989616769" +
                "75199818829449044168899552114360" +
                "57967611163009735911423152900562" +
                "97323731849038779577575329043705" +
                "15892693415038843924665989616769" +
                "75199818829449044168899552114360" +
                "5";

        bidList = new BidList("accountTestValue", "typeTestValue", 10.0);
        bidListList = new ArrayList<>(List.of(bidList));
        bidListOptional = Optional.of(bidList);

        bidListAccountSize = "account should be less than 31 characters";
        bidListAccountNotBlank = "account is mandatory";
        bidListTypeSize = "type should be less than 31 characters";
        bidListTypeNotBlank = "type is mandatory";
        bidListBidQuantityPositiveOrZero = "bidQuantity should be a positive number or zero";
        bidListAskQuantityPositiveOrZero = "askQuantity should be a positive number or zero";
        bidListBidPositiveOrZero = "bid should be a positive number or zero";
        bidListAskPositiveOrZero = "ask should be a positive number or zero";
        bidListBenchmarkSize = "benchmark should be less than 126 characters";
        bidListCommentarySize = "commentary should be less than 126 characters";
        bidListSecuritySize = "security should be less than 126 characters";
        bidListStatusSize = "status should be less than 11 characters";
        bidListTraderSize = "trader should be less than 126 characters";
        bidListBookSize = "book should be less than 126 characters";
        bidListCreationNameSize = "creationName should be less than 126 characters";
        bidListRevisionNameSize = "revisionName should be less than 126 characters";
        bidListDealNameSize = "dealName should be less than 126 characters";
        bidListDealTypeSize = "dealType should be less than 126 characters";
        bidListSourceListIdSize = "sourceListId should be less than 126 characters";
        bidListSideSize = "side should be less than 126 characters";
        
        curvePoint = new CurvePoint(10, 20.0, 30.0);
        curvePointList = new ArrayList<>(List.of(curvePoint));
        curvePointOptional = Optional.of(curvePoint);

        curvePointCurveIdPositiveOrZero = "curveId should be a positive number or zero";

        rating = new Rating("moodysRatingTestValue", "sandPRatingTestValue", "UsernameTestValue", 10);
        ratingList = new ArrayList<>(List.of(rating));
        ratingOptional = Optional.of(rating);

        ratingFitchRatingSize = "fitchRating should be less than 126 characters";
        ratingMoodysRatingSize = "moodysRating should be less than 126 characters";
        ratingOrderMin = "order should be more than -129";
        ratingOrderMax = "order should be less than 128";
        ratingSandPRatingSize = "sandPRating should be less than 126 characters";

        ruleName = new RuleName("nameTestValue", "descriptionTestValue", "jsonTestValue", "templateTestValue", "sqlStrTestValue","sqlPartTestValue");
        ruleNameList = new ArrayList<>(List.of(ruleName));
        ruleNameOptional = Optional.of(ruleName);
        
        ruleNameNameSize = "name should be less than 126 characters";
        ruleNameDescriptionSize = "description should be less than 126 characters";
        ruleNameJsonSize = "json should be less than 126 characters";
        ruleNameTemplateSize = "template should be less than 513 characters";
        ruleNameSqlStrSize = "sqlStr should be less than 126 characters";
        ruleNameSqlPartSize = "sqlPart should be less than 126 characters";

        trade = new Trade("accountTestValue", "typeTestValue", 10.0);
        tradeList = new ArrayList<>(List.of(trade));
        tradeOptional = Optional.of(trade);

        tradeAccountSize = "account should be less than 31 characters";
        tradeAccountNotBlank = "account is mandatory";
        tradeTypeSize = "type should be less than 31 characters";
        tradeTypeNotBlank = "type is mandatory";
        tradeBuyQuantityPositiveOrZero = "buyQuantity should be a positive number or zero";
        tradeSellQuantityPositiveOrZero = "sellQuantity should be a positive number or zero";
        tradeBuyPricePositiveOrZero = "buyPrice should be a positive number or zero";
        tradeSellPricePositiveOrZero = "sellPrice should be a positive number or zero";
        tradeSecuritySize = "security should be less than 126 characters";
        tradeStatusSize = "status should be less than 11 characters";
        tradeTraderSize = "trader should be less than 126 characters";
        tradeBenchmarkSize = "benchmark should be less than 126 characters";
        tradeBookSize = "book should be less than 126 characters";
        tradeCreationNameSize = "creationName should be less than 126 characters";
        tradeRevisionNameSize = "revisionName should be less than 126 characters";
        tradeDealNameSize = "dealName should be less than 126 characters";
        tradeDealTypeSize = "dealType should be less than 126 characters";
        tradeSourceListIdSize = "sourceListId should be less than 126 characters";
        tradeSideSize = "side should be less than 126 characters";
        
        user = new User();
        user.setUsername("usernameTestValue");
        user.setPassword("passwordTestValue1!");
        user.setFullname("fullNameTestValue");
        user.setRole("USER");
        userList = new ArrayList<>(List.of(user));
        userOptional = Optional.of(user);

        passwordIncorrect = "psswrd";

        userUsernameSize = "Username should be less than 126 characters";
        userPasswordSize = "Password should be less than 126 characters";
        userFullNameSize = "FullName should be less than 126 characters";
        userRoleSize = "Role should be less than 126 characters";
        userUsernameNotBlank = "Username is mandatory";
        userPasswordNotBlank = "Password is mandatory";
    }
}
