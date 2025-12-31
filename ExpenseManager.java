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
