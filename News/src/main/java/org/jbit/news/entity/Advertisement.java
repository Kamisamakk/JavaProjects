package org.jbit.news.entity;

import java.sql.Date;

public class Advertisement {
    private int nid;
    private int ntid;
    private String ntitle;
    private String nstates;
    private String nsummary;
    private String npicpath;
    private String nbegindate;
    private String nenddate;
    private String noperator;
    private String noperate_time;
    private int check_states;//0 未审核 1审核通过 2审核未通过

    public String getNoperate_time() {
        return noperate_time;
    }

    public void setNoperate_time(String noperate_time) {
        this.noperate_time = noperate_time;
    }

    public int getCheck_states() {
        return check_states;
    }

    public void setCheck_states(int check_states) {
        this.check_states = check_states;
    }

    public String getNoperator() {
        return noperator;
    }

    public void setNoperator(String noperator) {
        this.noperator = noperator;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getNtid() {
        return ntid;
    }

    public void setNtid(int ntid) {
        this.ntid = ntid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public String getNstates() {
        return nstates;
    }

    public void setNstates(String nstates) {
        this.nstates = nstates;
    }

    public String getNsummary() {
        return nsummary;
    }

    public void setNsummary(String nsummary) {
        this.nsummary = nsummary;
    }

    public String getNpicpath() {
        return npicpath;
    }

    public void setNpicpath(String npicpath) {
        this.npicpath = npicpath;
    }

    public String getNbegindate() {
        return nbegindate;
    }

    public void setNbegindate(String nbegindate) {
        this.nbegindate = nbegindate;
    }

    public String getNenddate() {
        return nenddate;
    }

    public void setNenddate(String nenddate) {
        this.nenddate = nenddate;
    }

}
