package ATM;

public class AccountDAO {

	private Account[] accList;
	private int cnt;
	
	private final int accNumberLength = 14;
	
	
	
	public int getCnt() {
		return cnt;
	}

	public Account[] getAccList() {
		return accList;
	}
	public AccountDAO(){
		Init();
	}
	private void Init(){
		Reset();
	}
	
	public void Reset() {
		cnt = 0;
		accList = null;
	}
	public void parseData(String data) {
		
		String[] accArr = data.split("\n");
		
		accList = new Account[accArr.length];
		cnt = accArr.length;
		for(int i = 0; i < accArr.length; i++) {
			String[] oneAcc = accArr[i].split("/");
			Account a = new Account(oneAcc[0],oneAcc[1], Integer.parseInt(oneAcc[2]));
			accList[i] = a;
		}
		
	}
	
	void printAccList() {
		if(cnt == 0) {
			System.out.println("계좌 정보가 없습니다.");
			return;
		}
		
		for(Account a : accList) {
			System.out.println(a);
		}
	}
	
	
	//회원 아이디를 받아 해당 아이디를 가지고있는 계좌 삭제
	
	public void removeClientById(String clientId) {
		int removeCnt =  getCntById(clientId);
		if(removeCnt == 0) {
			System.out.println("계좌가 없습니다.");
			return;
		}
		
		if(cnt == removeCnt) {
			accList = null;
			cnt = 0;
		}else {
			for(int i = 0; i < accList.length; i++) {
				if( accList[i].getClientId().equals(clientId)) {
					accList[i] = null;
				}
			}
			
			Account[] temp = accList;
			accList = new Account[cnt - removeCnt];
			int idx = 0;
			for (int i = 0; i < temp.length; i++) {
				if(temp[i] != null) {
					accList[idx++] = temp[i];
				}
			}
			cnt -=removeCnt;
		}
		System.out.println("연결된 계좌가 삭제되었습니다.");
	}
	
	//회원 아이디를 받아서 보유 계좌 숫자 반환
    private int getCntById(String clientId) {
		if (cnt == 0) {
			return 0;
		}
		int accCnt = 0;
		for(int i = 0; i < accList.length; i++) {
			if( accList[i].getClientId().equals(clientId)) {
				accCnt++;
			}
		}
		return accCnt;
	}
	
	
	/// ======== 사용자 메뉴 ======== 
	
	//사용자메뉴
	// 계좌추가
    public void register(BankController controller) {
		if(getCntById(controller.log) == 3) {
			System.out.println("최대 개설 가능한 계좌 수(3개)를 초과했습니다.");
			return;
		}
		while (true) {
			System.out.println("== 계좌 추가 ==");
			String newAccNum = controller.util.getStrValue("계좌번호 입력");
			if(!isVailedNumber(newAccNum)) {
				continue;
			}
			if(isExist(newAccNum) ) {
				System.out.println("계좌번호 중복");
				continue;
			}
			// 중복확인 기능 추가
			int money = controller.util.getIntValue("최초 입금액을 입력하세요.", 0, Integer.MAX_VALUE);
			addList(new Account(controller.log, newAccNum, money));
			System.out.println("계좌 개설이 완료되었습니다.");
			
			break;
		}
	}
	
	//계좌 번호가 포맷에 맞는지 검사하는 메서드
    private boolean isVailedNumber(String accNumber) {
		if(accNumber.length() != accNumberLength) {
			System.out.println("계좌 번호의 길이가 맞지 않습니다.");
			return false;
		}
		for(int i= 0; i < accNumber.length(); i++) {
			char c = accNumber.charAt(i);
			if((i == 4 || i == 9)) {
				if (c != '-') {
					System.out.println("4번째 번호 사이에 - 를 입력해주세요.");
					return false;
				}
			}else {
				if(c > 57 || c <48) {
					System.out.println("계좌 번호에 정수 이외의 것을 입력하면 안 됩니다.");
					return false;
				}
			}
		}
		
		return true;
	}
	
	//계좌 추가 메서드
    private void addList(Account a) {
		if (cnt == 0) {
			accList = new Account[cnt + 1];
			accList[cnt] = a;
			cnt = 1;
		} else {
			Account[] temp = accList;
			accList = new Account[cnt + 1];
			int idx = 0;
			for (int i = 0; i < temp.length; i++) {
				accList[idx++] = temp[i];
			}
			accList[cnt] = a;
			cnt += 1;
		}
	}
	
	//계좌 삭제
	//보유 계좌가 있을 시 선택하여 계좌 삭제
    public void removeAcc(BankController controller) {
		if(getCntById(controller.log) == 0) {
			System.out.println("보유 계좌가 없습니다.");
			return;
		}
		printAccListById(controller.log);
		Account[] myAccs = getAccListById(controller.log);
		while(true) {
			int idx = controller.util.getIntValue("삭제할 계좌의 번호 선택", 1, myAccs.length)-1;
			
			removeAccByObj(myAccs[idx]);
			System.out.println("삭제가 완료되었습니다.");
			break;
		}
	}
	
