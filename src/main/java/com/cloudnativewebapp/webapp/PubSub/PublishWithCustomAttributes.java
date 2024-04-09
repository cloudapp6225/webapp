package com.cloudnativewebapp.webapp.PubSub;

import com.cloudnativewebapp.webapp.DTO.UserDTO;
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.hibernate.validator.internal.util.stereotypes.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PublishWithCustomAttributes {

    Logger logger = LoggerFactory.getLogger(PublishWithCustomAttributes.class);
    public void publishData(String projectId, String topicId, UserDTO userDTO) throws InterruptedException {
        TopicName topicName = TopicName.of(projectId, topicId);
        Publisher publisher = null;

        try {
            String uuid = userDTO.getId();
            String username = userDTO.getUsername();
            String link = "https://skynetx.me/v1/verify";
            String msgJson = "{\"uuid\":\"" + uuid + "\", \"username\":\"" + username + "\", \"link\":\"" + link + "\"}";
            publisher = Publisher.newBuilder(topicName).build();
            String msg = "New User Created form webapp";
            ByteString data = ByteString.copyFromUtf8(msgJson);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                            .setData(data)
                            .build();

            ApiFuture<String> msgIdFuture = publisher.publish(pubsubMessage);
            String msgId = msgIdFuture.get();
            logger.info("Pub/Sub message " + msgId);
            System.out.println("Message id " + msgId);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error("Pub/Sub error webapp " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
            logger.error("Pub/Sub error webapp " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            logger.error("Pub/Sub error webapp " + e.getMessage());
            throw new RuntimeException(e);
        }

        finally {
            if(publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}
