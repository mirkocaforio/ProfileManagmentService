package it.unisalento.pasproject.profilemanagmentservice.service;

import it.unisalento.pasproject.profilemanagmentservice.business.producer.MessageProducer;
import it.unisalento.pasproject.profilemanagmentservice.dto.PaymentInfoMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoMessageHandler {
    private final MessageProducer messageProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentInfoMessageHandler.class);

    @Autowired
    public PaymentInfoMessageHandler(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Value("${rabbitmq.routing.payment.key}")
    private String paymentInfoTopic;

    @Value("${rabbitmq.exchange.payment.name}")
    private String paymentInfoExchange;

    public void sendPaymentInfoMessage(PaymentInfoMessageDTO paymentInfoMessageDTO) {
        messageProducer.sendMessage(paymentInfoMessageDTO, paymentInfoTopic, paymentInfoExchange);
        LOGGER.info("Send message: {}", paymentInfoMessageDTO);
    }
}
