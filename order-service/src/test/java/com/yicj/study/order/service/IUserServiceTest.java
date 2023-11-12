package com.yicj.study.order.service;

import com.yicj.study.common.model.dto.IdentityDTO;
import com.yicj.study.common.model.vo.PageVO;
import com.yicj.study.common.utils.CommonUtils;
import com.yicj.study.order.OrderServiceApplication;
import com.yicj.study.order.web.request.ListUserRequest;
import com.yicj.study.order.web.request.SaveUserRequest;
import com.yicj.study.order.web.response.ListUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest(classes = OrderServiceApplication.class)
class IUserServiceTest {

    @Autowired
    private IUserService userService ;

    private IdentityDTO identity;

    @BeforeEach
    public void init(){
        identity = new IdentityDTO() ;
        identity.setUserCode("yicj");
        identity.setUserName("YiChengJie");
    }

    @Test
    void addEntity() {
        SaveUserRequest request = new SaveUserRequest() ;
        request.setName("yicj");
        request.setCompany("company test1");
        request.setJob("job test1");
        userService.addEntity(request, identity) ;
    }

    @Test
    void updateEntity() {
        SaveUserRequest request = new SaveUserRequest() ;
        request.setName("yicj");
        request.setCompany("company test1");
        request.setJob("job test1");
        userService.updateEntity(request, identity) ;
    }

    @Test
    void list4Page() {
        ListUserRequest request = new ListUserRequest() ;
        PageVO<ListUserResponse> pageVO = userService.list4Page(request);
        CommonUtils.pagePrint(pageVO);
    }

    @Test
    void deleteEntity() {
        Integer id = 1 ;
        Integer count = userService.deleteEntity(id, identity);
        log.info("count : {}", count);
    }
}