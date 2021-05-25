//package com.helper;
//
//import com.exception.YyghException;
//import com.result.Result;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 创作时间：2021/4/26 18:39
// * 作者：
// *  全局异常
// */
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    //要是有异常，自己来处理
//    //自己的返回了result，在result中封装一下
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result error(Exception e){
//        e.printStackTrace();
//        return Result.fail();
//    }
//
//    /**
//     * 自定义异常处理方法
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(YyghException.class)
//    @ResponseBody
//    public Result error(YyghException e){
//        return Result.build(e.getCode(), e.getMessage());
//    }
//}
