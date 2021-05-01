package me.pm.lemon.friends;

import java.util.ArrayList;

public class FriendManager {
    public static ArrayList<Friend> friendsList = new ArrayList<Friend>();

    public void addFriend(String name)
    {
        friendsList.add(new Friend(name));
    }

    public void removeFriend(String name) {
        for(Friend friend: friendsList) {
            if(friend.getName().equalsIgnoreCase(name)) {
                friendsList.remove(friend);
                break;
            }
        }
    }

    public boolean isFriend(String name) {
        boolean isFriend = false;
        for(Friend friend: friendsList) {
            if(friend.getName().equalsIgnoreCase(name)) {
                isFriend = true;
                break;
            }
        }
        return isFriend;
    }
}
