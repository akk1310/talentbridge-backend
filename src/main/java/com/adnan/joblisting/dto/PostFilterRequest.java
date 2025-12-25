package com.adnan.joblisting.dto;

public class PostFilterRequest {

    private String profile;
    private String location;
    private String tech;
    private Integer minExp;
    private Integer maxExp;
    private Integer page;
    private Integer size;

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTech() { return tech; }
    public void setTech(String tech) { this.tech = tech; }

    public Integer getMinExp() { return minExp; }
    public void setMinExp(Integer minExp) { this.minExp = minExp; }

    public Integer getMaxExp() { return maxExp; }
    public void setMaxExp(Integer maxExp) { this.maxExp = maxExp; }

    public int getPage() { return page == null ? 0 : page; }
    public void setPage(Integer page) { this.page = page; }

    public int getSize() { return size == null ? 10 : size; }
    public void setSize(Integer size) { this.size = size; }
}
