package com.czbank.market.imp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.czbank.market.model.MarketTask;
import com.hdong.common.util.JaxbUtil;

import org.slf4j.Logger;

/**
 * Servlet implementation class MarketImportServlet
 */
@WebServlet("/MarketImportServlet")
public class MarketImportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger _log = LoggerFactory.getLogger(MarketImportServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */ 
    public MarketImportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String xmlStr = request.getParameter("sendxml");
		_log.info("Market服务器接收报文成功，接收到报文为：" + xmlStr);
		//Document document = DocumentHelper.
		MarketTask marketTask = JaxbUtil.convertToJavaBean(xmlStr, MarketTask.class);
		
		response.getWriter().append("Served at: ").append(request.getContextPath()).append(marketTask.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
