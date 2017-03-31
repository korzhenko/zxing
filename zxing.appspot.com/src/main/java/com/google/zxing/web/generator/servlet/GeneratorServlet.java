package com.google.zxing.web.generator.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 */
public class GeneratorServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("Start...");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }


    private String getParameter(HttpServletRequest pReq, String pName,String pDefault) {
        String resultValue = pReq.getParameter(pName);
        if (resultValue == null || resultValue.isEmpty()) {
            resultValue = pDefault;
        }
        return resultValue;
    }

    private static String getUrl(int sizeX, int sizeY, String ecLevel, String encoding, String content) {
        return "https://zxing.org/w/chart?cht=qr" +
                "&chs=" + sizeX + 'x' + sizeY +
                "&chld=" + ecLevel +
                "&choe=" + encoding +
                "&chl=" + content;
    }


    private void processRequest(HttpServletRequest pReq, HttpServletResponse pResp) throws IOException, ServletException {
        String pattern = pReq.getParameter("pattern");
        if (pattern == null || pattern.isEmpty()) {
            throw new ServletException("Bad parameters. See help at main page");
        }
        int count = Integer.parseInt(getParameter(pReq,"count","0"));
        int size = Integer.parseInt(getParameter(pReq,"size","350"));
        String strUrl = getUrl(size,size
                ,getParameter(pReq,"level","L")
                ,getParameter(pReq,"encoding","UTF-8")
                ,RandomStringGenerator.generate(pattern,count,getParameter(pReq,"separator","")));
        pResp.setContentType("image/png");
        OutputStream out = pResp.getOutputStream();

        final URL url = new URL(strUrl);
        final URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        pResp.setContentType(connection.getContentType());

        try {
            final byte[] buffer = new byte[1024];
            for (int length; (length = in.read(buffer)) != -1;) {
                out.write(buffer, 0, length);
            }
        } finally {
            out.flush();
            out.close();
            in.close();
        }
    }
}
