package kr.guestbook.domain;

// 자동입력방지 숫자 4자리
public class Random {
	
	
	//싱글턴 패턴
	private static Random instance = new Random();

	public static Random getInstance(){
		return instance;
	}

	private Random(){}
	
	public int Random(){
		
		double rndValue = Math.random();
		int intValue = (int)(rndValue * 9000) + 1000;
		
		return intValue;
		
	}

}
