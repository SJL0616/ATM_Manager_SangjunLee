package ATM;

public class Account {

	private String clientId;
	private String accNumber;
	private int money;
	// 1클라 - 3계좌 생성 가능
	public Account(String clientId, String accNumber, int money) {
		this.clientId = clientId;
		this.accNumber = accNumber;
		this.money = money;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return clientId +"/"+ accNumber +"/"+ money;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	
}
