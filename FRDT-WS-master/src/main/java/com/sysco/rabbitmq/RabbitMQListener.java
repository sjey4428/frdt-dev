package com.sysco.rabbitmq;


import com.sysco.request.ReqSupplier;
import com.sysco.service.SupplierInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    private final static Logger log= LoggerFactory.getLogger("listen");

    @Autowired
    private SupplierInfoService supplierInfoService;

    @RabbitListener(queues = "vendor.send.email.queue",containerFactory = "singleListenerContainer")
    public void sendEmailMessage(@Payload ReqSupplier record){
        try {
            log.info("消费者监听交易记录信息： {} ",record);
            supplierInfoService.sendAttachmentsMail(record);
        }catch (Exception e){
            log.error("消息体解析 发生异常； ",e.fillInStackTrace());
        }
    }

//    @RabbitListener(queues = "vendor.activation.queue",containerFactory = "singleListenerContainer")
//    public void activationMessage(@Payload ReqSupplier record){
//        try {
//            log.info("激活： {} ","确认。。。。");
//        }catch (Exception e){
//            log.error("消息体解析 发生异常； ",e.fillInStackTrace());
//        }
//    }

    @RabbitListener(queues = "vendor.delay.queue",containerFactory = "singleListenerContainer")
    public void delayMessage(@Payload ReqSupplier record){
        try {
            log.info("未激活： {} ","删除信息。。。。");
        }catch (Exception e){
            log.error("消息体解析 发生异常； ",e.fillInStackTrace());
        }
    }

}
