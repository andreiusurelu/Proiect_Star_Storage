package shop;

public class Consumer {
    private String username;
    private int balance;

    public Consumer(String newUsername, int newBalance) {
        username = newUsername;
        balance = newBalance;
    }

    public void decrement(int sum) {
        balance -= sum;
    }

    public String getUsername() {
        return username;
    }

    public int getBalance() {
        return balance;
    }
}
