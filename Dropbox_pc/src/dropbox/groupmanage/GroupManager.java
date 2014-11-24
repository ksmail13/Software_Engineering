package dropbox.groupmanage;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import dropbox.common.Message;
import dropbox.common.MessageType;
import dropbox.common.MySocket;
import dropbox.common.ReceiveWork;

public class GroupManager {
	public static final int INVITE = 0;
	public static final int EXIT_GROUP = 1;
	public static final int DELETE_GROUP = 2;
	public static final int CREATE_GROUP = 3;
	public static final int SELECT_GROUP = 4;
	public static final int SELECT_MEMBER = 5;

	public GroupManager(int flag, String selectedGroupName,
			String selectedGroupLeader, String newMemberID) throws JSONException {
		manipulateGroup(flag, selectedGroupName, selectedGroupLeader,
				newMemberID);
	}

	public GroupManager(int flag, String groupName, String comment) throws JSONException {
		createGroup(flag, groupName, comment);
	}
	
	public GroupManager(int flag) throws JSONException{
		getGroupList();
	}
	public GroupManager(int flag, String groupId) throws JSONException{
		getMemberList(groupId);
	}

	private void getGroupList() throws JSONException {

		JSONObject jobj = new JSONObject();

		Message msg = new Message();
		jobj.put(Message.SUBCATEGORY_KEY, "grouplist");

		String data = jobj.toString();

		msg.messageType = MessageType.Group;
		msg.msg = jobj.toString();

		try {
			MySocket ms = MySocket.getConnector();
			ms.send(msg);
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	private void getMemberList(String groupId) throws JSONException {
		
		JSONObject jobj = new JSONObject();
		
		Message msg = new Message();
		jobj.put(Message.SUBCATEGORY_KEY, "memberlist");
		
		String data = jobj.toString();
		
		msg.messageType = MessageType.Group;
		msg.msg = jobj.toString();
		
		try {
			MySocket ms = MySocket.getConnector();
			ms.send(msg);
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	private void manipulateGroup(int flag, String selectedGroupName,
			String selectedGroupLeader, String newMemberID) throws JSONException {
		if (flag == INVITE) {
			JSONObject inviteJObj = new JSONObject();
			inviteJObj.put("GID", selectedGroupName);
			inviteJObj.put("AID", newMemberID);
			System.out.println(inviteJObj);
		} else if (flag == EXIT_GROUP) {
			JSONObject exitGroupJObj = new JSONObject();
			exitGroupJObj.put("GID", selectedGroupName);
			System.out.println(exitGroupJObj);
		} else if (flag == DELETE_GROUP) {
			JSONObject deleteGroupJObj = new JSONObject();
			deleteGroupJObj.put("GID", selectedGroupName);
			System.out.println(deleteGroupJObj);
		}
	}

	private void createGroup(int flag, String groupName, String comment) throws JSONException {
		if (flag == CREATE_GROUP) {
			JSONObject createGroupJObj = new JSONObject();

			createGroupJObj.put("name", groupName);
			createGroupJObj.put("comment", comment);
			createGroupJObj.put(Message.SUBCATEGORY_KEY, "create");

			System.out.println(createGroupJObj);

			Message msg = new Message();
			msg.messageType = MessageType.Group;
			msg.msg = createGroupJObj.toString();

			try {
				MySocket ms = MySocket.getConnector();
				ms.send(msg);
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
}
