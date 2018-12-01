package cc.ryanc.halo.handler;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;

import java.util.List;

public interface IMyChatHandler extends IMsgHandlerFace {

     List<String> getMsgFromUserName(int count);

     List<BaseMsg> getChatMsg(String fromUserName,int msgCount);
}
