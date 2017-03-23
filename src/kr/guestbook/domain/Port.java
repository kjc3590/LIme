package kr.guestbook.domain;

//자바빈
public class Port {
	//프로퍼티
	private int src;
	private String name;
	private String column1;
	
	private String id;
	private String password;
	
	private int resv_id;
	private String division;
	private String b_name;
	private String resv_name;
	private String tel;
	private String phonetel;
	private String email;
	private String address;
	private String title;
	private String attach;
	private String file_name;
	private String content;
	private String keyField;
	private String keyWord;
	private String day;
	private int rnd;
	private int hit;
	private String sort;
	
	   public boolean isCheckedPasswd(String userPassword) {
		   if(password.equals(userPassword)) {
			   return true;
		   }
		   return false;
	   }
	
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getResv_name() {
		return resv_name;
	}
	public void setResv_name(String resv_name) {
		this.resv_name = resv_name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhonetel() {
		return phonetel;
	}
	public void setPhonetel(String phonetel) {
		this.phonetel = phonetel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getSrc() {
		return src;
	}
	public void setSrc(int src) {
		this.src = src;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumn1() {
		return column1;
	}
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
	public int getRnd() {
		return rnd;
	}
	public void setRnd(int rnd) {
		this.rnd = rnd;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getResv_id() {
		return resv_id;
	}
	public void setResv_id(int resv_id) {
		this.resv_id = resv_id;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}
