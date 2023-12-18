package ATM;

public class BankController {

	public AccountDAO accDAO;
	public ClientDAO clientDAO;
	public Util util;
	public String log = null;
	//관리자
	//[1] 관리자 [2] 사용자 [0] 종료
	
	//회원 수정: 회원 아이디 검색
	//[1] 회원목록 [2] 회원수정 [3] 회원 삭제 [4] 데이터 저장 [5] 데이터 불러오기 
	
	//[1] 회원가입 [2] 로그인 [0] 뒤로가기
	
	//[1] 계좌추가 [2] 계좌삭제 [3] 입금 [4] 출금 [5] 이체 [6] 탈퇴 [7] 마이페이지 [0] 로그아웃
	
	void Init() {
		accDAO = new AccountDAO();
		clientDAO = new ClientDAO();
		log = null;
		util = Util.getInstance();
	}
	
	void Run() {
		Init();
		
		while(true) {
			
			int start = 0;
			int end = 0;
			int num = 1;
			if(log == null) {
				System.out.println("===== [우리 은행 ATM] =====");
				System.out.println("["+ num++ +"] 관리자");
				System.out.println("["+ num++ +"] 사용자");
				System.out.println("[0] 종료");
			}else {
				if(log.equals("admin")) {
					System.out.println("===== [우리 은행 관리자] =====");
					System.out.println("["+ num++ +"] 회원목록");
					System.out.println("["+ num++ +"] 회원수정");
					System.out.println("["+ num++ +"] 회원 삭제");
					System.out.println("["+ num++ +"] 회원데이터 저장");
					System.out.println("["+ num++ +"] 회원데이터 불러오기 ");
					System.out.println("[0] 뒤로가기");
				}else if(log.equals("user")){
					System.out.println("===== [우리 은행 사용자] =====");
					System.out.println("["+ num++ +"] 회원가입");
					System.out.println("["+ num++ +"] 로그인");
					System.out.println("[0] 뒤로가기");
				}else {
					System.out.println("===== [우리 은행 ATM] =====");
					System.out.println("["+ num++ +"] 계좌추가");
					System.out.println("["+ num++ +"] 계좌삭제");
					System.out.println("["+ num++ +"] 입금");
					System.out.println("["+ num++ +"] 출금");
					System.out.println("["+ num++ +"] 이체");
					System.out.println("["+ num++ +"] 탈퇴");
					System.out.println("["+ num++ +"] 마이페이지");
					System.out.println("[0] 로그아웃");
				}
			}
			end = (num-1);
			int sel = util.getIntValue(start +" ~ "+ end+"메뉴 선택 >>",start,end);
			
			if(log == null) {
				if(sel == 1) {
					log = "admin";
				}else if(sel ==2) {
					log = "user";
				}else {
					System.out.println("종료합니다.");
					break;
				}
			}else {
				if(log.equals("admin")) {
					if(sel == 1) {
						clientDAO.PrintClientList();
						accDAO.PrintAccList();;
					}else if (sel == 2) {
						clientDAO.UpdateClient(this);
					}else if (sel == 3) {
						clientDAO.RemoveClient(this);
					}else if (sel == 4) {
						util.FileSave(this);
					}else if (sel == 5) {
						util.FileLoad(this);
					}else if (sel == 0){
						log = null;
					}
				}else if (log.equals("user")) {
					if(sel == 1) {
						log = clientDAO.Register(this);
					}else if (sel == 2) {
						log = clientDAO.Login(this);
					}else if (sel == 0){
						log = null;
					}
				} else {
					if(sel == 1) {
						accDAO.Register(this);
					}else if (sel == 2) {
						accDAO.RemoveAcc(this);
					}else if (sel == 3) {
						accDAO.Deposit(this);
					}else if (sel == 4) {
						accDAO.Withdraw(this);
					}else if (sel == 5) {
						accDAO.Transfer(this);
					}else if (sel == 6) {
						log = clientDAO.Quit(this);
					}else if (sel == 7) {
						accDAO.PrintAccListById(log);
					}else if (sel == 0){
						log = null;
					}
				}
			}
		}
	}
}
