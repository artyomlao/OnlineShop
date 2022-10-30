package onlineshop.service;

import onlineshop.model.User;

public interface UserLabelView {
    String getUserNameLabel();

    String getUserEmailLabel();

    String getUserRoleLabel();

    void setUser(User user);
}
