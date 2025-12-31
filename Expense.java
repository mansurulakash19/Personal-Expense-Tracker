
public class Expense {
 private String date;
 private String category;
 private double amount;
 private String description;
 public Expense(String date, String category, double amount, String description) {
9
 this.date = date;
 this.category = category;
 this.amount = amount;
 this.description = description;
 }

public String getDate() {
 return date;
}
 public String getCategory() {
 return category;
 }
 public double getAmount() {
 return amount;
 }
 public String getDescription() {
 return description;
 }
 @Override
 public String toString() {
 return "Date: " + date +
 " | Category: " + category +
10
 " | Amount: " + amount +
 " | Description: " + description;
}
 public String toFileFormat() {
 return date + "," + category + "," + amount + "," + description;
 }
 public static Expense fromFileFormat(String line) {
 String[] parts = line.split(",");
 if (parts.length != 4) return null;
 return new Expense(
 parts[0].trim(),
 parts[1].trim(),
 Double.parseDouble(parts[2].trim()),
 parts[3].trim()
 );
 }
}
11
ExpenseManager.java
import java.io.*;
import java.util.*;
public class ExpenseManager {
 private final String fileName;
 public ExpenseManager(String fileName) {
 this.fileName = fileName;
 }
 public void addExpense(Expense expense) throws IOException {
 FileWriter writer = new FileWriter(fileName, true);
 writer.write(expense.toFileFormat() + "\n");
 writer.close();
 }
 public List<Expense> getAllExpenses() {
 List<Expense> expenses = new ArrayList<>();
 try {
 File file = new File(fileName);
 if (!file.exists()) return expenses;
 Scanner scanner = new Scanner(file);
 while (scanner.hasNextLine()) {
12
 String line = scanner.nextLine();
 Expense expense = Expense.fromFileFormat(line);
 if (expense != null) expenses.add(expense);
 }
 scanner.close();
 } catch (Exception ignored) {}
 return expenses;
 }
 public Map<String, Double> getCategorySummary() {
 Map<String, Double> summary = new HashMap<>();
 List<Expense> expenses = getAllExpenses();
 for (Expense exp : expenses) {
 summary.put(exp.getCategory(),
 summary.getOrDefault(exp.getCategory(), 0.0) + exp.getAmount());
 }
 return summary;
 }
13
 public void generateSummaryReport(String reportFile) throws IOException {
 List<Expense> expenses = getAllExpenses();
 Map<String, Double> summary = getCategorySummary();
 FileWriter writer = new FileWriter(reportFile);
 writer.write("===== Expense Summary Report =====\n\n");
 writer.write("Total Entries: " + expenses.size() + "\n\n");
 double overall = 0;
 for (String category : summary.keySet()) {
 double total = summary.get(category);
 writer.write(category + " : " + total + " BDT\n");
 overall += total;
 }
 writer.write("\nOverall Spending: " + overall + " BDT\n");
 writer.write("=====================================\n");
 writer.close();
 }
}
14
Main.java
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
15
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
16
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
17
 } else {
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
