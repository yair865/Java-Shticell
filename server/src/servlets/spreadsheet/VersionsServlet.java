package servlets.spreadsheet;

import constant.Constants;
import dto.dtoPackage.SpreadsheetDTO;
import engine.engineimpl.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Get Version Servlet" , urlPatterns = "/version")
public class VersionsServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Engine engine = ServletUtils.getEngine(request.getServletContext());
        String sheetName = SessionUtils.getSheetName(request);
        String userName = SessionUtils.getUsername(request);


        String versionParam = request.getParameter("version");
        if (versionParam == null || versionParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int version = Integer.parseInt(versionParam);
            SpreadsheetDTO spreadsheetDTO = getSpreadsheetByVersion(engine, version , sheetName , userName);
            if (spreadsheetDTO != null) {
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(Constants.GSON_INSTANCE.toJson(spreadsheetDTO));
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    private SpreadsheetDTO getSpreadsheetByVersion(Engine engine, int version , String sheetName ,String userName) {

        return engine.getSpreadsheetByVersion(version ,userName , sheetName);
    }
}
