package ATM;


public class ClientDAO {

	private Client[] clientList;
	private int cnt = 0;
	
	
	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public Client[] getClientList() {
		return clientList;
	}

	ClientDAO(){
		Reset();
	}
	
	public void Reset() {
		cnt = 0;
		clientList = null;
	}
	
	public void ParseData(String data) {
		String[] clientArr = data.split("\n");

		clientList = new Client[clientArr.length];
		cnt = clientList.length;
		for (int i = 0; i < clientArr.length; i++) {
			String[] oneC = clientArr[i].split("/");
			Client c = new Client(Integer.parseInt(oneC[0]),oneC[1],oneC[2],oneC[3]);
			clientList[i] = c;
		}

	}
	
	public void PrintClientList() {
		if(cnt == 0) {
			System.out.println("사용자 정보가 없습니다.");
			return;
		}
		
		for(Client c : clientList) {
			System.out.println(c);
		}
		
	}
	
	//사용자메뉴
	//로그인
    public String Login(BankController controller) {
		String log = "-1";
		while(true) {
			String id = controller.util.getStrValue("아이디 입력 >>");
			String pw = controller.util.getStrValue("패스워드 입력 >>");
			int idx = -1;
			if((idx = getIdx(id)) == -1){
				System.out.println("아이디 오류");
				continue;
			}
			if(!clientList[idx].getPw().equals(pw)) {
				System.out.println("비밀번호 오류");
				continue;
			}
			
			
			System.out.println(clientList[idx].getName()+ "님 로그인 성공");
			return id;
		}
	}
	
	//사용자메뉴
	//회원가입
	public String Register(BankController controller) {
		String log = null;
		while (true) {
			System.out.println("== 회원 가입 ==");
			String inputId = controller.util.getStrValue("회원 아이디 입력");
			int idx = -1;
			if ((idx = getIdx(inputId)) != -1) {
				System.out.println("아이디 중복입니다.");
				continue;
			}
			String newPw = controller.util.getStrValue("비밀번호 입력");
			String newName = controller.util.getStrValue("이름 입력");
			
			AddList(new Client((1000+(cnt+1)), inputId, newPw, newName));
			System.out.println(newName+ "님");
			System.out.println("회원 가입이 완료되었습니다.");
			return inputId;
		}
		
	}
	
	private void AddList(Client c) {
		if(cnt == 0) {
			clientList = new Client[cnt+1];
			clientList[cnt] = c;
			cnt = 1;
		}else {
			
			Client[] temp = clientList;
			clientList = new Client[cnt + 1];
			int idx = 0;
			for (int i = 0; i < temp.length; i++) {
					clientList[idx++] = temp[i];
			}
			clientList[cnt] = c;
			cnt +=1;
		}
	}
	
	// 회원 수정
	public void UpdateClient(BankController controller) {
		if(cnt == 0 ) {
			System.out.println("사용자 정보가 없습니다.");
			return;
		}
		while(true) {
			
			String inputId = controller.util.getStrValue("회원 아이디 입력");
			int idx = -1;
			if((idx = getIdx(inputId)) == -1) {
				System.out.println("아이디를 찾을 수 없습니다.");
				continue;
			}
			String newPw = controller.util.getStrValue("새 비밀번호 입력");
			String newName = controller.util.getStrValue("새 이름 입력");
			clientList[idx].setPw(newPw);;
			clientList[idx].setName(newName);
			System.out.println("회원 정보가 수정되었습니다.");
			
			break;
		}
	}
	
	//회원 삭제
	public void RemoveClient(BankController controller ) {
		if(cnt == 0 ) {
			System.out.println("사용자 정보가 없습니다.");
			return;
		}
		while(true) {
			
			String inputId = controller.util.getStrValue("회원 아이디 입력");
			int idx = -1;
			if((idx = getIdx(inputId)) == -1) {
				System.out.println("아이디를 찾을 수 없습니다.");
				continue;
			}
			controller.accDAO.RemoveClientById(clientList[idx].getId());
			RemoveClientAt(idx);
			System.out.println("회원 정보가 삭제되었습니다.");
			
			break;
		}
	}
	
	private void RemoveClientAt(int clientIdx) {
		if(cnt == 1) {
			clientList = null;
			cnt = 0;
		}else {
			
			// 연결된 계좌 먼저 삭제.
			clientList[clientIdx] = null;
			
			Client[] temp = clientList;
			clientList = new Client[cnt - 1];
			int idx = 0;
			for (int i = 0; i < temp.length; i++) {
				if(temp[i] != null) {
					clientList[idx++] = temp[i];
				}
			}
			cnt -=1;
		}
	}
	
	private int getIdx(String clientId) {
		if(cnt == 0 ) return -1;
		int idx = -1;
		for(int i = 0; i < clientList.length; i++) {
			if(clientList[i].getId().equals(clientId)) {
				idx = i;
				break;
			}
		}
		return idx;
	}
	
	private boolean IsPwExist(String pw) {
		if(cnt == 0 ) return false;
		for(int i = 0; i < clientList.length; i++) {
			if(clientList[i].getPw().equals(pw)) {
				return true;
			
			}
		}
		return false;
	}
	
	
	//사용자 메뉴
	//비밀번호를 입력받아서 일치하면 탈퇴.
	public String Quit(BankController controller) {
		while(true) {
			String pw = controller.util.getStrValue("패스워드 입력 >>");
			int idx = getIdx(controller.log);
			String name = clientList[idx].getName();
			if(!clientList[idx].getPw().equals(pw)) {
				System.out.println("비밀번호 오류");
				continue;
			}
			controller.accDAO.RemoveClientById(clientList[idx].getId());
			RemoveClientAt(idx);
			System.out.println(name+ "님 회원탈퇴되었습니다.");
			return null;
		}
	}
}
