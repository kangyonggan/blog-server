package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.IpInfo;
import com.kangyonggan.app.util.IpUtil;
import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.constants.Status;
import com.kangyonggan.server.constants.YesNo;
import com.kangyonggan.server.model.Guest;
import com.kangyonggan.server.service.GuestService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @date 3/24/18
 */
@Service
@Log4j2
public class GuestServiceImpl extends BaseService<Guest> implements GuestService {


    @Override
    public List<Guest> findGuestsByPage(int pageNum, int pageSize) {
        Example example = new Example(Guest.class);
        example.createCriteria().andEqualTo("status", Status.COMPLETE.getStatus())
                .andEqualTo("isDeleted", YesNo.NO.getCode());

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, pageSize);
        return myMapper.selectByExample(example);
    }

    @Override
    @Log
    public void saveGuest(Guest guest) {
        guest.setReplyMessage(StringUtils.EMPTY);
        guest.setIpInfo("正在查地址");

        myMapper.insertSelective(guest);
    }

    @Override
    @Log
    public void updateGuestIpInfo(Long id, String ip) {
        new Thread() {
            @Override
            public void run() {
                IpInfo info = IpUtil.getIpInfo(ip);

                String city = info.getCity();
                if (StringUtils.isEmpty(city)) {
                    city = "未知地";
                }
                city += "网友";

                Guest guestbook = new Guest();
                guestbook.setId(id);
                guestbook.setIpInfo(city);
                myMapper.updateByPrimaryKeySelective(guestbook);
                log.info("留言的ip信息查询成功");
            }
        }.start();
    }

}
