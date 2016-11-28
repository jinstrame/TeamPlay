package servlets.controllers;

import Entities.Page;
import Entities.PageTypes;
import Entities.Post;
import jdbc.DaoProvider;
import jdbc.dao.core.PageDao;
import jdbc.dao.core.PostDao;
import jdbc.dao.core.agregation.Agregator;
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
import java.util.LinkedList;
import java.util.List;

@Log4j2
@WebServlet("/page")
public class PageController extends HttpServlet {

    private PageDao pageDao;
    private PostDao postDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page;
        String s = req.getParameter("id");
        if (s == null){
            page = (Page)req.getSession().getAttribute(AuthFilter.AUTH);
        } else{
            int a = Integer.parseInt(req.getParameter(s));
            //noinspection OptionalGetWithoutIsPresent
            page = pageDao.get(a).get();
        }


        LinkedList<Integer> source = new LinkedList<>();
        source.add(page.getId());
        Agregator<Post> agregator = postDao.agregator(source);
        List<Post> initPosts = agregator.getNext(10);
        req.getSession().setAttribute("agregator", agregator);
        req.getSession().setAttribute("posts", initPosts);
        req.getSession().setAttribute("page", page);

        if (page.getPageType() == PageTypes.PERSON) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsppages/page/person.jsp");
            requestDispatcher.forward(req, resp);
        }
        else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsppages/page/team.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val postBuilder = Post.builder();
        val page = (Page)req.getSession().getAttribute("page");

        postBuilder.firstName(page.getFirstName()).secondName(page.getSecondName()).nickname(page.getNickname());
        postBuilder.pageId(page.getId());
        postBuilder.id(page.getLastPostId() + 1);
        postBuilder.time(Instant.now());
        postBuilder.content(req.getParameter("content"));

        postDao.put(postBuilder.build());




    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
        postDao = provider.getPostDao();
    }
}
