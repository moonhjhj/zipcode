package kr.co.bit.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.bit.dao.ZipcodeDAO;
import kr.co.bit.vo.ZipcodeVO;

public class CommandController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
		// System.out.println("in");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");
		cmd = cmd == null ? "" : cmd;
		String url = "./result.jsp";
		/************* 20180410 - 2 *******************/

		if (cmd.equals("searchDong")) {
			String dong = request.getParameter("dong");
			// dong = new String(dong.getBytes("ISO-8859-1"), "UTF-8");
			ZipcodeDAO dao = new ZipcodeDAO();
			ArrayList<ZipcodeVO> list = dao.getSearchList(dong);
			// dao.getSearchList(dong);
			// request.setAttribute("result", "success");
			request.setAttribute("list", list);
			url = "./postal.jsp";

		} else {
			String path = this.getServletContext().getRealPath("WEB-INF/file/zipcode_utf.csv");
			ZipcodeDAO dao = new ZipcodeDAO();
			boolean flag = dao.insert(path);
			request.setAttribute("result", flag ? "success" : "fail");
		}
		/***********************************************************************/
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, resp);

	}

}
