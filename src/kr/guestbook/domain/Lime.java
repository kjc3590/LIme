package kr.guestbook.domain;

public class Lime {

	private String member_id;
	private String member_pw;
	private String member_name;
	private int company_pk;
	private String company_name;
	private String member_ptel;
	private String member_tel;
	private String member_email;
	private String member_type;
	private String member_day;
	
	private int question_id;
	private String question_day;
	private String question_division;
	private String question_content;
	private String question_attach;
	private String question_filename;
	private String question_progress;
	private String question_manager;

	private int reply_id ;
	private String reply_day;
	private String reply_keyword;
	private String reply_priority;
	private String reply_method;
	private String reply_term;
	private String reply_content;
	private String detailDivision;
	
	private String KeyWord;
	private String KeyField;
	private String sort;
	
	private int translation_id;
	private String translation_korean;
	private String translation_manager_korean;
	private String translation_english;
	private String translation_manager_english;
	private String translation_chinese;
	private String translation_manager_chinese;
	private String translation_day;
	private String translation_cday;
	private String translation_type;
	
	   public boolean isCheckedPasswd(String userPassword) {
		   if(member_pw.equals(userPassword)) {
			   return true;
		   }
		   return false;
	   }
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public int getCompany_pk() {
		return company_pk;
	}
	public void setCompany_pk(int company_pk) {
		this.company_pk = company_pk;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getMember_ptel() {
		return member_ptel;
	}
	public void setMember_ptel(String member_ptel) {
		this.member_ptel = member_ptel;
	}
	public String getMember_tel() {
		return member_tel;
	}
	public void setMember_tel(String member_tel) {
		this.member_tel = member_tel;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public String getMember_day() {
		return member_day;
	}
	public void setMember_day(String member_day) {
		this.member_day = member_day;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public String getQuestion_day() {
		return question_day;
	}
	public void setQuestion_day(String question_day) {
		this.question_day = question_day;
	}
	public String getQuestion_division() {
		return question_division;
	}
	public void setQuestion_division(String question_division) {
		this.question_division = question_division;
	}
	public String getQuestion_content() {
		return question_content;
	}
	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}
	public String getQuestion_attach() {
		return question_attach;
	}
	public void setQuestion_attach(String question_attach) {
		this.question_attach = question_attach;
	}
	public String getQuestion_filename() {
		return question_filename;
	}
	public void setQuestion_filename(String question_filename) {
		this.question_filename = question_filename;
	}

	public String getQuestion_progress() {
		return question_progress;
	}

	public void setQuestion_progress(String question_progress) {
		this.question_progress = question_progress;
	}

	public String getQuestion_manager() {
		return question_manager;
	}

	public void setQuestion_manager(String question_manager) {
		this.question_manager = question_manager;
	}
	public String getReply_day() {
		return reply_day;
	}

	public void setReply_day(String reply_day) {
		this.reply_day = reply_day;
	}

	public String getReply_keyword() {
		return reply_keyword;
	}

	public void setReply_keyword(String reply_keyword) {
		this.reply_keyword = reply_keyword;
	}

	public String getReply_priority() {
		return reply_priority;
	}

	public void setReply_priority(String reply_priority) {
		this.reply_priority = reply_priority;
	}
	public String getReply_method() {
		return reply_method;
	}

	public void setReply_method(String reply_method) {
		this.reply_method = reply_method;
	}

	public String getReply_term() {
		return reply_term;
	}

	public void setReply_term(String reply_term) {
		this.reply_term = reply_term;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public String getDetailDivision() {
		return detailDivision;
	}

	public void setDetailDivision(String detailDivision) {
		this.detailDivision = detailDivision;
	}

	public int getReply_id() {
		return reply_id;
	}

	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}

	public String getKeyWord() {
		return KeyWord;
	}

	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}

	public String getKeyField() {
		return KeyField;
	}

	public void setKeyField(String keyField) {
		KeyField = keyField;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	public int getTranslation_id() {
		return translation_id;
	}

	public void setTranslation_id(int translation_id) {
		this.translation_id = translation_id;
	}

	public String getTranslation_korean() {
		return translation_korean;
	}

	public void setTranslation_korean(String translation_korean) {
		this.translation_korean = translation_korean;
	}

	public String getTranslation_manager_korean() {
		return translation_manager_korean;
	}

	public void setTranslation_manager_korean(String translation_manager_korean) {
		this.translation_manager_korean = translation_manager_korean;
	}

	public String getTranslation_english() {
		return translation_english;
	}

	public void setTranslation_english(String translation_english) {
		this.translation_english = translation_english;
	}

	public String getTranslation_manager_english() {
		return translation_manager_english;
	}

	public void setTranslation_manager_english(String translation_manager_english) {
		this.translation_manager_english = translation_manager_english;
	}

	public String getTranslation_chinese() {
		return translation_chinese;
	}

	public void setTranslation_chinese(String translation_chinese) {
		this.translation_chinese = translation_chinese;
	}

	public String getTranslation_manager_chinese() {
		return translation_manager_chinese;
	}

	public void setTranslation_manager_chinese(String translation_manager_chinese) {
		this.translation_manager_chinese = translation_manager_chinese;
	}

	public String getTranslation_day() {
		return translation_day;
	}

	public void setTranslation_day(String translation_day) {
		this.translation_day = translation_day;
	}

	public String getTranslation_cday() {
		return translation_cday;
	}

	public void setTranslation_cday(String translation_cday) {
		this.translation_cday = translation_cday;
	}

	public String getTranslation_type() {
		return translation_type;
	}

	public void setTranslation_type(String translation_type) {
		this.translation_type = translation_type;
	}

	

}
