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
