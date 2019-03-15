package com.tencent.qcloud.uikit.api.infos;

import com.tencent.qcloud.uikit.business.chat.group.model.GroupChatInfo;
import com.tencent.qcloud.uikit.business.chat.group.view.widget.GroupMemberControler;
import com.tencent.qcloud.uikit.common.component.info.InfoItemAction;
import com.tencent.qcloud.uikit.common.component.action.PopMenuAction;
import com.tencent.qcloud.uikit.common.component.titlebar.PageTitleBar;

import java.util.List;

/**
 * Created by valxehuang on 2018/7/17.
 */

public interface IGroupInfoPanel {
    void setGroupInfo(GroupChatInfo info);

    void setGroupInfoPanelEvent(GroupInfoPanelEvent event);

    void initDefault();

    void setMemberControler(GroupMemberControler controler);

    PageTitleBar getTitleBar();


    public void addInfoItem(List<InfoItemAction> items, int group, int index);

    public void addPopActions(List<PopMenuAction> actions);
}
