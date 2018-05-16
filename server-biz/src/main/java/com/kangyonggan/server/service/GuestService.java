package com.kangyonggan.server.service;


import com.kangyonggan.server.model.Guest;

import java.util.List;

/**
 * @author kangyonggan
 * @date 3/24/18
 */
public interface GuestService {

    /**
     * 查询留言信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Guest> findGuestsByPage(int pageNum, int pageSize);

    /**
     * 保存留言
     *
     * @param guest
     */
    void saveGuest(Guest guest);

    /**
     * 更新留言ip信息
     *
     * @param id
     * @param ip
     */
    void updateGuestIpInfo(Long id, String ip);

}
