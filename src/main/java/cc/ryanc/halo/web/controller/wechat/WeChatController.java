package cc.ryanc.halo.web.controller.wechat;


import cc.ryanc.halo.service.WeChatService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/wechat")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    /**
     * 登錄微信
     *
     * @param model model
     */
    @GetMapping(value = "/login")
    public String archives(Model model) {
        return weChatService.startLogin();
    }

    /**
     * 查詢所有好友列表
     *
     * @param model model
     */
    @GetMapping(value = "/listFriends")
    public List<JSONObject> listFriends(Model model) {
        return weChatService.listFriends();
    }
}
