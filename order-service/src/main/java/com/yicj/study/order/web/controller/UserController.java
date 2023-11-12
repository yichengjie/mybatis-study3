package com.yicj.study.order.web.controller;

import com.yicj.study.common.model.vo.PageVO;
import com.yicj.study.common.model.vo.ResponseVO;
import com.yicj.study.order.service.IUserService;
import com.yicj.study.order.web.request.ListUserRequest;
import com.yicj.study.order.web.response.ListUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yicj
 * @since 2023-11-12
 */
@Controller
@RequestMapping("/order/user")
public class UserController {

    private final IUserService userService ;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("list4Page")
    public ResponseVO<PageVO<ListUserResponse>> list4Page(@RequestBody ListUserRequest request){
        PageVO<ListUserResponse> pageVO = userService.list4Page(request);
        return ResponseVO.success(pageVO) ;
    }

}
