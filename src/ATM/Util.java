package ATM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Util {
	
	private static Util instance;
	private static Scanner sc = new Scanner(System.in);
	private Random rd = new Random();
	private String path = System.getProperty("user.dir");
	private final String CUR_PATH = path + "\\src\\ATM\\";

	public static Util getInstance() {
		if(instance == null) {
			instance = new Util();
		}
		return instance;
	}
	
	private Util(){
		//SetData(controller);
	}
	
	public int getIntValue(String str, int start , int end) {
		int val = 0;
		while(true) {
			System.out.print(str);
			try {
				val = sc.nextInt();
				sc.nextLine();
				if(val < start || val > end) {
					continue;
				}
			}catch(InputMismatchException e) {
				System.out.println("정수를 입력하세요.");
				sc.nextLine();
				continue;
			}catch(Exception e) {
				
				e.printStackTrace();
			}
			
			break;
		}
		return val;
	}
	
	public String getStrValue(String str) {
		String val = "";
		while(true) {
			System.out.print(str);
			try {
				val = sc.nextLine();
				if(val.isEmpty() || val.isBlank()) {
					System.out.println("공백을 입력할 수 없습니다.");
				}
			}catch(InputMismatchException e) {
				System.out.println("문자열을 입력하세요.");
				continue;
			}catch(Exception e) {
				
				e.printStackTrace();
			}
			
			break;
		}
		return val;
	}
	private void SetData(BankController controller) {
		String userdata = "1001/test01/pw1/김철수\n"
		+ "1002/test02/pw2/이영희\n"
		+ "1003/test03/pw3/신민수\n"
		+ "1004/test04/pw4/최상민";
		
		
		String accountdata = "test01/1111-1111-1111/8000\n"
		+ "test02/2222-2222-2222/5000\n"
		+ "test01/3333-3333-3333/11000\n"
		+ "test03/4444-4444-4444/9000\n"
		+ "test01/5555-5555-5555/5400\n"
		+ "test02/6666-6666-6666/1000\n"
		+ "test03/7777-7777-7777/1000\n"
		+ "test04/8888-8888-8888/1000";
		
		controller.accDAO.ParseData(accountdata);
		controller.clientDAO.ParseData(userdata);
		
	}
	
	public void FileSave(BankController controller) {
		if(controller.clientDAO.getCnt() == 0 ) {
			System.out.println("저장할 회원 정보가 없습니다.");
		}else {
			int result = ListSave("clientListFlie01.txt",controller);
			if(result == 1) {
				System.out.println(" 회원 정보가 txt파일로 저장되었습니다.");
			}
		}
		
		if(controller.accDAO.getCnt() == 0 ) {
			System.out.println("저장할 계좌 정보가 없습니다.");
		}else {
			int result = ListSave("accListFlie01.txt",controller);
			if(result == 1) {
				System.out.println("성적 정보가 txt파일로 저장되었습니다.");
			}
		}
	}
	
    private int ListSave(String fileName, BankController controller) {
		Object[] itsList = null;
		if(fileName.equals("clientListFlie01.txt")) {
			itsList = controller.clientDAO.getClientList();
		}else if (fileName.equals("accListFlie01.txt")){
			itsList = controller.accDAO.getAccList();
		}
		
		String data ="";
		for(int i = 0; i < itsList.length; i++) {
			data += itsList[i]+"\n";
 		}
		
		File file = new File(CUR_PATH + fileName);
		
		try(FileWriter fw = new FileWriter(file)){
			fw.write(data);
		}catch(IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
    public void FileLoad(BankController controller) {
		controller.clientDAO.Reset();
		controller.accDAO.Reset();
		
		int result = LoadClientList(controller);
		if(result == 1) {
			System.out.println("회원 정보가 로드되었습니다.");
		}
		result = LoadAccList(controller);
		if(result == 1) {
			System.out.println("계좌 정보가 로드되었습니다.");
		}
	}
	
	private int LoadClientList(BankController controller) {
		
		String fileName = "clientListFlie01.txt";
		File file = new File(CUR_PATH + fileName);
		
		try(FileReader fr = new FileReader(file)){
			BufferedReader br = new BufferedReader(fr);
			
			String data = "";
			String next = null;
			while((next = br.readLine())!= null) {
				data += next+"\n";
			}
			controller.clientDAO.ParseData(data);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
			// TODO: handle exception
		}
		return 1;
	}
	
	private int LoadAccList(BankController controller) {
		String fileName = "accListFlie01.txt";
		File file = new File(CUR_PATH + fileName);
		
		try(FileReader fr = new FileReader(file)){
			BufferedReader br = new BufferedReader(fr);
			
			String data = "";
			String next = null;
			while((next = br.readLine())!= null) {
				data += next+"\n";
			}
			controller.accDAO.ParseData(data);
				
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
			// TODO: handle exception
		}
		return 1;
	}
	
	
}
