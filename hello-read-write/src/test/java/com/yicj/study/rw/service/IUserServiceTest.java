package com.yicj.study.rw.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yicj.study.common.model.dto.IdentityDTO;
import com.yicj.study.common.model.vo.PageVO;
import com.yicj.study.common.utils.CommonUtils;
import com.yicj.study.rw.HelloReadWriteApplication;
import com.yicj.study.rw.repository.entity.User;
import com.yicj.study.rw.web.request.PageUserRequest;
import com.yicj.study.rw.web.request.SaveUserRequest;
import com.yicj.study.rw.web.response.ListUserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = HelloReadWriteApplication.class)
class IUserServiceTest {

    @Autowired
    private IUserService userService ;

    @Test
    void addEntity() {
    }

    @Test
    void updateEntity() {
        SaveUserRequest request = new SaveUserRequest();
        request.setId(1);
        request.setCompany("test");
        IdentityDTO identity = new IdentityDTO();
        identity.setUserCode("yicj");
        userService.updateEntity(request, identity) ;
    }


    @Test
    void list4Page(){
        PageUserRequest request = new PageUserRequest() ;
        request.setCurrentPage(1);
        request.setPageSize(3);
        PageVO<ListUserResponse> page = userService.list4Page(request);
        CommonUtils.pagePrint(page);
    }

    @Test
    void list(){
        List<User> list = userService.list();
        CommonUtils.listPrint(list);
    }

    @Test
    void deleteEntity() {


    }
}