/**
* 
 * @author Martha Fernandez
 */
class CheckingAccount {
    private double balance;
    private double serviceCharges;

    public CheckingAccount(double initialBalance) {
        this.balance = initialBalance;
        this.serviceCharges = 0;
    }

    public enum TransactionType {
        CHECK,
        DEPOSIT
    }

    public void updateBalance(double amount, TransactionType type) {
        if (type == TransactionType.CHECK) {
            this.balance -= amount;
        } else if (type == TransactionType.DEPOSIT) {
            this.balance += amount;
        }
    }

    public void addServiceCharge(double charge) {
        this.serviceCharges += charge;
    }

    public double getBalance() {
        return balance;
    }

    public double getServiceCharges() {
        return serviceCharges;
    }
}