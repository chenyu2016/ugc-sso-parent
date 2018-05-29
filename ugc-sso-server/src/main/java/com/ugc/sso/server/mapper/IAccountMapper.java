package com.ugc.sso.server.mapper;

import com.ugc.sso.domain.Account;

/**
 * 用户账户数据接口
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/24
 */
public interface IAccountMapper {

    /**
     * 按照账号查
     * @param accName
     * @return
     */
    Account findAccountByAccName(String accName);

    /**
     * 按照Id查
     * @param accId
     * @return
     */
    Account findAccountByAccId(Long accId);

    /**
     * 创建账号
     * @param account
     * @return
     */
    void createAccount(Account account);

    /**
     * 更新账号
     * @param account
     * @return
     */
    void updateAccount(Account account);


}
