package at.htlklu.servlets;

import at.htlklu.entities.Parts;
import at.htlklu.persistence.Dao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PartSearchServlet", value = "/search")
public class PartSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchKeyword = request.getParameter("search");

        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            List<Parts> partsList = Dao.searchParts(searchKeyword);
            request.setAttribute("parts", partsList);
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
