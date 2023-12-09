package Friend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

class FriendRequestModel {
    String senderID;
    String receiverID;
    boolean accepted;

    public FriendRequestModel(String senderID, String receiverID, boolean accepted) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.accepted = accepted;
    }
}

public class FriendController {
    Connection conn;
    public FriendController(Connection conn) {
        this.conn = conn;
    }

    public int userIdFromName(String username) {
        try {
            String sql = "SELECT userid FROM username_userid WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("userid");
            }
            return -1;
        } catch (Exception e) {
            System.out.println("Error getting userID from username");
            throw new RuntimeException(e);
        }
    }

    public String usernameFromId(int userid) {
        try {
            String sql = "SELECT username FROM username_userid WHERE userid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userid);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getString("username");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error getting username from userID");
            throw new RuntimeException(e);
        }
    }

    public boolean sendRequest(String senderName, String receiverName) {
        try {
            //Get the senderID and receiverID
            int senderID = userIdFromName(senderName);
            int receiverID = userIdFromName(receiverName);

            if(senderID == -1 || receiverID == -1) {
                System.out.println("User does not exist");
                return false;
            }


            //See if the request already exists
            String sql = "SELECT * FROM friendrequest WHERE senderID = ? AND receiverID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, senderID);
            stmt.setInt(2, receiverID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                System.out.println("Friend request already exists");
                return false;
            }

            stmt.setInt(1, receiverID);
            stmt.setInt(2, senderID);
            rs = stmt.executeQuery();
            if(rs.next()) {
                System.out.println("This person has already sent you a friend request");
                return false;
            }

            //Add the request to the database
            sql = "INSERT INTO friendrequest (senderID, receiverID, accepted) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, senderID);
            stmt.setInt(2, receiverID);
            stmt.setBoolean(3, false);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error adding friend request");
            throw new RuntimeException(e);
        }
    }

    public boolean acceptRequest(String senderName, String receiverName) {
        try {
            //Get the senderID and receiverID
            int senderID = userIdFromName(senderName);
            int receiverID = userIdFromName(receiverName);

            //See if a request exists
            String sql = "SELECT * FROM friendrequest WHERE senderID = ? AND receiverID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, senderID);
            stmt.setInt(2, receiverID);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                System.out.println("Friend request does not exist");
                return false;
            }

            //Update the request to accepted
            sql = "UPDATE friendrequest SET accepted = ? WHERE senderID = ? AND receiverID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setInt(2, senderID);
            stmt.setInt(3, receiverID);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error accepting friend request");
            throw new RuntimeException(e);
        }
    }

    public boolean declineRequest(String senderName, String receiverName) {
        try {
            //Get the senderID and receiverID
            int senderID = userIdFromName(senderName);
            int receiverID = userIdFromName(receiverName);

            //See if there is a request with the sender-receiver ID pair
            String sql = "SELECT * FROM friendrequest WHERE senderID = ? AND receiverID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, senderID);
            stmt.setInt(2, receiverID);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                System.out.println("Friend request does not exist");
                return false;
            }

            sql = "DELETE FROM friendrequest WHERE senderID = ? AND receiverID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, senderID);
            stmt.setInt(2, receiverID);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error removing friend request");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getRequestList(String username) {
        try {
            String sql =
            "SELECT sender.username as sender_name, receiver.username as receiver_name, friendrequest.accepted as accepted " +
            "FROM friendrequest " +
            "JOIN username_userid as sender on sender.userid = friendrequest.senderid " +
            "JOIN username_userid as receiver on receiver.userid = friendrequest.receiverid " +
            "WHERE receiver.username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                System.out.println("No friend requests");
                return null;
            }
            ArrayList<String> requestList = new ArrayList<>();
            do {
                String senderName = rs.getString("sender_name");
                boolean accepted = rs.getBoolean("accepted");
                if(!accepted) {
                    requestList.add(senderName);
                }
            } while(rs.next());
            return requestList;
        } catch (Exception e) {
            System.out.println("Error getting friend request list");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getFriendList(String username) {
        try {
            //Get the list of friends
            String sql =
            "SELECT usr.username as name, friend.username as friendname " +
            "FROM friendlist " +
            "JOIN username_userid usr ON usr.userid = friendlist.userid " +
            "JOIN username_userid friend on friend.userid = friendlist.friendid " +
            "WHERE usr.username = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> friendList = new ArrayList<>();
            if(!rs.next()) {
                System.out.println("No friends");
                return null;
            }
            do {
                String friendName = rs.getString("friendname");
                friendList.add(friendName);
            } while(rs.next());
            return friendList;
        } catch (Exception e) {
            System.out.println("Error getting friend list");
            throw new RuntimeException(e);
        }
    }
}