	//아이디를 가져와서 일치하는 계좌 출력
    public void printAccListById(String id) {
		
		int num = 0;
		for(int i = 0; i < accList.length; i++) {
			if(accList[i].getClientId().equals(id)) {
				System.out.println("["+((num++)+1)+"] "+accList[i]);
			}
		}
	}
	
	//아이디를 가져와서 일치하는 계좌 배열 출력
    private Account[] getAccListById(String id) {
		Account[] arr = null;
		int num = 0;
		for(int i = 0; i < accList.length; i++) {
			if(accList[i].getClientId().equals(id)) {
				num++;
			}
		}
		if(num > 0) {
			arr = new Account[num];
			int idx = 0;
			for(int i = 0; i < accList.length; i++) {
				if(accList[i].getClientId().equals(id)) {
					arr[idx++] = accList[i];
				}
			}
		}
		return arr;
	}
	
	//계좌 삭제 메서드
    private void removeAccByObj(Account a) {
		if (cnt == 1) {
			accList = null;
			cnt = 0;
		} else {
			for (int i = 0; i < accList.length; i++) {
				if(accList[i].getAccNumber().equals(a.getAccNumber())) {
					accList[i] = null;
				}
			}
			Account[] temp = accList;
			accList = new Account[cnt - 1];
			int idx = 0;
			for (int i = 0; i < temp.length; i++) {
				if(temp[i] != null) {
					accList[idx++] = temp[i];
				}
			}
			cnt -= 1;
		}
	}
	
	//입금 메서드
    public void deposit(BankController controller) {
		System.out.println(getCntById(controller.log));
		if(getCntById(controller.log) == 0) {
			System.out.println("보유 계좌가 없습니다.");
			return;
		}
		printAccListById(controller.log);
		Account[] myAccs = getAccListById(controller.log);
		while(true) {
			int idx = controller.util.getIntValue("입금 계좌의 번호 선택", 1, myAccs.length)-1;
			int money = controller.util.getIntValue("입금액을 입력하세요.", 100, Integer.MAX_VALUE);
			
			depositByAccnum(myAccs[idx].getAccNumber() , money);
			System.out.println("입금이 완료되었습니다.");
			
			// 확인용 프린트 삭제예정
			printAccListById(controller.log);
			break;
		}
	}
	
	// 선택 계좌에 입금하기
    private void depositByAccnum(String accNumber, int money) {
		for (int i = 0; i < accList.length; i++) {
			if(accList[i].getAccNumber().equals(accNumber)) {
				accList[i].setMoney(money + accList[i].getMoney());
			}
		}
	}
	
	//계좌이체 
    public void transfer(BankController controller ) {
		if(getCntById(controller.log) == 0) {
			System.out.println("보유 계좌가 없습니다.");
			return;
		}
		printAccListById(controller.log);
		Account[] myAccs = getAccListById(controller.log);
		while(true) {
			int idx = controller.util.getIntValue("입금 계좌의 번호 선택", 1, myAccs.length)-1;
			int money = controller.util.getIntValue("입금액을 입력하세요.", 100, Integer.MAX_VALUE);
			
			String yourAccNum = controller.util.getStrValue("계좌번호 입력");
			if(!isVailedNumber(yourAccNum)) {
				continue;
			}
			if(!isExist(yourAccNum) ) {
				System.out.println("계좌번호가 존재하지 않습니다.");
				continue;
			}
			if(yourAccNum.equals(myAccs[idx].getAccNumber())) {
				System.out.println("출금 계좌에 이체할 수 없습니다.");
				continue;
			}
			
			depositByAccnum(yourAccNum , money);
			myAccs[idx].setMoney(myAccs[idx].getMoney() - money);
			
			System.out.println("이체가 완료되었습니다.");
			
			// 확인용 프린트 삭제예정
			printAccListById(controller.log);
			break;
		}
	}
	
	//계좌번호로 계좌가 존재하는지 확인하는 메서드.
    private boolean isExist(String accNumber) {
		for (int i = 0; i < accList.length; i++) {
			if(accList[i].getAccNumber().equals(accNumber)) {
				return true;
			}
		}
		return false;
	}
	
	//내 계좌에서 출금 메서드
    public void withdraw(BankController controller) {
		if(getCntById(controller.log) == 0) {
			System.out.println("보유 계좌가 없습니다.");
			return;
		}
		printAccListById(controller.log);
		Account[] myAccs = getAccListById(controller.log);
		while(true) {
			int idx = controller.util.getIntValue("출금 계좌의 번호 선택", 1, myAccs.length)-1;
			int money = controller.util.getIntValue("출금 액을 입력하세요.", 100, Integer.MAX_VALUE);
			if(money > myAccs[idx].getMoney()) {
				System.out.println("잔액이 부족합니다.");
				continue;
			}
			myAccs[idx].setMoney(myAccs[idx].getMoney() - money);
			System.out.println("출금이 완료되었습니다.");
			
			// 확인용 프린트 삭제예정
			printAccListById(controller.log);
			break;
		}
	}
	
	
}
