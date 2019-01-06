package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model;

public class ConditionsListItem {
    private String positiveSubCondition;
    private String negativeSubCondition;

    public ConditionsListItem(String positiveSubCondition, String negativeSubCondition) {
        this.positiveSubCondition = positiveSubCondition;
        this.negativeSubCondition = negativeSubCondition;
    }

    public String getPositiveSubCondition() {
        return positiveSubCondition;
    }

    public String getNegativeSubCondition() {
        return negativeSubCondition;
    }

    @Override
    public String toString() {
        return positiveSubCondition + " and " + negativeSubCondition;
    }
}
