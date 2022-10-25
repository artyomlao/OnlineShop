package onlineshop.service;

import onlineshop.model.User;

public class ViewConversion implements UserLabel{
    @Override
    public String getUserNameLabel(User user) {
        return "Name: " + user.getName();
    }
}
