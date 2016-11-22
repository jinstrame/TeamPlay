package servlets.controllers;

import Entities.Page;
import Entities.Post;
import jdbc.DaoProvider;
import jdbc.dao.core.PageDao;
import jdbc.dao.core.PostDao;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import servlets.listeners.Initer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Log4j2
@WebServlet("/page")
public class PageController extends HttpServlet {

    private PageDao pageDao;
    private PostDao postDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String s = (String)req.getParameterMap().keySet().toArray()[0];
        int a = Integer.parseInt(req.getParameter(s));

        @SuppressWarnings("OptionalGetWithoutIsPresent") Page page = pageDao.get(a).get();
        req.getSession().setAttribute("page", page);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("page.jsp");
        requestDispatcher.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        val postBuilder = Post.builder();
        val page = (Page)req.getSession().getAttribute("page");

        postBuilder.firstName(page.getFirstName()).secondName(page.getSecondName()).nickname(page.getNickname());
        postBuilder.pageId(page.getId());
        postBuilder.id(page.getLastPostId() + 1);
        postBuilder.time(Instant.now());
        postBuilder.content(req.getParameter("content"));


    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
        postDao = provider.getPosttDao();
    }
}
