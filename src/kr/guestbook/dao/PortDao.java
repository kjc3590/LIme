package kr.guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.guestbook.domain.Port;

public class PortDao {

	//싱글턴 패턴
	private static PortDao instance = new PortDao();

	public static PortDao getInstance(){
		return instance;
	}

	private PortDao(){}

	//context.xml에서 설정정보를 읽어들여 커넥션풀로부터
	//커넥션을 할당 받음
	private Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = 
				(DataSource)initCtx.lookup("java:comp/env/jdbc/orcl");

		return ds.getConnection();
	}

	//자원정리
	private void executeClose(ResultSet rs, 
			PreparedStatement pstmt,
			Connection conn){
		if(rs!=null)try{rs.close();}catch(SQLException e){}
		if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		if(conn!=null)try{conn.close();}catch(SQLException e){}
	}
	
	// 포트폴리오 페이지네이션 
			public List<Port> portList(int startRow, int endRow)
					throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				List<Port> list = null;
				String sql = "";
				
				try{
					//커넥션풀로부터 커넥션을 반환
					conn = getConnection();
					//SQL문 작성
					sql = "select * from(select a.*, rownum rnum from"
						    + "(select * from ian order by src asc)a)"
						    + "where rnum >= ? and rnum <= ?";
					//PreparedStatement 객체 생성
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, startRow);
					pstmt.setInt(2, endRow);
					//SQL문 반영해서 결과행을 ResultSet 담음
					rs = pstmt.executeQuery();
					list = new ArrayList<Port>();
					
					while(rs.next()){
						Port book = new Port();
						book.setSrc(rs.getInt("src"));
						book.setName(rs.getString("name"));
						book.setColumn1(rs.getString("column1"));
						
						//자바빈을 ArrayList에 저장
						list.add(book);
					}					
					
				}catch(Exception e){
					throw new Exception(e);
				}finally{
					executeClose(rs, pstmt, conn);
				}
				return list;
			}
	//사진 목록2 (9보다 작은)
	public List<Port> getList()
			throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Port> list = null;
		String sql = "";
		
		try{
			//커넥션풀로부터 커넥션을 반환
			conn = getConnection();
			//SQL문 작성
			sql = "select * from(select a.*, rownum rnum from"
				    + "(select * from ian order by src asc)a)"
				    + "where rnum <= 9 ";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문 반영해서 결과행을 ResultSet 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<Port>();
			
			while(rs.next()){
				Port book = new Port();
				book.setSrc(rs.getInt("src"));
				book.setName(rs.getString("name"));
				book.setColumn1(rs.getString("column1"));
				
				//자바빈을 ArrayList에 저장
				list.add(book);
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	// 사진출력 (9보다 큰 나머지)
	public List<Port> getList2()
			throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Port> list = null;
		String sql = "";
		
		try{
			//커넥션풀로부터 커넥션을 반환
			conn = getConnection();
			//SQL문 작성
			sql = "select * from(select a.*, rownum rnum from"
					+ "(select * from ian order by src asc)a)"
					+ "where rnum > 9 ";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문 반영해서 결과행을 ResultSet 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<Port>();
			
			while(rs.next()){
				Port book = new Port();
				book.setSrc(rs.getInt("src"));
				book.setName(rs.getString("name"));
				book.setColumn1(rs.getString("column1"));
				
				//자바빈을 ArrayList에 저장
				list.add(book);
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	// 이안 포트폴리오 자동추가 (30개)
		public void insert()throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			int cnt = 1;
			String [] title = {"-","인본정형외과","제니스치과의원","바른정형외과","판교연세산부인과","오름통증클리닉","제이치과","조앤미 치과","참조은내과검진센터","서울듀크의원","예인이비인후과","췌리쉬성형외과","라벨성형외과","편안성모내과","맘마외과","미웰유외과","온정형외과","강스 교정치과","고운비의원","봄정신건강의학과","SOK내과","가온삼성가정의학과","가인가정의학과의원","경로치과","노통증의학의원","누가의원","두루가정의학과","라벨 성형외과","메이트치과의원","미소래 이비인후과","블랑스 치과"}; 
			
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();

				sql = "insert into ian values (?,?,?) ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);

				while(cnt<31){
				//?에 데이터 매칭
				pstmt.setInt(1, cnt);
				pstmt.setString(2, title[cnt]);
				pstmt.setString(3, "a");
				System.out.println(title[cnt]);
				pstmt.executeUpdate();
				cnt++;
			}
				//SQL문 반영
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		// 이안 포트폴리오 추가
		public void insertPort(Port port)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				
				sql = "insert into ian values (?,?,?) ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
					//?에 데이터 매칭
					pstmt.setInt(1, port.getSrc());
					pstmt.setString(2, port.getName());
					pstmt.setString(3, port.getColumn1());
					pstmt.executeUpdate();
				//SQL문 반영
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		// 이안 포트폴리오 수정
		public void UpdatetPort(Port port)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				
				sql = "update ian set name = ? , column1 =? where src = ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 매칭
				pstmt.setString(1, port.getName());
				pstmt.setString(2, port.getColumn1());
				pstmt.setInt(3, port.getSrc());
				pstmt.executeUpdate();
				//SQL문 반영
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		//예약 총 갯수 (페이지네이션 시 사용)
		public int getCount(Port port)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int count = 0;
			String sql = "";
			String keyField = port.getKeyField();
			String keyWord = port.getKeyWord();
			
			try{
				//커넥션풀로부터 커넥션 객체 반환
				conn = getConnection();
				//SQL문 작성
				if(keyField != null && keyWord !=null){
					sql = "select count(*) from ian_resv where "+keyField.trim()+" like '%"+keyWord.trim()+"%' ";
				}else{
					sql = "select count(*) from ian_resv";
				}
				pstmt = conn.prepareStatement(sql);
				//PreparedStatement 객체 생성
				//SQL문을 반영해서 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();
				if(rs.next()){
					count = rs.getInt(1);
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return count;
		}
		
		//포트폴리오의 총 갯수
		public int portCount()throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int count = 0;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션 객체 반환
				conn = getConnection();
				//SQL문 작성
				sql = "select count(*) from ian";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//SQL문을 반영해서 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();
				if(rs.next()){
					count = rs.getInt(1);
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return count;
		}
		
		//포트폴리오 마지막 SRC 구하기
		public int endRowSrc()
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "";
			int src = 0;
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql = "SELECT (SRC) FROM  (SELECT * FROM IAN ORDER BY SRC DESC) WHERE ROWNUM = 1";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				if(rs.next()){
					src = rs.getInt("src");
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return src;
		}



		
		//예약
		public void resv(Port port)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();

				sql = "insert into ian_resv values (resv_seq.nextval,?,?,?,?,?,?,?,?,?,?,sysdate,0,?) ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);

				//?에 데이터 매칭
				pstmt.setString(1,port.getDivision());
				pstmt.setString(2, port.getB_name());
				pstmt.setString(3, port.getResv_name());
				pstmt.setString(4, port.getTel());
				pstmt.setString(5, port.getPhonetel());
				pstmt.setString(6, port.getEmail());
				pstmt.setString(7, port.getAddress());
				pstmt.setString(8, port.getAttach());
				pstmt.setString(9, port.getTitle());
				pstmt.setString(10, port.getContent());
				pstmt.setString(11, port.getFile_name());
				pstmt.executeUpdate();
				//SQL문 반영
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		//자동 예약 (30개)
		public void resvAuto(Port port)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			int cnt = 0;
			
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				
				sql = "insert into ian_resv values (resv_seq.nextval,?,?,?,?,?,?,?,?,?,?,sysdate,0,?) ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				while(cnt<30){
					
				//?에 데이터 매칭
				pstmt.setString(1, "인테리어"+cnt);
				pstmt.setString(2, "파인"+cnt);
				pstmt.setString(3, "김종찬");
				pstmt.setString(4, "031-573-3590");
				pstmt.setString(5, "010-9211-3590");
				pstmt.setString(6, "kjc3590@naver.com");
				pstmt.setString(7, "서울특별시 성북구");
				pstmt.setString(8, "http://mimgnews2.naver.net/image/111/2017/01/29/1485652704809_1_101856_99_20170129105104.jpg?type=w540");
				pstmt.setString(9, "안녕하세요"+cnt);
				pstmt.setString(10, "내용입력");
				pstmt.setString(11, "파일이름");
				pstmt.executeUpdate();
				cnt++;
				//SQL문 반영
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		//예약 목록 출력 페이지네이션
		public List<Port> resvList(Port port, int startRow , int endRow)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Port> list = null;
			String sql = "";
			String keyField = port.getKeyField();
			String keyWord = port.getKeyWord();
			String sort = port.getSort();
			
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				
				/*if (keyField.equals("all")){
					sql = "select * from(select a.*, rownum rnum from"
							+ "(select * from ian_resv order by "+sort+" asc)a)"
							+ "where rnum >= ? and rnum <= ?";
				}else */
				
				if(keyField != null && !keyWord.equals("")){
						sql= "SELECT * FROM (SELECT A.*, ROWNUM RNUM FROM (SELECT * FROM IAN_RESV WHERE "+keyField.trim()+" LIKE '%"+keyWord.trim()+"%' ORDER BY "+sort+" asc) A) WHERE RNUM >= ? AND RNUM <= ?";
				}else{
					sql = "select * from(select a.*, rownum rnum from"
							+ "(select * from ian_resv order by "+sort+" asc)a)"
							+ "where rnum >= ? and rnum <= ?";
				}
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
				//PreparedStatement 객체 생성
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Port>();
				
				while(rs.next()){
					Port resvList = new Port();
					
					int resv_id = rs.getInt("resv_id");
					String division = rs.getString("division");
					String b_name = rs.getString("b_name");
					String resv_name = rs.getString("resv_name");
					String tel = rs.getString("tel");
					String phonetel = rs.getString("phonetel");
					String email = rs.getString("email");
					String address = rs.getString("address");
					String attach = rs.getString("attach");
					String title = rs.getString("title");
					String content = rs.getString("content");
					String day = rs.getString("day");
					int hit = rs.getInt("hit");
					
					resvList.setResv_id(resv_id);
					resvList.setDivision(division);
					resvList.setB_name(b_name);
					resvList.setResv_name(resv_name);
					resvList.setTel(tel);
					resvList.setPhonetel(phonetel);
					resvList.setEmail(email);
					resvList.setAddress(address);
					resvList.setAttach(attach);
					resvList.setTitle(title);
					resvList.setContent(content);
					resvList.setDay(day);
					resvList.setHit(hit);
					//자바빈을 ArrayList에 저장
					list.add(resvList);
				}
				
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		//예약 목록 출력 (전체목록)
		public List<Port> resvListAll()
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Port> list = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql = "select * from ian_resv order by day desc";
						//PreparedStatement 객체 생성
						pstmt = conn.prepareStatement(sql);
						//SQL문 반영해서 결과행을 ResultSet 담음
						rs = pstmt.executeQuery();
						list = new ArrayList<Port>();
						
						while(rs.next()){
							Port resvList = new Port();
							
							int resv_id = rs.getInt("resv_id");
							String division = rs.getString("division");
							String b_name = rs.getString("b_name");
							String resv_name = rs.getString("resv_name");
							String tel = rs.getString("tel");
							String phonetel = rs.getString("phonetel");
							String email = rs.getString("email");
							String address = rs.getString("address");
							String attach = rs.getString("attach");
							String title = rs.getString("title");
							String content = rs.getString("content");
							String day = rs.getString("day");
							String file_name = rs.getString("file_name");
							int hit = rs.getInt("hit");
							
							resvList.setResv_id(resv_id);
							resvList.setDivision(division);
							resvList.setB_name(b_name);
							resvList.setResv_name(resv_name);
							resvList.setTel(tel);
							resvList.setPhonetel(phonetel);
							resvList.setEmail(email);
							resvList.setAddress(address);
							resvList.setAttach(attach);
							resvList.setTitle(title);
							resvList.setContent(content);
							resvList.setDay(day);
							resvList.setHit(hit);
							resvList.setFile_name(file_name);
							//자바빈을 ArrayList에 저장
							list.add(resvList);
						}
						
						
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return list;
		}
		

		// 예약내역 상세정보 (id 받아와서)
		public List<Port> resvListDetail(int resv_id)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Port> list = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql = "select * from ian_resv where resv_id =?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, resv_id);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Port>();
				
				while(rs.next()){
					
					Port resvList = new Port();
					
					String division = rs.getString("division");
					String b_name = rs.getString("b_name");
					String resv_name = rs.getString("resv_name");
					String tel = rs.getString("tel");
					String phonetel = rs.getString("phonetel");
					String email = rs.getString("email");
					String address = rs.getString("address");
					String attach = rs.getString("attach");
					String title = rs.getString("title");
					String content = rs.getString("content");
					String file_name = rs.getString("file_name");
					String day = rs.getString("day");
					int hit = rs.getInt("hit");
					
					resvList.setResv_id(resv_id);
					resvList.setDivision(division);
					resvList.setB_name(b_name);
					resvList.setResv_name(resv_name);
					resvList.setTel(tel);
					resvList.setPhonetel(phonetel);
					resvList.setEmail(email);
					resvList.setAddress(address);
					resvList.setAttach(attach);
					resvList.setTitle(title);
					resvList.setContent(content);
					resvList.setDay(day);
					resvList.setHit(hit);
					resvList.setFile_name(file_name);

					//자바빈을 ArrayList에 저장
					list.add(resvList);
				}
				
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return list;
		}
		//포트폴리오 수정 디테일 (해당 src)
		public List<Port> portListDetail(int src)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Port> list = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql = "select * from ian where src =?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, src);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Port>();
				
				while(rs.next()){
					
					Port portList = new Port();
					
					String name = rs.getString("name");
					String col1 = rs.getString("column1");
					
					portList.setName(name);
					portList.setColumn1(col1);
					
					//자바빈을 ArrayList에 저장
					list.add(portList);
				}
				
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		//글 조회수 증가
		public void hit(int resv_id) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			
			try{
				conn = getConnection();
				sql = "update ian_resv set hit=hit+1 where resv_id=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, resv_id);
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		//해당 id의 예약내역 삭제
		public void deleteResv(int resv_id) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt= null;
			String sql="";

			try{ 
				// 커넥션풀로부터 커넥션을 반환
				conn = getConnection();

				//SQL문 작성
				sql = "delete from ian_resv where resv_id=?";

				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);

				//?에 데이터 매칭
				pstmt.setInt(1, resv_id);

				//SQL문 반영
				pstmt.executeUpdate();

			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				executeClose(null, pstmt, conn);
			}
		}
		
		//포트폴리오 삭제 (src받아서)
		public void portDelete(int src) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt= null;
			String sql="";
			
			try{ 
				// 커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				
				//SQL문 작성
				sql = "delete from ian where src=?";
				
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 매칭
				pstmt.setInt(1, src);
				
				//SQL문 반영
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				executeClose(null, pstmt, conn);
			}
		}
		
		//adm 페이지 로그인
		   public Port Login(String id) throws Exception{
		      Connection conn = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      Port port =null;
		      String sql= "";
		      
		      try{
		         // 커넥션 풀로부터 커넥션을 할당
		         conn = getConnection();
		         //SQL문
		         sql = "select * from ian_login where id=?";
		         //PreparedStatement 객체 생성
		         pstmt = conn.prepareStatement(sql);
		         			//?표 순번
		         pstmt.setString(1, id);
		         //SQL문 실행해서 결과행을 ResultSet에 담음
		         rs = pstmt.executeQuery();
		         
		         if(rs.next()) {
		        	 //자바빈 객체생성, 자바빈에 넣어주기
		        	 port = new Port();
		        	 port.setId(rs.getString("id"));
		        	 port.setPassword(rs.getString("pw"));
		         }
		      }
		      catch(Exception e){
		         throw new Exception(e);
		      }finally{
		         executeClose(rs, pstmt, conn);
		      }
		      return port;
		   }


}
