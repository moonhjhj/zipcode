package kr.co.bit.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.bit.database.ConnectionManager;
import kr.co.bit.vo.ZipcodeVO;

public class ZipcodeDAO {
	private ZipcodeVO getInstance(String line) {
		ZipcodeVO vo = new ZipcodeVO();
		// 5. 분석해서 -> , 로 분리
		// 6. VO 인스턴스 생성
		String[] temp = line.split(",");
		vo.setZipcode(temp[0]);
		vo.setSido(temp[1]);
		vo.setGugun(temp[2].equals("") ? " " : temp[2]);
		vo.setDong(temp[3]);
		System.out.println("temp4 " + temp[4].equals(""));
		vo.setRi(temp[4].equals("") ? " " : temp[4]);
		vo.setBldg(temp[5].equals("") ? " " : temp[5]);
		vo.setBunji(temp[6].equals("") ? " " : temp[6]);
		vo.setSeq(temp[7]);

		return vo;
	}

	/*********************** 20180410 - 1 ***********************************/
	public ArrayList<ZipcodeVO> getSearchList(String dong) {
		ArrayList<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		// System.out.println(dong);
		/************************** 2 **********************************/
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		String sql = "select * from zipcode_tbl where dong like ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+dong+"%");
			System.out.println(sql);
			rs = pstmt.executeQuery();
			ZipcodeVO vo = null;
			while (rs.next()) {
				vo = new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setRi(rs.getString(5));
				vo.setBldg(rs.getString("bldg"));
				vo.setBunji(rs.getString("bunji"));
				vo.setSeq(rs.getString("seq"));
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mgr.connectClose(con, pstmt, rs);
		}
		return list;
	}

	/***********************************************************************/
	public boolean insert(String path) {
		boolean flag = false;
		ConnectionManager mgr = new ConnectionManager();
		Connection con = mgr.getConnection();
		// 1. 파일에 접속해서 내용을 읽어와 5만개 정도를 입력한다.
		// 2. 패스를 근간으로 file 객체 생성
		File file = new File(path);
		ArrayList<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		// 3. 스트림 생성
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				// 4. 한 라인씩 읽어서 전달한 후
				// 7. 리스트에 저장
				// System.out.println(line);
				// zipcodeDAO로 line 넘겨줌->dao에서 vo객체 만들어서 arraylist에 집어넣어서 다시 여기로 넘겨줌
				list.add(this.getInstance(line)); // this.getinstance가 vo를 가져옴. 걔를 list에 넣으면 됨.
				// break; // <-테스트용으로 한번만 돌도록 break걸어줌
			}
			br.close();
			fr.close();
			// flag = true; //->test용
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(list.size());
		String sql = "insert into zipcode_tbl values (?,?,?,?,?,?,?,?) ";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			// 리스트에서 빼야함
			// 8. 리스트를 가지고 한꺼번에 입력
			for (ZipcodeVO vo : list) {
				// ZipcodeVO vo = list.get(0);
				pstmt.setString(1, vo.getZipcode());
				pstmt.setString(2, vo.getSido());
				pstmt.setString(3, vo.getGugun());
				pstmt.setString(4, vo.getDong());
				pstmt.setString(5, vo.getRi());
				pstmt.setString(6, vo.getBldg());
				pstmt.setString(7, vo.getBunji());
				pstmt.setString(8, vo.getSeq());
				int affectedCount = pstmt.executeUpdate();

				// if (affectedCount > 0) {
				// flag = true;
				// }
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mgr.connectClose(con, pstmt, null);
		return flag;
	}

	/******************** teacher version (수정전) *******************************/
	// public boolean insert(ZipcodeVO vo) {
	// boolean flag = false;
	// ConnectionManager mgr = new ConnectionManager();
	// Connection con = mgr.getConnection();
	//// if(con!=null) {
	//// flag = true;
	//// }
	// //파일에 접속해서 내용을 읽어와 5만개 정도를 입력한다.
	// mgr.connectClose(con, null, null);
	// return flag;
	// }
}
