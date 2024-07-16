/**
 * 
 * @author Martha Fernandez
 */
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class Main {
    private static CheckingAccount account;
    private static DecimalFormat currencyFormat;
    private static boolean firstServiceChargeApplied = false;

    public static void main(String[] args) {
        currencyFormat = new DecimalFormat("#,##0.00; (#,##0.00)");

        // Initialize the account with the initial balance
        initializeAccount();

        int transactionCode;
        do {
            transactionCode = getTransactionCode();

            switch (transactionCode) {
                case 1:
                    processCheckTransaction(getTransactionAmount());
                    break;
                case 2:
                    processDepositTransaction(getTransactionAmount());
                    break;
                case 0:
                    displayFinalAccountSummary();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid Choice. Please enter a valid transaction code.");
            }

        } while (transactionCode != 0);
    }

    private static void initializeAccount() {
        double initialBalance = getInitialBalanceFromUser();
        account = new CheckingAccount(initialBalance);
    }

    private static double getInitialBalanceFromUser() {
        double balance = 0.0;
        try {
            String input = JOptionPane.showInputDialog("Enter your initial balance: ");
            balance = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            System.exit(1);
        }
        return balance;
    }

    private static int getTransactionCode() {
        int code = -1;
        try {
            String input = JOptionPane.showInputDialog(
                    "Enter the transaction code:\n1) Check \n2) Deposit \n0) Exit the program");
            code = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
        return code;
    }

    private static double getTransactionAmount() {
        double amount = 0.0;
        try {
            String input = JOptionPane.showInputDialog("Enter the transaction amount: ");
            amount = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
        return amount;
    }

    private static void processCheckTransaction(double amount) {
        account.updateBalance(amount, CheckingAccount.TransactionType.CHECK);
        account.addServiceCharge(0.15);
        String message = createTransactionMessage("Check", amount, 0.15);
        applyAdditionalServiceCharges();
        message += "\nTotal Service Charge: $" + currencyFormat.format(account.getServiceCharges());
        JOptionPane.showMessageDialog(null, message);
    }

    private static void processDepositTransaction(double amount) {
        account.updateBalance(amount, CheckingAccount.TransactionType.DEPOSIT);
        account.addServiceCharge(0.10);
        String message = createTransactionMessage("Deposit", amount, 0.10);
        applyAdditionalServiceCharges();
        message += "Total Service Charge: $" + currencyFormat.format(account.getServiceCharges());
        JOptionPane.showMessageDialog(null, message);
    }

    private static String createTransactionMessage(String transactionType, double amount, double serviceCharge) {
        return "Transaction: " + transactionType + " in Amount of $" + amount + "\n" +
                "Current Balance: $" + account.getBalance() + "\n" +
                "Service Charge: " + transactionType + " --- charge $" + serviceCharge + "\n";
    }

    private static void applyAdditionalServiceCharges() {
        if (account.getBalance() < 500 && !firstServiceChargeApplied) {
            account.addServiceCharge(5.00);
            firstServiceChargeApplied = true;
        }

        if (account.getBalance() < 50) {
            JOptionPane.showMessageDialog(null, "Warning: Balance below $50.");
        }

        if (account.getBalance() < 0) {
            account.addServiceCharge(10.00);
        }
    }

    private static void displayFinalAccountSummary() {
        JOptionPane.showMessageDialog(null, "Transaction: End\n" +
                "Current Balance: $" + account.getBalance() + "\n" +
                "Total Service Charge: $" + currencyFormat.format(account.getServiceCharges()) + "\n" +
                "Final Balance: $" + currencyFormat.format(account.getBalance() - account.getServiceCharges()));
    }
}

