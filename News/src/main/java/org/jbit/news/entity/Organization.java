package org.jbit.news.entity;

import lombok.Data;

@Data
public class Organization {
    private int nid;
    private String orgId;
    private String orgName;
    private int orgParentId;
    private int orgStates;
    private String statesName;
    private String orgArea;
    private String orgContact;
    private String orgContactNumber;
    private String orgAddress;
    private String operator;
    private String operateTime;

}
