import java.util.*;
import java.io.IOException;
public class Main {
 public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
 ExpenseManager manager = new ExpenseManager("expenses.txt");
 while (true) {
 System.out.println("\n===== PERSONAL EXPENSE TRACKER =====");
 System.out.println("1. Add New Expense");
 System.out.println("2. View All Expenses");
 System.out.println("3. Category Summary");
 System.out.println("4. Generate Summary Report");
 System.out.println("5. Exit");
 System.out.print("Choose option (1-5): ");
 int choice;
 try {
 choice = Integer.parseInt(scanner.nextLine());
 } catch (Exception e) {
 System.out.println("Invalid input! Try again.");
 continue;
 }
 switch (choice) {
 case 1 -> {
 System.out.print("Enter Date (YYYY-MM-DD): ");
 String date = scanner.nextLine();
 System.out.print("Enter Category: ");
 String category = scanner.nextLine();
 System.out.print("Enter Amount: ");
 double amount = Double.parseDouble(scanner.nextLine());
 System.out.print("Enter Description: ");
 String description = scanner.nextLine();
 Expense exp = new Expense(date, category, amount, description);
 try {
 manager.addExpense(exp);
 System.out.println("Expense added successfully!");
 } catch (IOException e) {
 System.out.println("Error writing file!");
 }
 }
 case 2 -> {
 List<Expense> list = manager.getAllExpenses();
 System.out.println("\n===== ALL SAVED EXPENSES =====");
 if (list.isEmpty()) {
 System.out.println("No records found.");
 } else {
 list.forEach(System.out::println);
 }
 }
 case 3 -> {
 System.out.println("\n===== CATEGORY SUMMARY =====");
 Map<String, Double> summary = manager.getCategorySummary();
 if (summary.isEmpty()) {
 System.out.println("No data found.");
 }
 else {
 summary.forEach((cat, total) ->
 System.out.println(cat + " = " + total + " BDT"));
 }
 }
 case 4 -> {
 try {
 manager.generateSummaryReport("summary.txt");
 System.out.println("Report generated as summary.txt");
 } catch (IOException e) {
 System.out.println("Error generating report.");
 }
 }
 case 5 -> {
 System.out.println("Thank you for using Expense Tracker!");
 return;
 }
 default -> System.out.println("Invalid option.");
          }
     }   
   }
}
