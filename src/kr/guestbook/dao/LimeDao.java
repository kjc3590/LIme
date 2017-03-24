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

import kr.guestbook.domain.Lime;

public class LimeDao {

	//싱글턴 패턴
	private static LimeDao instance = new LimeDao();

	public static LimeDao getInstance(){
		return instance;
	}

	private LimeDao(){}

	//context.xml에서 설정정보를 읽어들여 커넥션풀로부터
	//커넥션을 할당 받음
	private Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = 
				(DataSource)initCtx.lookup("java:comp/env/jdbc/mysql");

		return ds.getConnection();
	}

	//자원정리
	private void executeClose(ResultSet rs,PreparedStatement pstmt,Connection conn){
		if(rs!=null)try{rs.close();}catch(SQLException e){}
		if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
		if(conn!=null)try{conn.close();}catch(SQLException e){}
	}
	
	//회원가입
	public void Lime_join(Lime lime) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try{
			//커넥션풀로부터 커넥션을 할당
			conn = getConnection();
			
			sql = "insert into Lime_member values (?,?,?,?,?,?,?,?,'wait',date_format(now(),?)) ";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 매칭
			pstmt.setString(1, lime.getMember_id());
			pstmt.setString(2, lime.getMember_pw());
			pstmt.setString(3, lime.getMember_name());
			pstmt.setInt(4, lime.getCompany_pk());
			pstmt.setString(5, lime.getCompany_name());
			pstmt.setString(6, lime.getMember_ptel());
			pstmt.setString(7, lime.getMember_tel());
			pstmt.setString(8, lime.getMember_email());
			pstmt.setString(9, "%Y-%m-%d %H:%i");
			pstmt.executeUpdate();
			//SQL문 반영
			
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			executeClose(null, pstmt, conn);
		}
	}
	
	   //업체 조회 
	   public List<Lime> CompanyList() throws Exception{
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs = null;
		   String sql= "";
		   List<Lime> list = null;
		   
		   try{
			   // 커넥션 풀로부터 커넥션을 할당
			   conn = getConnection();
			   //SQL문
			   sql =  "select * from lime_company";
			   //PreparedStatement 객체 생성
			   pstmt = conn.prepareStatement(sql);
			   //?표 순번
			   //SQL문 실행해서 결과행을 ResultSet에 담음
			   rs = pstmt.executeQuery();
				list = new ArrayList<Lime>();
			   
			   while(rs.next()) {
					Lime cList = new Lime();
				   //자바빈 객체생성, 자바빈에 넣어주기
					
					cList.setCompany_pk(rs.getInt("company_pk"));
					cList.setCompany_name(rs.getString("company_name"));

					list.add(cList);
			   }
		   }
		   catch(Exception e){
			   throw new Exception(e);
		   }finally{
			   executeClose(rs, pstmt, conn);
		   }
		   return list;
	   }
	   
	   //adm 페이지 로그인
	   public Lime Login(String id) throws Exception{
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      Lime lime =null;
	      String sql= "";
	      
	      try{
	         // 커넥션 풀로부터 커넥션을 할당
	         conn = getConnection();
	         //SQL문
	         sql = "select * from lime_member where member_id=?";
	         //PreparedStatement 객체 생성
	         pstmt = conn.prepareStatement(sql);
	         			//?표 순번
	         pstmt.setString(1, id);
	         //SQL문 실행해서 결과행을 ResultSet에 담음
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	        	 //자바빈 객체생성, 자바빈에 넣어주기
	        	 lime = new Lime();
	        	 lime.setMember_id(id);
	        	 lime.setMember_name(rs.getString("member_name"));
	        	 lime.setMember_pw(rs.getString("member_pw"));
	        	 lime.setCompany_pk(rs.getInt("company_pk"));
	        	 lime.setCompany_name(rs.getString("company_name"));
	        	 lime.setMember_ptel(rs.getString("member_ptel"));
	        	 lime.setMember_tel(rs.getString("member_tel"));
	        	 lime.setMember_email(rs.getString("member_email"));
	        	 lime.setMember_type(rs.getString("member_type"));
	        	 lime.setMember_day(rs.getString("member_day"));
	         }
	      }
	      catch(Exception e){
	         throw new Exception(e);
	      }finally{
	         executeClose(rs, pstmt, conn);
	      }
	      return lime;
	   }
	   
		//회원가입 총 갯수 (페이지네이션 시 사용)
		public int joinCount()throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int count = 0;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션 객체 반환
				conn = getConnection();
				//SQL문 작성
					sql = "select count(*) from lime_member";
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

		   //가입회원 조회 
		   public List<Lime> JoinList(int pageN , int pageSize) throws Exception{
			   Connection conn = null;
			   PreparedStatement pstmt = null;
			   ResultSet rs = null;
			   String sql= "";
			   List<Lime> list = null;
			   
			   try{
				   // 커넥션 풀로부터 커넥션을 할당
				   conn = getConnection();
				   //SQL문
				   sql =  "select * from lime_member order by member_type desc limit ?,? ";
				   //PreparedStatement 객체 생성
				   pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, pageN);
					pstmt.setInt(2, pageSize);
				   //?표 순번
				   //SQL문 실행해서 결과행을 ResultSet에 담음
				   rs = pstmt.executeQuery();
					list = new ArrayList<Lime>();
				   
				   while(rs.next()) {
						Lime joinList = new Lime();
					   //자바빈 객체생성, 자바빈에 넣어주기
						joinList.setMember_id(rs.getString("member_id"));
						joinList.setMember_name(rs.getString("member_name"));
						joinList.setCompany_name(rs.getString("company_name"));
						joinList.setMember_tel(rs.getString("member_tel"));
						joinList.setMember_type(rs.getString("member_type"));
						joinList.setMember_ptel(rs.getString("member_ptel"));
						joinList.setMember_day(rs.getString("member_day"));
						list.add(joinList);
				   }
			   }
			   catch(Exception e){
				   throw new Exception(e);
			   }finally{
				   executeClose(rs, pstmt, conn);
			   }
			   return list;
		   }
		   
			//회원 승인
			public void join_accept(String member_id) throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				String sql = "";
				try{
					//커넥션풀로부터 커넥션을 할당
					conn = getConnection();
					
					sql = "update Lime_member set member_type = 'user'  "
							+" where member_id = ? ";
					//PreparedStatement 객체 생성
					pstmt = conn.prepareStatement(sql);
					
					//?에 데이터 매칭
					pstmt.setString(1, member_id);
					pstmt.executeUpdate();
					//SQL문 반영
					
				}catch(Exception e){
					throw new Exception(e);
				}finally{
					executeClose(null, pstmt, conn);
				}
			}		
	   
	   //예약 시 답변 테이블에 값 생성
		public void work_resv() throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				
				sql = "insert into Lime_reply (question_id) value (last_insert_id()) ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 매칭
				pstmt.executeUpdate();
				//SQL문 반영
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}

		//문의
		public void resv(Lime lime)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			int cnt =1 ;
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				
				sql = "insert into Lime_question (member_id, member_name, member_email, member_tel, member_ptel , question_day, question_division,question_content, question_attach, question_filename) values (?,?,?,?,?,date_format(now(),?),?,?,?,?) ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 매칭
				pstmt.setString(cnt++, lime.getMember_id());
				pstmt.setString(cnt++, lime.getMember_name());
				pstmt.setString(cnt++, lime.getMember_email());
				pstmt.setString(cnt++, lime.getMember_tel());
				pstmt.setString(cnt++, lime.getMember_ptel());
				pstmt.setString(cnt++, "%Y-%m-%d %H:%i");
				pstmt.setString(cnt++, lime.getQuestion_division());
				pstmt.setString(cnt++, lime.getQuestion_content());
				pstmt.setString(cnt++, lime.getQuestion_attach());
				pstmt.setString(cnt++, lime.getQuestion_filename());
				pstmt.executeUpdate();
				//SQL문 반영
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
	   
	   
		//자동 문의
		public void resvAuto(Lime lime)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			int cnt = 1;
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				
				sql = "insert into lime_question(member_id, member_name, member_email, member_tel, member_ptel ,question_day, question_division,"
						+ " question_manager, question_content, question_attach, question_filename, question_progress) values (?,?,?,?,?,date_format(now(),?),?,?,?,?,?,?) ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 매칭
					pstmt.setString(cnt++,"test");
					pstmt.setString(cnt++,"아하");
					pstmt.setString(cnt++,"구구");
					pstmt.setString(cnt++,"절절");
					pstmt.setString(cnt++,"하하");
					pstmt.setString(cnt++, "%Y-%m-%d %H:%i");
					pstmt.setString(cnt++, "이히");
					pstmt.setString(cnt++, "담담담");
					pstmt.setString(cnt++, "오호");
					pstmt.setString(cnt++, "으흐");
					pstmt.setString(cnt++, "이흐");
					pstmt.setString(cnt++, "아흐");
					pstmt.executeUpdate();
				//SQL문 반영
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		//예약 총 갯수 (페이지네이션 시 사용)
				public int getCount(Lime lime)throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					int count = 0;
					String sql = "";
					
					try{

						String keyField = lime.getKeyField();
						String keyWord = lime.getKeyWord();
						
						//커넥션풀로부터 커넥션 객체 반환
						conn = getConnection();
						//SQL문 작성
						
						if(keyField != null && keyWord !=null){
							sql = "select count(*) from LIME_QUESTION A, LIME_REPLY B , LIME_MEMBER C "
									+ "where  A.question_id = B.question_ID and a.member_id = c.member_id  "
									+ "and "+keyField+" like '%"+keyWord.trim()+"%' ";
						}else{
							sql = "select count(*) from lime_question";
						}
						if("All".equals(keyField)){
							sql = "select count(*) from LIME_QUESTION A, LIME_REPLY B , LIME_MEMBER C "
									+ " where A.question_id = B.question_ID and a.member_id = c.member_id  and "
									+ "concat(A.question_id , A.QUESTION_MANAGER , A.QUESTION_PROGRESS , "
									+ " A.QUESTION_CONTENT , A.MEMBER_NAME , A.question_division, c.company_name ,B.REPLY_PRIORITY)  "
									+ "like '%"+keyWord.trim()+"%' ";
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
		
		//예약 목록 출력 페이지네이션
		public List<Lime> resvList(Lime lime, int pageN , int pageSize)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Lime> list = null;
			String sql = "";
			String keyField = lime.getKeyField();
			String keyWord = lime.getKeyWord();
			String sort = lime.getSort();
			
			if(sort ==null){
				sort = "question_id";
			}
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				if(keyField != null && keyWord!=null){
					sql= "SELECT A.* ,B.REPLY_DAY, B.REPLY_PRIORITY, B.REPLY_DAY, c.company_name  "
							+ "FROM LIME_QUESTION A, LIME_REPLY B, LIME_MEMBER C "
							+ "WHERE A.question_id = B.question_ID and a.member_id = c.member_id "
							+ "and "+keyField.trim()+" LIKE '%"+keyWord.trim()+"%' ORDER BY a."+sort+" desc LIMIT ?,? ";
				}else{
					sql = "select  A.* ,B.REPLY_DAY, B.REPLY_PRIORITY, B.REPLY_DAY, c.company_name "
							+ " from lime_question a , lime_reply b , LIME_MEMBER C "
							+ " WHERE A.question_id = B.question_ID and a.member_id = c.member_id order by a."+sort+" desc limit ?,? ";
				}
				if("All".equals(keyField)){
					sql= "SELECT A.* ,B.REPLY_DAY, B.REPLY_PRIORITY, B.REPLY_DAY, c.company_name  "
							+ "FROM LIME_QUESTION A, LIME_REPLY B , LIME_MEMBER C "
							+ "WHERE A.question_id = B.question_ID and a.member_id = c.member_id "
							+ "and concat(A.question_id , A.QUESTION_MANAGER , A.QUESTION_PROGRESS , A.QUESTION_CONTENT , A.MEMBER_NAME , "
							+ "A.question_division, c.company_name ,B.REPLY_PRIORITY) "
							+ "LIKE '%"+keyWord.trim()+"%' ORDER BY a."+sort+" desc LIMIT ?,? ";
				}
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pageN);
				pstmt.setInt(2, pageSize);
				//PreparedStatement 객체 생성
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Lime>();
				
				while(rs.next()){
					Lime resvList = new Lime();
					
					int question_id = rs.getInt("question_id");
					String member_id = rs.getString("member_id");
					String member_name = rs.getString("member_name");
					String company_name = rs.getString("company_name");
					String question_day = rs.getString("question_day");
					String reply_day = rs.getString("reply_day");
					String question_division = rs.getString("question_division");
					String question_content = rs.getString("question_content");
					String question_attach = rs.getString("question_attach");
					String question_filename = rs.getString("question_filename");
					String question_progress = rs.getString("question_progress");
					String question_manager = rs.getString("question_manager");
					String reply_priority = rs.getString("reply_priority");
					
					resvList.setQuestion_id(question_id);
					resvList.setMember_id(member_id);
					resvList.setMember_name(member_name);
					resvList.setQuestion_day(question_day);
					resvList.setReply_day(reply_day);
					resvList.setQuestion_division(question_division);
					resvList.setQuestion_content(question_content);
					resvList.setQuestion_attach(question_attach);
					resvList.setQuestion_filename(question_filename);
					resvList.setQuestion_progress(question_progress);
					resvList.setQuestion_manager(question_manager);
					resvList.setReply_priority(reply_priority);
					resvList.setCompany_name(company_name);

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
		public List<Lime> resvListDetail(int question_id)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Lime> list = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql = "select * from Lime_question where question_id =?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, question_id);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Lime>();
				
				while(rs.next()){
					
					Lime resvList = new Lime();
					
					question_id = rs.getInt("question_id");
					String member_id = rs.getString("member_id");
					String member_name = rs.getString("member_name");
					String member_email = rs.getString("member_email");
					String member_tel = rs.getString("member_tel");
					String member_ptel = rs.getString("member_ptel");
					String question_day = rs.getString("question_day");
					String question_division = rs.getString("question_division");
					String question_content = rs.getString("question_content");
					String question_attach = rs.getString("question_attach");
					String question_filename = rs.getString("question_filename");
					String question_progress = rs.getString("question_progress");
					String question_manager = rs.getString("question_manager");
					
					resvList.setQuestion_id(question_id);
					resvList.setMember_id(member_id);
					resvList.setMember_name(member_name);
					resvList.setMember_email(member_email);
					resvList.setMember_tel(member_tel);
					resvList.setMember_ptel(member_ptel);
					resvList.setQuestion_day(question_day);
					resvList.setQuestion_division(question_division);
					resvList.setQuestion_content(question_content);
					resvList.setQuestion_attach(question_attach);
					resvList.setQuestion_filename(question_filename);
					resvList.setQuestion_progress(question_progress);
					resvList.setQuestion_manager(question_manager);

					
					list.add(resvList);
				}
				
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
/*		// Join list
		public List<Lime> QMListDetail(int question_id)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Lime> list = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql =    "SELECT B.COMPANY_NAME, B.MEMBER_PTEL, B.MEMBER_TEL, B.MEMBER_EMAIL, B.MEMBER_NAME , A.MEMBER_ID " + 
						  "FROM LIME_QUESTION A, LIME_MEMBER B " +
						  "WHERE A.MEMBER_ID = B.MEMBER_ID " +
						    "AND A.QUESTION_ID = ? ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, question_id);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Lime>();
				
				while(rs.next()){
					
							Lime resvList = new Lime();
						   //자바빈 객체생성, 자바빈에 넣어주기
							resvList.setMember_name(rs.getString("member_name"));
							resvList.setCompany_name(rs.getString("company_name"));
							resvList.setMember_tel(rs.getString("member_tel"));
							resvList.setMember_ptel(rs.getString("member_ptel"));
							resvList.setMember_email(rs.getString("member_email"));
							resvList.setMember_id(rs.getString("member_id"));
					
					list.add(resvList);
				}
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return list;
		}*/
		
		// Join CompanyName
		public String CompanyName(int question_id)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String CompanyName = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql =  "SELECT B.COMPANY_NAME " + 
						"FROM LIME_QUESTION A, LIME_MEMBER B " +
						"WHERE A.MEMBER_ID = B.MEMBER_ID " +
						"AND A.QUESTION_ID = ? ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, question_id);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
					//자바빈 객체생성, 자바빈에 넣어주기
				while(rs.next()){
					CompanyName = rs.getString("company_name");
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return CompanyName;
		}
		
		// Join Division
		public Lime Division(int question_id)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Lime lime = new Lime();
			String sql = "";

			try{
				//커넥션풀로부터 커넥션을 반환
				Lime resvList = new Lime();
				conn = getConnection();
				//SQL문 작성
				sql =    "select * from question_id ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, question_id);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				//자바빈 객체생성, 자바빈에 넣어주기
				while(rs.next()){
					lime.setQuestion_division(rs.getString("question_division"));
					lime.setQuestion_content(rs.getString("question_content"));
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return lime;
		}

		// 예약내역 상세정보 (id 받아와서)
		public List<Lime> workListDetail(int question_id)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Lime> list = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql = "SELECT B.COMPANY_NAME, B.MEMBER_PTEL, B.MEMBER_TEL, B.MEMBER_EMAIL, B.MEMBER_NAME ,A.QUESTION_FILENAME,A.QUESTION_ATTACH,A.QUESTION_DIVISION, A.QUESTION_CONTENT " + 
						  "FROM LIME_QUESTION A, LIME_MEMBER B " +
						  "WHERE A.MEMBER_ID = B.MEMBER_ID " +
						    "AND A.QUESTION_ID = ? ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, question_id);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Lime>();
				
				while(rs.next()){
					Lime resvList = new Lime();
				   //자바빈 객체생성, 자바빈에 넣어주기
					resvList.setMember_name(rs.getString("member_name"));
					resvList.setCompany_name(rs.getString("company_name"));
					resvList.setMember_tel(rs.getString("member_tel"));
					resvList.setMember_ptel(rs.getString("member_ptel"));
					resvList.setMember_email(rs.getString("member_email"));
					resvList.setQuestion_division(rs.getString("question_division"));
					resvList.setQuestion_content(rs.getString("question_content"));
					resvList.setQuestion_attach(rs.getString("question_attach"));
					resvList.setQuestion_filename(rs.getString("question_filename"));
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
		public List<Lime> replyDetail(int Question_id)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Lime> list = null;
			String sql = "";
			
			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql = "SELECT A.QUESTION_MANAGER, A.QUESTION_DIVISION, A.QUESTION_PROGRESS, B.*  " + 
						"FROM LIME_QUESTION A, LIME_REPLY B " +
						"WHERE A.QUESTION_ID = B.QUESTION_ID " +
						"AND B.Question_id = ? ";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Question_id);
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Lime>();
				
				while(rs.next()){
					Lime resvList = new Lime();
					//자바빈 객체생성, 자바빈에 넣어주기

					resvList.setReply_id(rs.getInt("reply_id"));
					resvList.setQuestion_manager(rs.getString("question_manager"));
					resvList.setQuestion_division(rs.getString("question_division"));
					resvList.setDetailDivision(rs.getString("detailDivision"));
					resvList.setReply_keyword(rs.getString("reply_keyword"));
					resvList.setReply_method(rs.getString("reply_method"));
					resvList.setReply_term(rs.getString("reply_term"));
					resvList.setReply_priority(rs.getString("reply_priority"));
					resvList.setQuestion_progress(rs.getString("question_progress"));
					resvList.setReply_day(rs.getString("reply_day"));
					resvList.setReply_content(rs.getString("reply_content"));

					list.add(resvList);
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return list;
			
		}
		
		//예약 후 답변
		public void work_update(Lime lime) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			int cnt = 1;
			try{
				
				String priority = lime.getReply_priority();
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				String progress = lime.getQuestion_progress();
				String progress_ago = lime.getprogress_ago();
				
				if (priority.isEmpty()){
					priority = "보통";
				}
				
				if(progress_ago.isEmpty() && progress.equals("작업완료")){
					sql = "update lime_reply a , lime_question b set a.member_id =? , a.member_name = ?, a.reply_day = date_format(now(),?) , a.reply_comday = date_format(now(),?) , " +
							" a.detailDivision =?  , a.reply_keyword = ? ,a.reply_priority = ?, a.reply_method=? , a.reply_term= ?, a.reply_content = ? , b.question_progress = ? , b.question_division = ? , b.question_manager = ? " +
							" where a.question_id = b.question_id and a.reply_id = ?  ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(cnt++, lime.getMember_id());
					pstmt.setString(cnt++, lime.getMember_name());
					pstmt.setString(cnt++, "%Y-%m-%d %H:%i");
					pstmt.setString(cnt++, "%Y-%m-%d %H:%i");
				}else if(progress_ago.isEmpty()){
				sql = "update lime_reply a , lime_question b set a.member_id =? , a.member_name = ?, a.reply_day = date_format(now(),?) , " +
						 " a.detailDivision =?  , a.reply_keyword = ? ,a.reply_priority = ?, a.reply_method=? , a.reply_term= ?, a.reply_content = ? , b.question_progress = ? , b.question_division = ? , b.question_manager = ? " +
						 " where a.question_id = b.question_id and a.reply_id = ?  ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(cnt++, lime.getMember_id());
				pstmt.setString(cnt++, lime.getMember_name());
				pstmt.setString(cnt++, "%Y-%m-%d %H:%i");
				}else if(progress.equals("작업완료")){
					sql = "update lime_reply a , lime_question b set a.member_id =? , a.member_name = ?, a.reply_comday = date_format(now(),?) , " +
							" a.detailDivision =?  , a.reply_keyword = ? ,a.reply_priority = ?, a.reply_method=? , a.reply_term= ?, a.reply_content = ? , b.question_progress = ? , b.question_division = ? , b.question_manager = ? " +
							" where a.question_id = b.question_id and a.reply_id = ?  ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(cnt++, lime.getMember_id());
					pstmt.setString(cnt++, lime.getMember_name());
					pstmt.setString(cnt++, "%Y-%m-%d %H:%i");
				}else{
					sql = "update lime_reply a , lime_question b set a.member_id =? , a.member_name = ?,  " +
							" a.detailDivision =?  , a.reply_keyword = ? ,a.reply_priority = ?, a.reply_method=? , a.reply_term= ?, a.reply_content = ? , b.question_progress = ? , b.question_division = ? , b.question_manager = ? " +
							" where a.question_id = b.question_id and a.reply_id = ?  ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(cnt++, lime.getMember_id());
					pstmt.setString(cnt++, lime.getMember_name());
				}
			
				//PreparedStatement 객체 생성
				
				//?에 데이터 매칭
				pstmt.setString(cnt++, lime.getDetailDivision());
				pstmt.setString(cnt++, lime.getReply_keyword());
				pstmt.setString(cnt++, priority);
				pstmt.setString(cnt++, lime.getReply_method());
				pstmt.setString(cnt++, lime.getReply_term());
				pstmt.setString(cnt++, lime.getReply_content());
				pstmt.setString(cnt++, progress);
				pstmt.setString(cnt++, lime.getQuestion_division());
				pstmt.setString(cnt++, lime.getQuestion_manager());
				pstmt.setInt(cnt++, lime.getReply_id());

				pstmt.executeUpdate();
				//SQL문 반영
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		//예약 후 답변
		public void question_update(Lime lime) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			int cnt = 1;
			try{
				
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				
				sql = "update lime_question set member_email = ?, member_tel = ?, member_ptel =?, question_division = ? , question_attach =? , question_filename =? , question_content = ? "
						+ "where question_id = ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 매칭
				pstmt.setString(cnt++, lime.getMember_email());
				pstmt.setString(cnt++, lime.getMember_tel());
				pstmt.setString(cnt++, lime.getMember_ptel());
				pstmt.setString(cnt++, lime.getQuestion_division());
				pstmt.setString(cnt++, lime.getQuestion_attach());
				pstmt.setString(cnt++, lime.getQuestion_filename());
				pstmt.setString(cnt++, lime.getQuestion_content());
				pstmt.setInt(cnt++, lime.getQuestion_id());
				
				pstmt.executeUpdate();
				//SQL문 반영
				
			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}
		
		//예약 목록 출력 페이지네이션
		public List<Lime> translate(Lime lime, int pageN , int pageSize)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Lime> list = null;
			String sql = "";
			String keyField = lime.getKeyField();
			String keyWord = lime.getKeyWord();
			String sort = lime.getSort();

			if(sort ==null){
				sort = "translation_id";
			}

			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				if(keyField != null && !keyWord.equals("")){
					sql= "SELECT * FROM LIME_TRANSLATION "
							+ "WHERE "+keyField.trim()+" LIKE '%"+keyWord.trim()+"%' ORDER BY "+sort+" desc LIMIT ?,? ";
				}else{
					sql = "select  * FROM LIME_TRANSLATION "
							+ " order by "+sort+" desc limit ?,? ";
				}
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pageN);
				pstmt.setInt(2, pageSize);
				//PreparedStatement 객체 생성
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<Lime>();

				while(rs.next()){
					Lime resvList = new Lime();

					int translation_id = rs.getInt("translation_id");
					String translation_korean = rs.getString("translation_korean");
					String translation_manager_korean = rs.getString("translation_manager_korean");
					String translation_english = rs.getString("translation_english");
					String translation_manager_english = rs.getString("translation_manager_english");
					String translation_chinese = rs.getString("translation_chinese");
					String translation_manager_chinese = rs.getString("translation_manager_chinese");
					String translation_day = rs.getString("translation_day");
					String translation_cday = rs.getString("translation_cday");
					String translation_type = rs.getString("translation_type");


					resvList.setTranslation_id(translation_id);
					resvList.setTranslation_korean(translation_korean);
					resvList.setTranslation_manager_korean(translation_manager_korean);
					resvList.setTranslation_english(translation_english);
					resvList.setTranslation_manager_english(translation_manager_english);
					resvList.setTranslation_chinese(translation_chinese);
					resvList.setTranslation_manager_chinese(translation_manager_chinese);
					resvList.setTranslation_day(translation_day);
					resvList.setTranslation_cday(translation_cday);
					resvList.setTranslation_type(translation_type);


					list.add(resvList);
				}


			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return list;
		}


		//예약 총 갯수 (페이지네이션 시 사용)
		public int getCount_translation(Lime lime)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int count = 0;
			String sql = "";
			String keyField = lime.getKeyField();
			String keyWord = lime.getKeyWord();

			try{

				//커넥션풀로부터 커넥션 객체 반환
				conn = getConnection();
				//SQL문 작성
				if(keyField != null && keyWord !=null){
					sql = "select count(*) from lime_translation where "+keyField.trim()+" like '%"+keyWord.trim()+"%' ";
				}else{
					sql = "select count(*) from lime_translation";
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



		//회원가입
		public void Translation_insert(Lime lime) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			int cnt=1;
			try{
				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();

				sql = "insert into Lime_translation (translation_korean,translation_manager_korean,translation_day) values (?,?,date_format(now(),?))";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);

				//?에 데이터 매칭
				pstmt.setString(cnt++, lime.getTranslation_korean());
				System.out.println("dao :" +lime.getTranslation_korean());
				pstmt.setString(cnt++, lime.getTranslation_manager_korean());
				System.out.println("dao :" +lime.getTranslation_manager_korean());

				pstmt.setString(cnt++, "%Y-%m-%d %H:%i");
				pstmt.executeUpdate();
				//SQL문 반영

			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}




		public void Translation_update(Lime lime) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			int cnt=1;

			try{

				//커넥션풀로부터 커넥션을 할당
				conn = getConnection();
				if(!lime.getTranslation_chinese().isEmpty()&&!lime.getTranslation_english().isEmpty()){
					sql = "update Lime_translation set translation_chinese=?,translation_manager_chinese=?, translation_english=? ,translation_manager_english=?,translation_cday = date_format(now(),?) ,translation_type=? where translation_id = ? " ;
					pstmt = conn.prepareStatement(sql);
					//PreparedStatement 객체 생성
					//?에 데이터 매칭
					pstmt.setString(cnt++, lime.getTranslation_chinese());
					System.out.println("dao C :"+lime.getTranslation_manager_chinese());
					pstmt.setString(cnt++, lime.getTranslation_manager_chinese());
					pstmt.setString(cnt++, lime.getTranslation_english());
					System.out.println("dao E :"+lime.getTranslation_manager_english());
					pstmt.setString(cnt++, lime.getTranslation_manager_english());
					pstmt.setString(cnt++, "%Y-%m-%d %H:%i");
					pstmt.setString(cnt++, "0");
					pstmt.setInt(cnt++, lime.getTranslation_id());
					//SQL문 반영
				}else{
					sql = "update Lime_translation set translation_chinese=?,translation_manager_chinese=?, translation_english=? ,translation_manager_english=? where translation_id = ? " ;
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(cnt++, lime.getTranslation_chinese());
					pstmt.setString(cnt++, lime.getTranslation_manager_chinese());
					pstmt.setString(cnt++, lime.getTranslation_english());
					pstmt.setString(cnt++, lime.getTranslation_manager_english());
					pstmt.setInt(cnt++, lime.getTranslation_id());
				}
				pstmt.executeUpdate();

			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(null, pstmt, conn);
			}
		}




		public Lime translate_ago(int translation_id)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Lime lime = new Lime();
			String sql = "";

			try{
				//커넥션풀로부터 커넥션을 반환
				conn = getConnection();
				//SQL문 작성
				sql = "select translation_chinese,translation_english FROM LIME_TRANSLATION "
						+ " where translation_id = ? ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,translation_id);

				//PreparedStatement 객체 생성
				//SQL문 반영해서 결과행을 ResultSet 담음
				rs = pstmt.executeQuery();

				while(rs.next()){

					String translation_chinese = rs.getString("translation_chinese");
					String translation_english = rs.getString("translation_english");

					lime.setTranslation_chinese(translation_chinese);
					lime.setTranslation_english(translation_english);
				}


			}catch(Exception e){
				throw new Exception(e);
			}finally{
				executeClose(rs, pstmt, conn);
			}
			return lime;
		}
		
}
