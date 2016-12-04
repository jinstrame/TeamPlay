package servlets.controllers;

import core.Entities.Comment;
import core.Entities.Page;
import core.Entities.Post;
import jdbc.DaoProvider;
import jdbc.dao.core.CommentDao;
import jdbc.dao.core.PostDao;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import servlets.filters.AuthFilter;
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
@WebServlet("/post")
public class Commentary extends HttpServlet {
    private PostDao postDao;
    private CommentDao commentDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int page = Integer.parseInt(req.getParameter("page"));

        Post post = postDao.get(id, page);

        req.getSession().setAttribute("post", post);
        req.getSession().setAttribute("comments", commentDao.agregator(post).getNext(15));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsppages/comments.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        Post post = (Post)req.getSession().getAttribute("post");
        Page page = (Page)req.getSession().getAttribute(AuthFilter.AUTH);
        val builder = Comment.builder();

        builder.content(content);
        builder.time(Instant.now());
        builder.commentator_id(page.getId());
        builder.pageId(post.getPageId());
        builder.postId(post.getId());

        commentDao.put(builder.build());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        postDao = provider.getPostDao();
        commentDao = provider.getCommentDao();
    }
}
