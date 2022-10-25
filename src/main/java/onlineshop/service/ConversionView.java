package onlineshop.service;

import onlineshop.model.User;

public class ConversionView implements UserLabelView {
    private User user;
    @Override
    public String getUserNameLabel() {
        return "Name: " + user.getName();
    }

    public String getUserEmailLabel() {
        return "Email: " + user.getEmail();
    }

    public String getUserRoleLabel() {
        return "Role: " + user.getRole().name();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
