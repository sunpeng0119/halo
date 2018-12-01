package cc.ryanc.halo.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface WeChatService {

    String startLogin();

    String finishLogin();

    List<JSONObject> listFriends();

    List<String> listHistroryFrinds(int count);

    List<String> getMsgByNickName(String nikeName,int count);

    String sendMsg(String nikeName,String message);
}
