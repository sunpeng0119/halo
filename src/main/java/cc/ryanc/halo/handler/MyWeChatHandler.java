package cc.ryanc.halo.handler;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWeChatHandler implements IMyChatHandler {

    private List<String> historyChatUserName = Collections.synchronizedList(new ArrayList<>());

    private Map<String,List<BaseMsg>>  chatRecord = new ConcurrentHashMap<>();

    @Override
    public String textMsgHandle(BaseMsg msg) {
        handlerMsg(msg);
        return null;
    }
    @Override
    public String picMsgHandle(BaseMsg msg) {
        handlerMsg( msg);
        return null;
    }

    @Override
    public String voiceMsgHandle(BaseMsg msg) {
        handlerMsg( msg);
        return null;
    }

    @Override
    public String viedoMsgHandle(BaseMsg msg) {
        handlerMsg( msg);
        return null;
    }

    @Override
    public String nameCardMsgHandle(BaseMsg msg) {
        handlerMsg( msg);
        return null;
    }

    @Override
    public void sysMsgHandle(BaseMsg msg) {

    }

    @Override
    public String verifyAddFriendMsgHandle(BaseMsg msg) {
        return null;
    }

    @Override
    public String mediaMsgHandle(BaseMsg msg) {
        handlerMsg( msg);
        return null;
    }

    private void handlerMsg(BaseMsg msg){
        String fromUserName = msg.getFromUserName();
        if (!chatRecord.containsKey(fromUserName)){
            chatRecord.put(fromUserName,Collections.synchronizedList(new ArrayList<>()));
        }
        chatRecord.get(fromUserName).add(msg);
        if (historyChatUserName.contains(fromUserName)){
            historyChatUserName.remove(fromUserName);
        }
        historyChatUserName.add(0,fromUserName);

    }

    @Override
    public List<String> getMsgFromUserName(int count) {
        List<String> result = new ArrayList<>();
        for (int i = 0;i<count&&i<historyChatUserName.size();i++){
            result.add(historyChatUserName.get(i));
        }
        return result;
    }

    @Override
    public List<BaseMsg> getChatMsg(String fromUserName, int msgCount) {
        List<BaseMsg> baseMsgs = new ArrayList<>(msgCount);
        if (chatRecord.containsKey(fromUserName)){
            return baseMsgs;
        }
        for (int i = 0;i<msgCount&&i<historyChatUserName.size();i++){
            baseMsgs.add(chatRecord.get(fromUserName).get(i));
        }

        for (int i = 0;i<msgCount&&i<historyChatUserName.size();i++){
            chatRecord.get(fromUserName).remove(i);
        }
        return baseMsgs;
    }
}
