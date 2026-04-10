package Payload;

import org.json.simple.JSONObject;

public class PayloadBuilder {
    public static JSONObject loginUserPayload(String email, String password){
        JSONObject loginUser = new JSONObject();
        loginUser.put("email", email);
        loginUser.put("password", password);

        return loginUser;
    }

    public static JSONObject registerUserPayload(String firstName, String lastName, String email, String password, String groupId){
        JSONObject registerUser = new JSONObject();
        registerUser.put("firstName", firstName);
        registerUser.put("lastName", lastName);
        registerUser.put("email", email);
        registerUser.put("password", password);
        registerUser.put("confirmPassword", password);
        registerUser.put("groupId", groupId);

        return registerUser;
    }

    public static JSONObject approveUserRegistrationPayload(){
        JSONObject approveUserRegistration = new JSONObject();
        approveUserRegistration.put("status", "approved");

        return approveUserRegistration;
    }

    public static JSONObject makeUserAdminPayload(){
        JSONObject makeAdmin = new JSONObject();
        makeAdmin.put("role", "admin");
        return makeAdmin;
    }

    public static JSONObject deleteUserPayload(){
        JSONObject deleteUser = new JSONObject();
        deleteUser.put("status", "deleted");
        return deleteUser;
    }
}
