package com.example.comp1786_lebinhminh.model;

public class Expense {
    String id;
    String tripId;
    String expenseType;
    int amount;
    String timeOfExpense;
    String comment;

    public Expense(String id, String tripId, String expenseType, int amount, String timeOfExpense, String comment) {
        this.id = id;
        this.tripId = tripId;
        this.expenseType = expenseType;
        this.amount = amount;
        this.timeOfExpense = timeOfExpense;
        this.comment = comment;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public int getAmount() {
        return amount;
    }

    public String getTimeOfExpense() {
        return timeOfExpense;
    }

    public String getComment() {
        return comment;
    }
}
