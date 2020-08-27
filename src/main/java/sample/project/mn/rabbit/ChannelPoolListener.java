package sample.project.mn.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {
  private static final Logger LOG = LoggerFactory.getLogger(ChannelPoolListener.class);

  @Override
  public void initialize(Channel channel) throws IOException {
    channel.exchangeDeclare("judge", BuiltinExchangeType.DIRECT, true);
    channel.queueDeclare("submissions-status", true, false, false, getSubmissionsQueueArguments());
    channel.queueBind("submissions-status", "judge", "status");
    LOG.info("RabbitMQ exchange and queue were initialized.");
  }

  private Map<String, Object> getSubmissionsQueueArguments() {
    Long oneWeek = Duration.ofDays(7L).toMillis();
    Map<String, Object> queueArguments = new HashMap<>();
    queueArguments.put("x-single-active-consume", true);
    queueArguments.put("x-message-ttl", oneWeek.longValue());
    return queueArguments;
  }
}