package org.jbit.news.entity;

public class Menu {
    private int nid;
    private String ntitle;
    private String nparent_id;
    private String nsummary;
    private int nsort_id;
    private String noperator;
    private String noperator_time;
    private String njsp;
    private int isParent;

    public int getIsParent() {
        return isParent;
    }

    public void setIsParent(int isParent) {
        this.isParent = isParent;
    }

    public String getNjsp() {
        return njsp;
    }

    public void setNjsp(String njsp) {
        this.njsp = njsp;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public String getNparent_id() {
        return nparent_id;
    }

    public void setNparent_id(String nparent_id) {
        this.nparent_id = nparent_id;
    }

    public String getNsummary() {
        return nsummary;
    }

    public void setNsummary(String nsummary) {
        this.nsummary = nsummary;
    }

    public int getNsort_id() {
        return nsort_id;
    }

    public void setNsort_id(int nsort_id) {
        this.nsort_id = nsort_id;
    }

    public String getNoperator() {
        return noperator;
    }

    public void setNoperator(String noperator) {
        this.noperator = noperator;
    }

    public String getNoperator_time() {
        return noperator_time;
    }

    public void setNoperator_time(String noperator_time) {
        this.noperator_time = noperator_time;
    }
}
