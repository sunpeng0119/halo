package cc.ryanc.halo.service.impl;

import cc.ryanc.halo.handler.MyWeChatHandler;
import cc.ryanc.halo.service.WeChatService;
import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class WeChatServiceImpl implements WeChatService {

    private Wechat wechat;

    private  String qrPath = "";

    private static final String HOME_URL = "www.zeshan.cn";

    private String currentChatName = "";

    private Map<String,JSONObject> friends = new HashMap<>();

    private Map<String,String> nickNameUserName = new HashMap<>();

    private Map<String,String> userNameNickName = new HashMap<>();

    @Autowired
    private MyWeChatHandler myWeChatHandler;

    @PostConstruct
    public void init(){
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            qrPath = path.getPath()+File.separator+"static"+File.separator+"images"+File.separator+"qr";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String startLogin() {


        if (!WechatTools.getWechatStatus()){
            WechatTools.logout();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (wechat == null){
                    wechat = new Wechat(myWeChatHandler,qrPath);
                }
                wechat.start();
                friends.clear();
                List<JSONObject> allFriends = WechatTools.getContactList();
                for (JSONObject jsonObject : allFriends){
                    String name = jsonObject.getString("RemarkName");
                    if (StringUtils.isEmpty(name)){
                        name = jsonObject.getString("NickName");
                    }
                    friends.put(name,jsonObject);
                    if (nickNameUserName.containsKey(name)){
                        System.out.println(name);
                    }
                    nickNameUserName.put(name,jsonObject.getString("UserName"));
                    userNameNickName.put(jsonObject.getString("UserName"),name);
                }
            }
        }).start();
        return HOME_URL+File.separator+"static"+File.separator+"images"+File.separator+"qr"+File.separator+"QR.jpg";
    }

    @Override
    public String finishLogin() {
        return null;
    }

    @Override
    public List<JSONObject> listFriends() {
        return WechatTools.getContactList();
    }

    @Override
    public List<String> listHistroryFrinds(int count) {
        List<String> result = new ArrayList<>(count);
        for (String userName : myWeChatHandler.getMsgFromUserName(count)){
            result.add(userNameNickName.get(userName));
        }
        return result;
    }

    @Override
    public List<String> getMsgByNickName(String nikeName, int count) {
        String fromUserName = nickNameUserName.get(nikeName);
        if (StringUtils.isEmpty(fromUserName)) {
            return new ArrayList<>();
        }
        List<BaseMsg> msgs = myWeChatHandler.getChatMsg(fromUserName,count);
        List<String> result = new ArrayList<>();
        for (BaseMsg msg : msgs){
            result.add(msg.getText());
        }
        return result;
    }

    @Override
    public String sendMsg(String nikeName, String message) {
        return null;
    }
}
