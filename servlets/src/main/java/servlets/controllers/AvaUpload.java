package servlets.controllers;

import core.Entities.Page;
import core.ImageProcessor;
import jdbc.DaoProvider;
import jdbc.dao.core.PageDao;
import lombok.extern.log4j.Log4j2;
import servlets.filters.AuthFilter;
import servlets.listeners.Initer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
@MultipartConfig
public class AvaUpload extends HttpServlet {

    private ImageProcessor imgp;
    private PageDao pageDao;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("image");
        try (InputStream fileContent = filePart.getInputStream()){
            String imgId = imgp.storeImage(fileContent, filePart.getSubmittedFileName());
            Page me = (Page) request.getSession().getAttribute(AuthFilter.AUTH);
            pageDao.updateAvatar(me, imgId);

            response.sendRedirect("/page");
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();

        String imgPath = config.getInitParameter("imageFolder");
        imgp = new ImageProcessor(imgPath);
    }
}
