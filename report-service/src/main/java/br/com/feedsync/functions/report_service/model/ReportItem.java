package br.com.feedsync.functions.report_service.model;

public class ReportItem {
    private String term;
    private Integer occurrences;

    public ReportItem() {}

    public ReportItem(String term, Integer occurrences) {
        this.term = term;
        this.occurrences = occurrences;
    }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public Integer getOccurrences() { return occurrences; }
    public void setOccurrences(Integer occurrences) { this.occurrences = occurrences; }
}