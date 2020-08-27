package sample.project.mn.rabbit;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import io.micronaut.rabbitmq.exception.DefaultRabbitListenerExceptionHandler;
import io.micronaut.rabbitmq.exception.RabbitListenerException;
import io.micronaut.rabbitmq.exception.RabbitListenerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@RabbitListener
@Replaces(DefaultRabbitListenerExceptionHandler.class)
public class SubmissionListener implements RabbitListenerExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(SubmissionListener.class);

  @Queue(value = "submissions-status")
  public void getSubmissionStatus(String submissionStatus) {
    LOG.info("Your submission status: {}", submissionStatus);
  }

  @Override
  public void handle(RabbitListenerException exception) {
    LOG.error("Testing error handler");
  }
}